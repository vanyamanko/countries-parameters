package com.manko.counties.service.utility;

import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.Region;
import com.manko.counties.model.dto.BaseDto;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RegionUtils {
    public static BaseDto.Response buildRegionResponseFromModel(Region region) {
        List<String> countries = null;
        if (region.getCountryParameters() != null) {
            countries = region.getCountryParameters().stream()
                    .map(CountryParameters::getCountry)
                    .toList();
        }
        return BaseDto.Response.builder()
                .id(region.getId())
                .name(region.getRegionName())
                .countries(countries)
                .build();
    }
}
