package com.manko.countries.service;

import com.manko.countries.component.Cache;
import com.manko.countries.dao.CountryRepository;
import com.manko.countries.dao.RegionRepository;
import com.manko.countries.model.Region;
import com.manko.countries.model.dto.BaseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RegionServiceTest {
    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private Cache cache;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        regionService = new RegionService(regionRepository, countryRepository, cache);
    }
    @Test
    void testGetAll() {
        List<Region> regions = new ArrayList<>();
        Region region1 = new Region();
        region1.setId(1);
        region1.setName("Region 1");
        regions.add(region1);

        Region region2 = new Region();
        region2.setId(2);
        region2.setName("Region 2");
        regions.add(region2);

        when(regionRepository.findAll()).thenReturn(regions);

        List<BaseDto.Response> responses = regionService.getAll();

        assertEquals(2, responses.size());
        assertEquals("Region 1", responses.get(0).getName());
        assertEquals("Region 2", responses.get(1).getName());

        verify(regionRepository, times(1)).findAll();
    }

    @Test
    void testGet() {
        int regionId = 1;
        String cacheKey = "id" + regionId;

        Region region = new Region();
        region.setId(regionId);
        region.setName("Test Region");

        BaseDto.Response expectedResponse = new BaseDto.Response();
        expectedResponse.setId(regionId);
        expectedResponse.setName("Test Region");

        when(cache.get(cacheKey)).thenReturn(null);
        when(regionRepository.findById(regionId)).thenReturn(Optional.of(region));
        // Use doNothing to stub the put method
        doNothing().when(cache).put(cacheKey, expectedResponse);

        BaseDto.Response response = regionService.get(regionId);

        assertEquals(expectedResponse, response);

        verify(cache, times(1)).get(cacheKey);
        verify(regionRepository, times(1)).findById(regionId);
        verify(cache, times(1)).put(cacheKey, expectedResponse);
    }
}