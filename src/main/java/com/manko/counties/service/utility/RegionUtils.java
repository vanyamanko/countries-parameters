package com.manko.counties.service.utility;

import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.Region;
import com.manko.counties.model.dto.RegionDto;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RegionUtils {
    public static RegionDto.Response buildRegionResponseFromModel(Region region) {
        List<String> countries = null;
        if (region.getCountryParameters() != null) {
            countries = region.getCountryParameters().stream()
                    .map(CountryParameters::getCountry)
                    .toList();
        }
        return RegionDto.Response.builder()
                .id(region.getId())
                .name(region.getRegionName())
                .countries(countries)
                .build();
    }
}
