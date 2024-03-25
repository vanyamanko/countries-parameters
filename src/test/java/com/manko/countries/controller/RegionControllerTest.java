package com.manko.countries.controller;

import com.manko.countries.model.dto.BaseDto;
import com.manko.countries.service.CrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RegionControllerTest {

    @InjectMocks
    private RegionController regionController;

    @Mock
    private CrudService<BaseDto.Response, BaseDto.RequestBody> regionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllRegions() {
        List<BaseDto.Response> regions = Arrays.asList(
                createRegionResponse(1, "First Region"),
                createRegionResponse(2, "Region 2")
        );
        when(regionService.getAll()).thenReturn(regions);

        ResponseEntity<List<BaseDto.Response>> response = regionController.getAllRegions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(regions, response.getBody());
        verify(regionService, times(1)).getAll();
    }

    @Test
    void testGetRegionById() {
        int regionId = 1;
        BaseDto.Response region = createRegionResponse(regionId, "First Region");
        when(regionService.get(regionId)).thenReturn(region);

        ResponseEntity<BaseDto.Response> response = regionController.getRegionById(regionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(region, response.getBody());
        verify(regionService, times(1)).get(regionId);
    }

    @Test
    void testCreateRegion() {
        List<BaseDto.RequestBody> createForms = Arrays.asList(
                new BaseDto.RequestBody("Region 1", null),
                new BaseDto.RequestBody("Region 2", null)
        );
        List<BaseDto.Response> responses = Arrays.asList(
                createRegionResponse(1, "Region 1"),
                createRegionResponse(2, "Region 2")
        );
        when(regionService.create(createForms)).thenReturn(responses);

        ResponseEntity<List<BaseDto.Response>> response = regionController.createRegion(createForms);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responses, response.getBody());
        verify(regionService, times(1)).create(createForms);
    }

    @Test
    void testUpdateRegion() {
        int regionId = 1;
        BaseDto.RequestBody region = new BaseDto.RequestBody("Updated Region", null);
        BaseDto.Response updatedRegion = createRegionResponse(regionId, "Updated Region");
        when(regionService.update(regionId, region)).thenReturn(updatedRegion);

        ResponseEntity<BaseDto.Response> response = regionController.updateRegion(regionId, region);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedRegion, response.getBody());
        verify(regionService, times(1)).update(regionId, region);
    }

    @Test
    void testDeleteRegion() {
        int regionId = 1;

        ResponseEntity<Void> response = regionController.deleteRegion(regionId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(regionService, times(1)).delete(regionId);
    }

    private BaseDto.Response createRegionResponse(int id, String name) {
        BaseDto.Response region = new BaseDto.Response();
        region.setId(id);
        region.setName(name);
        return region;
    }
}