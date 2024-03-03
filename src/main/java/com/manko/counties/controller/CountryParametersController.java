package com.manko.counties.controller;

import com.manko.counties.model.dto.CountryParametersDto;
import com.manko.counties.service.CountriesParametersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries-parameters")
public class CountryParametersController {
    private final CountriesParametersService countriesParametersService;

    public CountryParametersController(CountriesParametersService countriesParametersService) {
        this.countriesParametersService = countriesParametersService;
    }

    @GetMapping
    public ResponseEntity<CountryParametersDto> getCodeByCountryOrId(@RequestParam String country) {
        CountryParametersDto code = countriesParametersService.getCodeByCountryOrId(country);
        return ResponseEntity.ok(code);
    }

    @GetMapping("/code")
    public ResponseEntity<List<CountryParametersDto>> getCountryByCode(@RequestParam Integer code) {
        List<CountryParametersDto> countries = countriesParametersService.getCountriesByCode(code);
        return ResponseEntity.ok(countries);
    }
}