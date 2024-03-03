package com.manko.counties.model.dto;

import lombok.*;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TimeZoneDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class RequestBody {
        private String name;
        private List<String> countries;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class Response {
        private Integer id;
        private String name;
        private List<String> countries;
    }
}
