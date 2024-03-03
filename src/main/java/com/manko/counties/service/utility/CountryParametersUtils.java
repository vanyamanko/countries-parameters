package com.manko.counties.service.utility;

import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.CountryParametersDto;
import com.manko.counties.model.TimeZone;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CountryParametersUtils {
    public static CountryParametersDto buildCountryParametersDtoFromModel(CountryParameters parameters) {
        Set<String> timeZones = parameters.getTimeZones().stream()
                .map(TimeZone::getNameTimeZone)
                .collect(Collectors.toSet());
        return CountryParametersDto.builder()
                .id(parameters.getId())
                .country(parameters.getCountry())
                .code(parameters.getCode())
                .region(parameters.getRegion().getRegionName())
                .timeZones(timeZones)
                .build();
    }
}
