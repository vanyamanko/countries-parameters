package com.manko.countries.service;

import com.manko.countries.component.Cache;
import com.manko.countries.dao.CountryRepository;
import com.manko.countries.dao.RegionRepository;
import com.manko.countries.model.Country;
import com.manko.countries.model.Region;
import com.manko.countries.model.dto.BaseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.manko.countries.service.utility.RegionUtils.buildRegionResponseFromModel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class RegionServiceTest {

    @InjectMocks
    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private Cache cache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        regionService = new RegionService(regionRepository, countryRepository, cache);
    }

    @Test
    void testGetAll() {
        List<Region> regions = new ArrayList<>();
        Region region1 = new Region();
        region1.setId(1);
        region1.setName("Region 1");
        regions.add(region1);

        Region region2 = new Region();
        region2.setId(2);
        region2.setName("Region 2");
        regions.add(region2);

        when(regionRepository.findAll()).thenReturn(regions);

        List<BaseDto.Response> responses = regionService.getAll();

        assertEquals(2, responses.size());
        assertEquals("Region 1", responses.get(0).getName());
        assertEquals("Region 2", responses.get(1).getName());

        verify(regionRepository, times(1)).findAll();
    }

    @Test
    void testGet() {
        int regionId = 1;
        String cacheKey = "id" + regionId;

        Region region = new Region();
        region.setId(regionId);
        region.setName("Test Region");

        BaseDto.Response expectedResponse = new BaseDto.Response();
        expectedResponse.setId(regionId);
        expectedResponse.setName("Test Region");

        when(cache.get(cacheKey)).thenReturn(null);
        when(regionRepository.findById(regionId)).thenReturn(Optional.of(region));
        doNothing().when(cache).put(cacheKey, expectedResponse);

        BaseDto.Response response = regionService.get(regionId);

        assertEquals(expectedResponse, response);

        verify(cache, times(1)).get(cacheKey);
        verify(regionRepository, times(1)).findById(regionId);
        verify(cache, times(1)).put(cacheKey, expectedResponse);
    }

    @Test
    void testGet_CachedDataNotNull() {
        Integer id = 1;
        String key = "id" + id;
        BaseDto.Response cachedData = new BaseDto.Response();

        when(cache.get(key)).thenReturn(cachedData);

        assertEquals(cachedData, regionService.get(id));
    }

    @Test
    void testGet_NonExistingRegion() {
        Integer id = 1;
        String key = "id" + id;

        when(cache.get(key)).thenReturn(null);
        when(regionRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = null;
        try {
            regionService.get(id);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertEquals("error 500 (NOT FOUND ID IN DB)", exception.getMessage());
        verify(cache, times(1)).get(key);
        verify(regionRepository, times(1)).findById(id);
    }

    @Test
    void testCreate() {
        List<BaseDto.RequestBody> createForms = new ArrayList<>();
        BaseDto.RequestBody requestBody = new BaseDto.RequestBody();
        requestBody.setName("testRegion");
        requestBody.setCountries(List.of("testCountry"));
        createForms.add(requestBody);
        when(regionRepository.findByName(requestBody.getName())).thenReturn(Optional.empty());


        List<BaseDto.Response> responses = regionService.create(createForms);

        assertEquals(1, responses.size());
        verify(regionRepository, times(1)).findByName(requestBody.getName());
        verify(regionRepository, times(1)).save(any(Region.class));
    }

    @Test
    void testCreate_WithDuplicateRegion() {
        List<BaseDto.RequestBody> createForms = new ArrayList<>();
        BaseDto.RequestBody requestBody = new BaseDto.RequestBody();
        requestBody.setName("testRegion");
        requestBody.setCountries(List.of("testCountry"));
        createForms.add(requestBody);

        Region region = new Region();
        when(regionRepository.findByName(requestBody.getName())).thenReturn(Optional.of(region));

        assertThrows(IllegalArgumentException.class, () -> regionService.create(createForms));

        verify(regionRepository, times(1)).findByName(requestBody.getName());
        verify(regionRepository, times(1)).findByName(requestBody.getName());

    }

    @Test
    void testUpdate() {
        Integer id = 1;
        BaseDto.RequestBody updateForm = new BaseDto.RequestBody("updateRegion", List.of("updateCountry"));

        Region region = new Region();
        region.setName("region");

        Region newRegion = new Region();
        newRegion.setName(updateForm.getName());


        Country country = new Country();
        country.setName("country");
        List<Country> countries = List.of(country);
        newRegion.setCountries(countries);

        when(regionRepository.findById(id)).thenReturn(Optional.of(region));
        when(regionService.saveRegion(region, updateForm)).thenReturn(newRegion);
        when(countryRepository.findByNames(updateForm.getCountries())).thenReturn(countries);

        assertEquals(buildRegionResponseFromModel(newRegion), regionService.update(id, updateForm));

        verify(regionRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteRegionWithCountries() {
        Region region = new Region();
        region.setId(1);
        Country country1 = new Country();
        country1.setRegion(region);
        Country country2 = new Country();
        country2.setRegion(region);
        region.setCountries(List.of(country1, country2));

        when(regionRepository.findById(1)).thenReturn(Optional.of(region));

        regionService.delete(1);

        verify(countryRepository, times(2)).save(any());
        verify(regionRepository, times(1)).deleteById(1);
    }

    @Test
    void testDelete_NonExistingRegion() {
        Integer id = 1;

        when(regionRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = null;
        try {
            regionService.delete(id);
        } catch (IllegalArgumentException ex) {
            exception = ex;
        }

        assertEquals("error 500 (NOT FOUND ID IN DB)", exception.getMessage());
        verify(regionRepository, times(0)).deleteById(id);
    }

    @Test
    void testSaveRegion() {
        Region region = new Region();
        region.setName("Region");

        BaseDto.RequestBody requestBody = new BaseDto.RequestBody();
        requestBody.setName("Updated Region");
        requestBody.setCountries(List.of("First Country", "Second Country"));

        Country firstCountry = new Country();
        firstCountry.setId(1);
        firstCountry.setName("First Country");

        Country secondCountry = new Country();
        secondCountry.setId(2);
        secondCountry.setName("Second Country");

        when(countryRepository.findByNames(List.of("First Country", "Second Country")))
                .thenReturn(List.of(firstCountry, secondCountry));
        when(regionRepository.save(region)).thenReturn(region);

        Region savedRegion = regionService.saveRegion(region, requestBody);

        assertEquals("Updated Region", savedRegion.getName());
        List<Country> savedCountries = savedRegion.getCountries();
        assertEquals(2, savedCountries.size());
        assertEquals("First Country", savedCountries.get(0).getName());
        assertEquals("Second Country", savedCountries.get(1).getName());

        verify(countryRepository, times(1)).findByNames(List.of("First Country", "Second Country"));
        verify(regionRepository, times(1)).save(region);
        verify(countryRepository, times(1)).save(firstCountry);
        verify(countryRepository, times(1)).save(secondCountry);
    }

}
