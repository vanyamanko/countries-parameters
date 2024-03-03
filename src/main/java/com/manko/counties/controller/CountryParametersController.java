package com.manko.counties.controller;

import com.manko.counties.model.dto.CountryParametersDto;
import com.manko.counties.service.CountriesParametersService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/country-or-id")
    public ResponseEntity<CountryParametersDto.Response> getCodeByCountryOrId(@RequestParam String country) {
        CountryParametersDto.Response code = countriesParametersService.getCodeByCountryOrId(country);
        return ResponseEntity.ok(code);
    }

    @GetMapping("/code")
    public ResponseEntity<List<CountryParametersDto.Response>> getCountryByCode(@RequestParam Integer code) {
        List<CountryParametersDto.Response> countries = countriesParametersService.getCountriesByCode(code);
        return ResponseEntity.ok(countries);
    }

    @GetMapping
    public ResponseEntity<List<CountryParametersDto.Response>> getAllCountryParameters() {
        List<CountryParametersDto.Response> countries = countriesParametersService.getAll();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryParametersDto.Response> getCountryParametersById(@PathVariable Integer id) {
        CountryParametersDto.Response country = countriesParametersService.get(id);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CountryParametersDto.Response> createCountryParameters(@RequestBody CountryParametersDto.RequestBody countryParameters) {
        return new ResponseEntity<>(countriesParametersService.create(countryParameters), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryParametersDto.Response> updateCountryParameters(@PathVariable Integer id, @RequestBody CountryParametersDto.RequestBody countryParameters) {
        return new ResponseEntity<>(countriesParametersService.update(id, countryParameters), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountryParameters(@PathVariable Integer id) {
        countriesParametersService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}