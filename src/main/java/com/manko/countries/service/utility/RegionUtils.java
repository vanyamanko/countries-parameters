package com.manko.countries.service.utility;

import com.manko.countries.model.Country;
import com.manko.countries.model.Region;
import com.manko.countries.model.dto.BaseDto;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RegionUtils {
    public static BaseDto.Response buildRegionResponseFromModel(Region region) {
        List<String> countries = null;
        if (region.getCountryParameters() != null) {
            countries = region.getCountryParameters().stream()
                    .map(Country::getName)
                    .toList();
        }
        return BaseDto.Response.builder()
                .id(region.getId())
                .name(region.getName())
                .countries(countries)
                .build();
    }
}
