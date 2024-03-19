package com.manko.countries.controller;

import com.manko.countries.model.dto.CountryDto;
import com.manko.countries.service.CountryService;

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
public class CountryController {
    private final CountryService countriesParametersService;

    public CountryController(CountryService countriesParametersService) {
        this.countriesParametersService = countriesParametersService;
    }

    @GetMapping("/country-or-short-name")
    public ResponseEntity<CountryDto.Response> getCodeByCountryOrId(@RequestParam String country) {
        CountryDto.Response code = countriesParametersService.getCodeByCountryOrId(country);
        return ResponseEntity.ok(code);
    }

    @GetMapping("/code")
    public ResponseEntity<List<CountryDto.Response>> getCountryByCode(@RequestParam Integer code) {
        List<CountryDto.Response> countries = countriesParametersService.getCountriesByCode(code);
        return ResponseEntity.ok(countries);
    }

    @GetMapping
    public ResponseEntity<List<CountryDto.Response>> getAllCountryParameters() {
        List<CountryDto.Response> countries = countriesParametersService.getAll();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto.Response> getCountryParametersById(@PathVariable Integer id) {
        CountryDto.Response country = countriesParametersService.get(id);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<CountryDto.Response>> createCountryParameters(@RequestBody List<CountryDto.RequestBody> createForms) {
        List<CountryDto.Response> responses = countriesParametersService.create(createForms);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDto.Response> updateCountryParameters(@PathVariable Integer id, @RequestBody CountryDto.RequestBody countryParameters) {
        return new ResponseEntity<>(countriesParametersService.update(id, countryParameters), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountryParameters(@PathVariable Integer id) {
        countriesParametersService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}