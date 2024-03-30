package com.manko.countries.controller;

import com.manko.countries.model.dto.CountryDto;
import com.manko.countries.service.CountryService;
import com.manko.countries.service.RequestCounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    @Mock
    private RequestCounterService requestCounterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllCountries() {
        List<CountryDto.Response> countries = List.of(mock(CountryDto.Response.class));

        when(countryService.getAll()).thenReturn(countries);

        assertEquals(new ResponseEntity<>(countries, HttpStatus.OK), countryController.getAllCountryParameters());

        verify(countryService, times(1)).getAll();
    }

    @Test
    void testGetCodeByCountryOrId() {
        String country = "country";
        CountryDto.Response code = mock(CountryDto.Response.class);

        when(countryService.getCodeByCountryOrId(country)).thenReturn(code);

        assertEquals(ResponseEntity.ok(code), countryController.getCodeByCountryOrId(country));

        verify(countryService, times(1)).getCodeByCountryOrId(country);
    }

    @Test
    void testGetCountryByCode() {
        Integer code = 1;
        List<CountryDto.Response> countries = List.of(mock(CountryDto.Response.class));

        when(countryService.getCountriesByCode(code)).thenReturn(countries);

        assertEquals(ResponseEntity.ok(countries), countryController.getCountryByCode(code));

        verify(countryService, times(1)).getCountriesByCode(code);
    }

    @Test
    void testGetCountryById() {
        Integer id = 1;
        CountryDto.Response country = mock(CountryDto.Response.class);

        when(countryService.get(id)).thenReturn(country);

        assertEquals(new ResponseEntity<>(country, HttpStatus.OK), countryController.getCountryById(id));

        verify(countryService, times(1)).get(id);
    }

    @Test
    void testCreateCountry() {
        List<CountryDto.RequestBody> createForms = new ArrayList<>();

        List<CountryDto.Response> expectedResponses = new ArrayList<>();

        when(countryService.create(createForms)).thenReturn(expectedResponses);

        ResponseEntity<List<CountryDto.Response>> expectedResponse = new ResponseEntity<>(expectedResponses, HttpStatus.CREATED);
        ResponseEntity<List<CountryDto.Response>> actualResponse = countryController.createCountry(createForms);

        assertEquals(expectedResponse, actualResponse);

        verify(countryService, times(1)).create(createForms);
    }

    @Test
    void testUpdateCountryParameters() {
        Integer id = 1;
        CountryDto.RequestBody countryParameters = mock(CountryDto.RequestBody.class);

        CountryDto.Response expectedResponse = mock(CountryDto.Response.class);

        when(countryService.update(id, countryParameters)).thenReturn(expectedResponse);

        ResponseEntity<CountryDto.Response> expectedResponseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.CREATED);
        ResponseEntity<CountryDto.Response> actualResponseEntity = countryController.updateCountryParameters(id, countryParameters);

        assertEquals(expectedResponseEntity, actualResponseEntity);

        verify(countryService, times(1)).update(id, countryParameters);

    }

    @Test
    void testDeleteCountryParameters() {
        Integer id = 1;
        ResponseEntity<Void> expectedResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> actualResponse = countryController.deleteCountryParameters(id);

        assertEquals(expectedResponse, actualResponse);

        verify(countryService, times(1)).delete(id);
    }
}