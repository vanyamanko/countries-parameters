package com.manko.countries.controller;

import com.manko.countries.model.dto.BaseDto;
import com.manko.countries.service.CrudService;
import com.manko.countries.service.RequestCounterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/regions")
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "https://countries-parameters.vercel.app"})
public class RegionController {
    private final RequestCounterService requestCounterService;
    private final CrudService<BaseDto.Response, BaseDto.RequestBody> regionService;

    @GetMapping
    public ResponseEntity<List<BaseDto.Response>> getAllRegions() {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        List<BaseDto.Response> regions = regionService.getAll();
        return new ResponseEntity<>(regions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDto.Response> getRegionById(@PathVariable Integer id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        BaseDto.Response region = regionService.get(id);
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<BaseDto.Response>> createRegion(@RequestBody List<BaseDto.RequestBody> createForms) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        List<BaseDto.Response> responses = regionService.create(createForms);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseDto.Response> updateRegion(@PathVariable Integer id, @RequestBody BaseDto.RequestBody region) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        return new ResponseEntity<>(regionService.update(id, region), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        regionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}