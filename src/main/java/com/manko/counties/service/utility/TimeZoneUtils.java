package com.manko.counties.service.utility;

import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.TimeZone;
import com.manko.counties.model.dto.BaseDto;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TimeZoneUtils {
    public static BaseDto.Response buildTimeZoneResponseFromModel(TimeZone timeZone) {
        List<String> countries = null;
        if (timeZone.getCountryParametersSet() != null) {
            countries = timeZone.getCountryParametersSet().stream()
                    .map(CountryParameters::getCountry)
                    .toList();
        }
        return BaseDto.Response.builder()
                .id(timeZone.getId())
                .name(timeZone.getName())
                .countries(countries)
                .build();
    }
}
