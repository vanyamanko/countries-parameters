package com.manko.countries.service;

import com.manko.countries.component.Cache;
import com.manko.countries.dao.CountryRepository;
import com.manko.countries.dao.RegionRepository;
import com.manko.countries.model.Country;
import com.manko.countries.model.Region;
import com.manko.countries.model.dto.CountryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.manko.countries.service.utility.CountryUtils.buildCountryParametersDtoFromModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.*;

import static org.mockito.Mockito.mock;

class CountryServiceImplTest {

    @InjectMocks
    private CountryServiceImpl countryService;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private Cache cache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetCodeByCountryOrId() {
        CountryDto.Response expectedResponse = mock(CountryDto.Response.class);
        String countryOrShortName = "CA";

        when(cache.get("countryOrShortName" + countryOrShortName)).thenReturn(expectedResponse);


        Country country = new Country();
        country.setShortName(countryOrShortName);
        country.setName(countryOrShortName);

        when(countryRepository.findByNameIgnoreCaseOrShortNameIgnoreCase(countryOrShortName, countryOrShortName)).thenReturn(Optional.of(country));

        CountryDto.Response actualResponse = countryService.getCodeByCountryOrId(countryOrShortName);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetCountriesByCode() {

        Integer code = 1;

        List<CountryDto.Response> expectedResponse = new ArrayList<>();
        expectedResponse.add(mock(CountryDto.Response.class));

        String key = "code" + code;

        when(cache.get(key)).thenReturn(expectedResponse);

        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country());

        when(countryRepository.findByCode(code)).thenReturn(countryList);

        List<CountryDto.Response> actualResponse = countryService.getCountriesByCode(code);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGet() {
        Integer id = 1;
        Country country = new Country();
        Region region = new Region();
        country.setRegion(region);
        when(countryRepository.findById(id)).thenReturn(Optional.of(country));
        assertEquals(buildCountryParametersDtoFromModel(country), countryService.get(id));
    }

    @Test
    void testCreate() {
        List<CountryDto.RequestBody> createForms = new ArrayList<>();

        CountryDto.RequestBody requestBody = CountryDto.RequestBody.builder()
                .countryShortName("CountryShort1")
                .country("Country1")
                .code(123)
                .region("Region1")
                .timeZones(Set.of("TimeZone1", "TimeZone2"))
                .build();
        requestBody.setCountry("Country1");
        requestBody.setCountryShortName("CountryShort1");
        requestBody.setRegion("Region1");

        createForms.add(requestBody);

        Region region = new Region();
        region.setId(1);
        region.setName("Region1");

        when(countryRepository.findByNameIgnoreCaseOrShortNameIgnoreCase("Country1", "CountryShort1"))
                .thenReturn(Optional.empty());
        when(regionRepository.findByName("Region1")).thenReturn(Optional.of(region));
        when(countryRepository.save(any(Country.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<CountryDto.Response> responses = countryService.create(createForms);

        assertEquals(1, responses.size());
        assertEquals("Country1", responses.get(0).getCountry());
        assertEquals("CountryShort1", responses.get(0).getCountryShortName());
        assertEquals("Region1", responses.get(0).getRegion());

        verify(countryRepository, times(1)).findByNameIgnoreCaseOrShortNameIgnoreCase("Country1", "CountryShort1");
        verify(regionRepository, times(1)).findByName("Region1");
        verify(countryRepository, times(1)).save(any(Country.class));
    }

    @Test
    void testUpdate() {
        Integer id = 1;
        CountryDto.RequestBody updateForm = CountryDto.RequestBody.builder()
                .countryShortName("CountryShort1")
                .country("Country1")
                .code(123)
                .region("Region1")
                .timeZones(Set.of("TimeZone1", "TimeZone2"))
                .build();

        Country existingCountry = new Country();
        existingCountry.setId(id);

        Region existingRegion = new Region();
        existingRegion.setName("Region1");

        Country updatedCountry = new Country();

        when(countryRepository.findById(id)).thenReturn(Optional.of(existingCountry));
        when(regionRepository.findByName(updateForm.getRegion())).thenReturn(Optional.of(existingRegion));
        when(countryRepository.save(any(Country.class))).thenReturn(updatedCountry);

        countryService.update(id, updateForm);

        verify(countryRepository, times(1)).findById(id);
        verify(regionRepository, times(1)).findByName(updateForm.getRegion());
        verify(countryRepository, times(1)).save(any(Country.class));
    }
    @Test
    void testDelete() {
        Integer id = 1;

        countryService.delete(id);

        verify(countryRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdateCountryParameters() {
        Country existingCountry = new Country();
        existingCountry.setShortName("ExistingShortName");
        existingCountry.setName("ExistingName");
        existingCountry.setCode(123);
        Region existingRegion = new Region();
        existingRegion.setName("ExistingRegion");

        CountryDto.RequestBody updateForm = CountryDto.RequestBody.builder()
                .countryShortName("NewShortName")
                .country("NewName")
                .code(456)
                .region("NewRegion")
                .build();

        Country updatedCountry = countryService.updateCountryParameters(existingCountry, updateForm, existingRegion);
        assertEquals("NewShortName", updatedCountry.getShortName());
        assertEquals("NewName", updatedCountry.getName());
        assertEquals(456, updatedCountry.getCode());
        assertEquals(existingRegion, updatedCountry.getRegion());
    }

    @Test
    void testGetAll() {
        Country country = new Country();
        Region region = new Region();
        country.setRegion(region);
        List<Country> countryList = List.of(country);
        when(countryRepository.findAll()).thenReturn(countryList);

        List<CountryDto.Response> responseList = countryService.getAll();

        assertEquals(1, responseList.size());
    }

}

