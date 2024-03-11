package com.manko.countries.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class CountryDto {
    @Data
    @Builder
    public class Response {
        private Integer id;
        private String countryShortName;
        private String country;
        private Integer code;

        private String region;
        private Set<String> timeZones;
    }

    @Data
    @Builder
    public class RequestBody {
        private String countryShortName;
        private String country;
        private Integer code;
        private String region;
    }
}
