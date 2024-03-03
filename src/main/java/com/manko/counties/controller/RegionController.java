package com.manko.counties.controller;

import com.manko.counties.model.dto.RegionDto;
import com.manko.counties.service.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/regions")
public class RegionController {
    private final CrudService<RegionDto.Response, RegionDto.RequestBody> regionService;

    @GetMapping
    public ResponseEntity<List<RegionDto.Response>> getAllRegions() {
        List<RegionDto.Response> regions = regionService.getAll();
        return new ResponseEntity<>(regions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDto.Response> getRegionById(@PathVariable Integer id) {
        RegionDto.Response region = regionService.get(id);
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RegionDto.Response> createRegion(@RequestBody RegionDto.RequestBody region) {
        return new ResponseEntity<>(regionService.create(region), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionDto.Response> updateRegion(@PathVariable Integer id, @RequestBody RegionDto.RequestBody region) {
        return new ResponseEntity<>(regionService.update(id, region), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
        regionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}