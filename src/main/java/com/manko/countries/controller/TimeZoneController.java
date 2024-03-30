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
@RequestMapping("api/v1/timezones")
@Slf4j
public class TimeZoneController {
    private final CrudService<BaseDto.Response, BaseDto.RequestBody> timeZoneService;

    private final RequestCounterService requestCounterService;

    @GetMapping
    public ResponseEntity<List<BaseDto.Response>> getAllTimeZones() {
        log.info(String.valueOf(requestCounterService.increment()));
        List<BaseDto.Response> timeZones = timeZoneService.getAll();
        return new ResponseEntity<>(timeZones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDto.Response> getTimeZoneById(@PathVariable Integer id) {
        log.info(String.valueOf(requestCounterService.increment()));
        BaseDto.Response timeZone = timeZoneService.get(id);
        return new ResponseEntity<>(timeZone, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<BaseDto.Response>> createTimeZone(@RequestBody List<BaseDto.RequestBody> createForms) {
        log.info(String.valueOf(requestCounterService.increment()));
        List<BaseDto.Response> responses = timeZoneService.create(createForms);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseDto.Response> updateTimeZone(@PathVariable Integer id, @RequestBody BaseDto.RequestBody timeZone) {
        log.info(String.valueOf(requestCounterService.increment()));
        return new ResponseEntity<>(timeZoneService.update(id, timeZone), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeZone(@PathVariable Integer id) {
        log.info(String.valueOf(requestCounterService.increment()));
        timeZoneService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}