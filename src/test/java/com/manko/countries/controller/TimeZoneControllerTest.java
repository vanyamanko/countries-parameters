package com.manko.countries.controller;

import com.manko.countries.model.dto.BaseDto;
import com.manko.countries.service.CrudService;
import com.manko.countries.service.RequestCounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimeZoneControllerTest {

    @InjectMocks
    private TimeZoneController timeZoneController;

    @Mock
    private CrudService<BaseDto.Response, BaseDto.RequestBody> timeZoneService;

    @Mock
    private RequestCounterService requestCounterService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllTimeZone() {
        List<BaseDto.Response> timeZones = Arrays.asList(
                createTimeZoneResponse(1, "nameFirst"),
                createTimeZoneResponse(2, "nameSecond")
        );
        when(timeZoneService.getAll()).thenReturn(timeZones);

        ResponseEntity<List<BaseDto.Response>> response = timeZoneController.getAllTimeZones();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(timeZones, response.getBody());

        verify(timeZoneService, times(1)).getAll();
    }

    @Test
    void testGetTimeZoneById() {
        Integer id = 1;
        BaseDto.Response timeZone = createTimeZoneResponse(id, "First timeZone");
        when(timeZoneService.get(id)).thenReturn(timeZone);

        ResponseEntity<BaseDto.Response> response = timeZoneController.getTimeZoneById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(timeZone, response.getBody());
        verify(timeZoneService, times(1)).get(id);
    }

    @Test
    void testCreateTimeZone() {
        List<BaseDto.RequestBody> createForms = Arrays.asList(
                new BaseDto.RequestBody("nameFirst", null),
                new BaseDto.RequestBody("nameSecond", null)
        );
        List<BaseDto.Response> responses = Arrays.asList(
                createTimeZoneResponse(1, "nameFirst"),
                createTimeZoneResponse(2, "nameSecond")
        );
        when(timeZoneService.create(createForms)).thenReturn(responses);

        ResponseEntity<List<BaseDto.Response>> response = timeZoneController.createTimeZone(createForms);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responses, response.getBody());
        verify(timeZoneService, times(1)).create(createForms);
    }



    @Test
    void testUpdateTimeZone() {
        int id = 1;
        BaseDto.RequestBody timeZone = new BaseDto.RequestBody("Updated timeZone", null);
        BaseDto.Response updatedTimeZone = createTimeZoneResponse(id, "Updated timeZone");
        when(timeZoneService.update(id, timeZone)).thenReturn(updatedTimeZone);

        ResponseEntity<BaseDto.Response> response = timeZoneController.updateTimeZone(id, timeZone);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedTimeZone, response.getBody());
        verify(timeZoneService, times(1)).update(id, timeZone);
    }

    @Test
    void testDeleteTimeZone() {
        int id = 1;

        ResponseEntity<Void> response = timeZoneController.deleteTimeZone(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

        private BaseDto.Response createTimeZoneResponse(int id, String name) {
        BaseDto.Response timeZone = new BaseDto.Response();
        timeZone.setId(id);
        timeZone.setName(name);
        return timeZone;
    }

}