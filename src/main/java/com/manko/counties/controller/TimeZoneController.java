package com.manko.counties.controller;

import com.manko.counties.model.dto.BaseDto;
import com.manko.counties.service.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/timezones")
public class TimeZoneController {
    private final CrudService<BaseDto.Response, BaseDto.RequestBody> timeZoneService;

    @GetMapping
    public ResponseEntity<List<BaseDto.Response>> getAllTimeZones() {
        List<BaseDto.Response> timeZones = timeZoneService.getAll();
        return new ResponseEntity<>(timeZones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDto.Response> getTimeZoneById(@PathVariable Integer id) {
        BaseDto.Response timeZone = timeZoneService.get(id);
        return new ResponseEntity<>(timeZone, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseDto.Response> createTimeZone(@RequestBody BaseDto.RequestBody timeZone) {
        return new ResponseEntity<>(timeZoneService.create(timeZone), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseDto.Response> updateTimeZone(@PathVariable Integer id, @RequestBody BaseDto.RequestBody timeZone) {
        return new ResponseEntity<>(timeZoneService.update(id, timeZone), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeZone(@PathVariable Integer id) {
        timeZoneService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}