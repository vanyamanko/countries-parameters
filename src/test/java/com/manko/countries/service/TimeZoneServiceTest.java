package com.manko.countries.service;

import com.manko.countries.component.Cache;
import com.manko.countries.dao.CountryRepository;
import com.manko.countries.dao.TimeZoneRepository;
import com.manko.countries.model.Country;
import com.manko.countries.model.TimeZone;
import com.manko.countries.model.dto.BaseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static com.manko.countries.service.utility.TimeZoneUtils.buildTimeZoneResponseFromModel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimeZoneServiceTest {

    @InjectMocks
    private TimeZoneService timeZoneService;

    @Mock
    private TimeZoneRepository timeZoneRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private Cache cache;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        List<TimeZone> timeZones = new ArrayList<>();
        TimeZone timeZoneFirst = new TimeZone();
        timeZoneFirst.setId(1);
        timeZoneFirst.setName("timeZone 1");
        timeZones.add(timeZoneFirst);

        TimeZone timeZoneSecond = new TimeZone();
        timeZoneSecond.setId(2);
        timeZoneSecond.setName("timeZone 2");
        timeZones.add(timeZoneSecond);

        when(timeZoneRepository.findAll()).thenReturn(timeZones);

        List<BaseDto.Response> responses = timeZoneService.getAll();

        assertEquals(2, responses.size());
        assertEquals("timeZone 1", responses.get(0).getName());
        assertEquals("timeZone 2", responses.get(1).getName());

        verify(timeZoneRepository, times(1)).findAll();
    }

    @Test
    void testGet() {
        int id = 1;
        String cacheKey = "idTimeZone" + id;

        TimeZone timeZone = new TimeZone();
        timeZone.setId(id);
        timeZone.setName("Test timeZone");

        BaseDto.Response expectedResponse = new BaseDto.Response();
        expectedResponse.setId(id);
        expectedResponse.setName("Test timeZone");

        when(cache.get(cacheKey)).thenReturn(null);
        when(timeZoneRepository.findById(id)).thenReturn(Optional.of(timeZone));
        doNothing().when(cache).put(cacheKey, expectedResponse);

        BaseDto.Response response = timeZoneService.get(id);

        assertEquals(expectedResponse, response);

        verify(cache, times(1)).get(cacheKey);
        verify(timeZoneRepository, times(1)).findById(id);
        verify(cache, times(1)).put(cacheKey, expectedResponse);
    }

    @Test
    void testGet_CachedDataNotNull() {
        Integer id = 1;
        String key = "idTimeZone" + id;
        BaseDto.Response cachedData = new BaseDto.Response();

        when(cache.get(key)).thenReturn(cachedData);

        assertEquals(cachedData, timeZoneService.get(id));
    }

    @Test
    void testGet_NonExistingTimeZone() {
        Integer id = 1;
        String key = "idTimeZone" + id;

        when(cache.get(key)).thenReturn(null);
        when(timeZoneRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = new Exception();
        try {
            timeZoneService.get(id);
        } catch (IllegalArgumentException ex) {
            exception = ex;
        }

        assertEquals("error 500 (NOT FOUND ID IN DB)", exception.getMessage());
        verify(cache, times(1)).get(key);
        verify(timeZoneRepository, times(1)).findById(id);
    }

    @Test
    void testCreate() {
        List<BaseDto.RequestBody> createForms = new ArrayList<>();
        BaseDto.RequestBody requestBody = new BaseDto.RequestBody();
        requestBody.setName("testTimeZone");
        requestBody.setCountries(List.of("testCountry"));
        createForms.add(requestBody);


        List<BaseDto.Response> responses = timeZoneService.create(createForms);

        assertEquals(1, responses.size());
        verify(timeZoneRepository, times(1)).save(any(TimeZone.class));
    }

    @Test
    void testUpdate() {
        Integer id = 1;
        BaseDto.RequestBody updateForm = new BaseDto.RequestBody("updateTimeZone", List.of("updateCountry"));

        TimeZone timeZone = new TimeZone();
        timeZone.setName("timeZone");

        TimeZone newTimeZone = new TimeZone();
        newTimeZone.setName(updateForm.getName());


        Country country = new Country();
        country.setName("country");
        List<Country> countries = List.of(country);
        newTimeZone.setCountryList(countries);

        when(timeZoneRepository.findById(id)).thenReturn(Optional.of(timeZone));
        when(timeZoneService.saveTimeZone(timeZone, updateForm)).thenReturn(newTimeZone);
        when(countryRepository.findByNames(updateForm.getCountries())).thenReturn(countries);

        assertEquals(buildTimeZoneResponseFromModel(newTimeZone), timeZoneService.update(id, updateForm));

        verify(timeZoneRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteTimeZone() {

        TimeZone timeZone = mock(TimeZone.class);
        timeZone.setId(1);
        Country countryFirst = new Country();
        countryFirst.setTimeZones(new HashSet<>(Set.of(timeZone)));
        Country countrySecond = new Country();
        countrySecond.setTimeZones(new HashSet<>(Set.of(timeZone)));

        when(timeZoneRepository.findById(1)).thenReturn(Optional.of(timeZone));
        when(timeZone.getCountryList()).thenReturn(new ArrayList<>(List.of(countryFirst, countrySecond)));

        timeZoneService.delete(1);

        verify(timeZoneRepository, times(1)).deleteById(1);
    }

    @Test
    void testDelete_NonExistingTimeZone() {
        Integer id = 1;

        when(timeZoneRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = null;
        try {
            timeZoneService.delete(id);
        } catch (IllegalArgumentException ex) {
            exception = ex;
        }

        assert exception != null;
        assertEquals("error 500 (NOT FOUND ID IN DB)", exception.getMessage());
        verify(timeZoneRepository, times(0)).deleteById(id);
    }

    @Test
    void testSaveTimeZone() {
        TimeZone timeZone = new TimeZone();
        timeZone.setName("TimeZone");

        BaseDto.RequestBody requestBody = new BaseDto.RequestBody();
        requestBody.setName("Updated TimeZone");
        requestBody.setCountries(List.of("First Country", "Second Country"));

        Country firstCountry = new Country();
        firstCountry.setId(1);
        firstCountry.setName("First Country");

        Country secondCountry = new Country();
        secondCountry.setId(2);
        secondCountry.setName("Second Country");

        when(countryRepository.findByNames(List.of("First Country", "Second Country")))
                .thenReturn(List.of(firstCountry, secondCountry));
        when(timeZoneRepository.save(timeZone)).thenReturn(timeZone);

        TimeZone saveTimeZone = timeZoneService.saveTimeZone(timeZone, requestBody);

        assertEquals("Updated TimeZone", saveTimeZone.getName());
        List<Country> savedCountries = saveTimeZone.getCountryList();
        assertEquals(2, savedCountries.size());
        assertEquals("First Country", savedCountries.get(0).getName());
        assertEquals("Second Country", savedCountries.get(1).getName());

        verify(countryRepository, times(1)).findByNames(List.of("First Country", "Second Country"));
        verify(timeZoneRepository, times(1)).save(timeZone);
        verify(countryRepository, times(1)).save(firstCountry);
        verify(countryRepository, times(1)).save(secondCountry);
    }
}