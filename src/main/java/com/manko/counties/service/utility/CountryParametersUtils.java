package com.manko.counties.service.utility;

import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.Region;
import com.manko.counties.model.dto.CountryParametersDto;
import com.manko.counties.model.TimeZone;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CountryParametersUtils {
    public static CountryParametersDto.Response buildCountryParametersDtoFromModel(CountryParameters parameters) {
        Set<String> timeZones = null;
        if (parameters.getTimeZones() != null) {
            timeZones = parameters.getTimeZones().stream()
                    .map(TimeZone::getName)
                    .collect(Collectors.toSet());
        }

        return CountryParametersDto.Response.builder()
                .id(parameters.getId())
                .countryShortName(parameters.getCountryShortName())
                .country(parameters.getCountry())
                .code(parameters.getCode())
                .region(parameters.getRegion().getRegionName())
                .timeZones(timeZones)
                .build();
    }

    public static CountryParameters buildCountryParameters(CountryParametersDto.RequestBody requestBody, Region region) {
        return CountryParameters.builder()
                .countryShortName(requestBody.getCountryShortName())
                .country(requestBody.getCountry())
                .code(requestBody.getCode())
                .region(region)
                .build();
    }
}
