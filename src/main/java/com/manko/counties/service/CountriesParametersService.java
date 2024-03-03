package com.manko.counties.service;

import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.CountryParametersDto;

import java.util.List;

public interface CountriesParametersService {
    CountryParametersDto getCodeByCountryOrId(String countryOrId);

    List<CountryParametersDto> getCountriesByCode(Integer code);
}
