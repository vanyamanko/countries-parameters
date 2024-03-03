package com.manko.counties.service;

import com.manko.counties.model.dto.CountryParametersDto;

import java.util.List;

public interface CountriesParametersService extends CrudService<CountryParametersDto.Response, CountryParametersDto.RequestBody> {
    CountryParametersDto.Response getCodeByCountryOrId(String countryOrId);

    List<CountryParametersDto.Response> getCountriesByCode(Integer code);
}
