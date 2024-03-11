package com.manko.countries.service;

import com.manko.countries.dao.CountryRepository;
import com.manko.countries.dao.RegionRepository;
import com.manko.countries.model.Country;
import com.manko.countries.model.Region;
import com.manko.countries.model.dto.CountryDto;
import com.manko.countries.service.utility.CountryParametersUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.manko.countries.service.utility.CountryParametersUtils.buildCountryParameters;
import static com.manko.countries.service.utility.CountryParametersUtils.buildCountryParametersDtoFromModel;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;

    @Override
    public CountryDto.Response getCodeByCountryOrId(String countryOrId) {
        Country country = countryRepository
                .findByNameIgnoreCaseOrShortNameIgnoreCase(countryOrId, countryOrId)
                .orElseThrow(IllegalArgumentException::new);
        return buildCountryParametersDtoFromModel(country);
    }

    @Override
    public List<CountryDto.Response> getCountriesByCode(Integer code) {
        List<Country> countryList = countryRepository.findByCode(code)
                .orElseThrow(IllegalArgumentException::new);
        return countryList.stream()
                .map(CountryParametersUtils::buildCountryParametersDtoFromModel)
                .toList();
    }

    @Override
    public List<CountryDto.Response> getAll() {
        return countryRepository.findAll().stream()
                .map(CountryParametersUtils::buildCountryParametersDtoFromModel)
                .toList();
    }

    @Override
    public CountryDto.Response get(Integer id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return buildCountryParametersDtoFromModel(country);
    }

    @Override
    public CountryDto.Response create(CountryDto.RequestBody createForm) {
        Region region = regionRepository.findByRegionName(createForm.getRegion())
                .orElseThrow(IllegalArgumentException::new);

        Country country = buildCountryParameters(createForm, region);
        countryRepository.save(country);
        return buildCountryParametersDtoFromModel(country);
    }

    @Override
    public CountryDto.Response update(Integer id, CountryDto.RequestBody updateForm) {
        Country country = countryRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        Region region = regionRepository.findByRegionName(updateForm.getRegion())
                .orElseThrow(IllegalArgumentException::new);

        Country newCountry = updateCountryParameters(country, updateForm, region);
        countryRepository.save(newCountry);
        return buildCountryParametersDtoFromModel(country);
    }

    @Override
    public void delete(Integer id) {
        countryRepository.deleteById(id);
    }

    private Country updateCountryParameters(Country country, CountryDto.RequestBody updateForm, Region region) {
        if (updateForm.getCountryShortName() != null) {
            country.setShortName(updateForm.getCountryShortName());
        }
        if (updateForm.getCountry() != null) {
            country.setName(updateForm.getCountry());
        }
        if (updateForm.getCode() != null) {
            country.setCode(updateForm.getCode());
        }
        if (updateForm.getRegion() != null) {
            country.setRegion(region);
        }
        return country;
    }
}
