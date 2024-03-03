package com.manko.counties.service;

import com.manko.counties.dao.CountryParametersRepository;
import com.manko.counties.dao.RegionRepository;
import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.Region;
import com.manko.counties.model.dto.CountryParametersDto;
import com.manko.counties.service.utility.CountryParametersUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.manko.counties.service.utility.CountryParametersUtils.buildCountryParameters;
import static com.manko.counties.service.utility.CountryParametersUtils.buildCountryParametersDtoFromModel;

@Service
@AllArgsConstructor
public class CountriesParametersServiceImpl implements CountriesParametersService {
    private final CountryParametersRepository countryParametersRepository;
    private final RegionRepository regionRepository;

    @Override
    public CountryParametersDto.Response getCodeByCountryOrId(String countryOrId) {
        CountryParameters countryParameters = countryParametersRepository
                .findByCountryIgnoreCaseOrCountryShortNameIgnoreCase(countryOrId, countryOrId)
                .orElseThrow(IllegalArgumentException::new);
        return buildCountryParametersDtoFromModel(countryParameters);
    }

    @Override
    public List<CountryParametersDto.Response> getCountriesByCode(Integer code) {
        List<CountryParameters> countryParametersList = countryParametersRepository.findByCode(code)
                .orElseThrow(IllegalArgumentException::new);
        return countryParametersList.stream()
                .map(CountryParametersUtils::buildCountryParametersDtoFromModel)
                .toList();
    }

    @Override
    public List<CountryParametersDto.Response> getAll() {
        return countryParametersRepository.findAll().stream()
                .map(CountryParametersUtils::buildCountryParametersDtoFromModel)
                .toList();
    }

    @Override
    public CountryParametersDto.Response get(Integer id) {
        CountryParameters countryParameters = countryParametersRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return buildCountryParametersDtoFromModel(countryParameters);
    }

    @Override
    public CountryParametersDto.Response create(CountryParametersDto.RequestBody createForm) {
        Region region = regionRepository.findByRegionName(createForm.getRegion())
                .orElseThrow(IllegalArgumentException::new);

        CountryParameters countryParameters = buildCountryParameters(createForm, region);
        countryParametersRepository.save(countryParameters);
        return buildCountryParametersDtoFromModel(countryParameters);
    }

    @Override
    public CountryParametersDto.Response update(Integer id, CountryParametersDto.RequestBody updateForm) {
        CountryParameters countryParameters = countryParametersRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        Region region = regionRepository.findByRegionName(updateForm.getRegion())
                .orElseThrow(IllegalArgumentException::new);

        CountryParameters newCountryParameters = updateCountryParameters(countryParameters, updateForm, region);
        countryParametersRepository.save(newCountryParameters);
        return buildCountryParametersDtoFromModel(countryParameters);
    }

    @Override
    public void delete(Integer id) {
        countryParametersRepository.deleteById(id);
    }

    private CountryParameters updateCountryParameters(CountryParameters countryParameters, CountryParametersDto.RequestBody updateForm, Region region) {
        if (updateForm.getCountryShortName() != null) {
            countryParameters.setCountryShortName(updateForm.getCountryShortName());
        }
        if (updateForm.getCountry() != null) {
            countryParameters.setCountry(updateForm.getCountry());
        }
        if (updateForm.getCode() != null) {
            countryParameters.setCode(updateForm.getCode());
        }
        if (updateForm.getRegion() != null) {
            countryParameters.setRegion(region);
        }
        return countryParameters;
    }
}
