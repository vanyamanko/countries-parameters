package com.manko.counties.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CountryParametersDto {
    private String id;
    private String country;
    private Integer code;

    private String region;
    private Set<String> timeZones;
}
