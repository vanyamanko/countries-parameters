package com.manko.countries.service.utility;

import com.manko.countries.model.Country;
import com.manko.countries.model.Region;
import com.manko.countries.model.dto.CountryDto;
import com.manko.countries.model.TimeZone;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CountryUtils {
    public static CountryDto.Response buildCountryParametersDtoFromModel(Country parameters) {
        Set<String> timeZones = null;
        if (parameters.getTimeZones() != null) {
            timeZones = parameters.getTimeZones().stream()
                    .map(TimeZone::getName)
                    .collect(Collectors.toSet());
        }

        return CountryDto.Response.builder()
                .id(parameters.getId())
                .countryShortName(parameters.getShortName())
                .country(parameters.getName())
                .code(parameters.getCode())
                .region(parameters.getRegion().getName())
                .timeZones(timeZones)
                .build();
    }

    public static Country buildCountryParameters(CountryDto.RequestBody requestBody, Region region) {
        return Country.builder()
                .shortName(requestBody.getCountryShortName())
                .name(requestBody.getCountry())
                .code(requestBody.getCode())
                .region(region)
                .build();
    }
}
