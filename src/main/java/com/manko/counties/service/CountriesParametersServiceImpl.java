package com.manko.counties.service;

import com.manko.counties.dao.CountryParametersRepository;
import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.CountryParametersDto;
import com.manko.counties.service.utility.CountryParametersUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.manko.counties.service.utility.CountryParametersUtils.buildCountryParametersDtoFromModel;

@Service
@AllArgsConstructor
public class CountriesParametersServiceImpl implements CountriesParametersService {
    private final CountryParametersRepository countryParametersRepository;

    @Override
    public CountryParametersDto getCodeByCountryOrId(String countryOrId) {
        CountryParameters countryParameters = countryParametersRepository.findByCountryIgnoreCaseOrIdIgnoreCase(countryOrId, countryOrId)
                .orElseThrow(IllegalArgumentException::new);
        return buildCountryParametersDtoFromModel(countryParameters);
    }

    @Override
    public List<CountryParametersDto> getCountriesByCode(Integer code) {
        List<CountryParameters> countryParametersList = countryParametersRepository.findByCode(code)
                .orElseThrow(IllegalArgumentException::new);
        return countryParametersList.stream()
                .map(CountryParametersUtils::buildCountryParametersDtoFromModel)
                .toList();
    }
}
