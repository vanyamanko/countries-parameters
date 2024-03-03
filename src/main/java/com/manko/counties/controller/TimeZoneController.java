package com.manko.counties.controller;

import com.manko.counties.model.dto.TimeZoneDto;
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
    private final CrudService<TimeZoneDto.Response, TimeZoneDto.RequestBody> timeZoneService;

    @GetMapping
    public ResponseEntity<List<TimeZoneDto.Response>> getAllTimeZones() {
        List<TimeZoneDto.Response> timeZones = timeZoneService.getAll();
        return new ResponseEntity<>(timeZones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeZoneDto.Response> getTimeZoneById(@PathVariable Integer id) {
        TimeZoneDto.Response timeZone = timeZoneService.get(id);
        return new ResponseEntity<>(timeZone, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TimeZoneDto.Response> createTimeZone(@RequestBody TimeZoneDto.RequestBody timeZone) {
        return new ResponseEntity<>(timeZoneService.create(timeZone), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeZoneDto.Response> updateTimeZone(@PathVariable Integer id, @RequestBody TimeZoneDto.RequestBody timeZone) {
        return new ResponseEntity<>(timeZoneService.update(id, timeZone), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeZone(@PathVariable Integer id) {
        timeZoneService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}