package com.manko.countries.service;

import com.manko.countries.model.dto.CountryDto;

import java.util.List;

public interface CountryService extends CrudService<CountryDto.Response, CountryDto.RequestBody> {
    CountryDto.Response getCodeByCountryOrId(String countryOrId);

    List<CountryDto.Response> getCountriesByCode(Integer code);
}
