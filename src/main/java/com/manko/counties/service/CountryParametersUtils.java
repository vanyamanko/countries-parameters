package com.manko.counties.service;

import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.CountryParametersDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CountryParametersUtils {
    public static CountryParametersDto buildCountryParametersDtoFromModel(CountryParameters parameters) {
        return CountryParametersDto.builder()
                .id(parameters.getId())
                .country(parameters.getCountry())
                .code(parameters.getCode())
                .region(parameters.getRegion().getRegionName())
                .build();
    }
}
