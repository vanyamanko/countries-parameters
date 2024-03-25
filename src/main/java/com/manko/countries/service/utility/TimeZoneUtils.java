package com.manko.countries.service.utility;

import com.manko.countries.model.Country;
import com.manko.countries.model.TimeZone;
import com.manko.countries.model.dto.BaseDto;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TimeZoneUtils {
    public static BaseDto.Response buildTimeZoneResponseFromModel(TimeZone timeZone) {
        List<String> countries = null;
        if (timeZone.getCountryList() != null) {
            countries = timeZone.getCountryList().stream()
                    .map(Country::getName)
                    .toList();
        }
        return BaseDto.Response.builder()
                .id(timeZone.getId())
                .name(timeZone.getName())
                .countries(countries)
                .build();
    }
}
