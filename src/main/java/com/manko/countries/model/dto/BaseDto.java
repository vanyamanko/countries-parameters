package com.manko.countries.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class BaseDto {
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
