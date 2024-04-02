package com.manko.countries.controller;

import com.manko.countries.model.dto.CountryDto;
import com.manko.countries.service.CountryService;

import com.manko.countries.service.RequestCounterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/countries-parameters")
@Slf4j
@CrossOrigin(origins = "*")
public class CountryController {
    private final CountryService countriesService;
    private final RequestCounterService requestCounterService;

    @GetMapping("/country-or-short-name")
    public ResponseEntity<CountryDto.Response> getCodeByCountryOrId(@RequestParam String country) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        CountryDto.Response code = countriesService.getCodeByCountryOrId(country);
        return ResponseEntity.ok(code);
    }

    @GetMapping("/code")
    public ResponseEntity<List<CountryDto.Response>> getCountryByCode(@RequestParam Integer code) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        List<CountryDto.Response> countries = countriesService.getCountriesByCode(code);
        return ResponseEntity.ok(countries);
    }

    @GetMapping
    public ResponseEntity<List<CountryDto.Response>> getAllCountryParameters() {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        List<CountryDto.Response> countries = countriesService.getAll();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto.Response> getCountryById(@PathVariable Integer id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        CountryDto.Response country = countriesService.get(id);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<CountryDto.Response>> createCountry(@RequestBody List<CountryDto.RequestBody> createForms) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        List<CountryDto.Response> responses = countriesService.create(createForms);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDto.Response> updateCountryParameters(@PathVariable Integer id, @RequestBody CountryDto.RequestBody countryParameters) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        return new ResponseEntity<>(countriesService.update(id, countryParameters), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountryParameters(@PathVariable Integer id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        countriesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}