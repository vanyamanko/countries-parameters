package com.manko.countries.service;

import com.manko.countries.component.Cache;
import com.manko.countries.dao.CountryRepository;
import com.manko.countries.dao.RegionRepository;
import com.manko.countries.model.Country;
import com.manko.countries.model.Region;
import com.manko.countries.model.dto.CountryDto;
import com.manko.countries.service.utility.CountryParametersUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.manko.countries.service.utility.CountryParametersUtils.buildCountryParameters;
import static com.manko.countries.service.utility.CountryParametersUtils.buildCountryParametersDtoFromModel;

@Service
@AllArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {
    private static final String CASH_LOG = "Cached data found for key: ";
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final Cache cache;

    @Override
    public CountryDto.Response getCodeByCountryOrId(String countryOrId) {
        String key = "countryOrId" + countryOrId;
        CountryDto.Response cachedData = (CountryDto.Response) cache.get(key);
        if (cachedData != null) {
            String logString = CASH_LOG + key;
            log.info(logString);
            return cachedData;
        }
        Country country = countryRepository
                .findByNameIgnoreCaseOrShortNameIgnoreCase(countryOrId, countryOrId)
                .orElseThrow(IllegalArgumentException::new);
        CountryDto.Response data = buildCountryParametersDtoFromModel(country);
        cache.put(key, data);
        return data;
    }

    public List<CountryDto.Response> getCountriesByCode(Integer code) {
        String key = "code" + code;

        List<CountryDto.Response> cachedData = (List<CountryDto.Response>) cache.get(key);
        if (cachedData != null) {
            String logString = CASH_LOG + key;
            log.info(logString);
            return cachedData;
        }

        List<Country> countryList = countryRepository.findByCode(code)
                .orElseThrow(IllegalArgumentException::new);

        List<CountryDto.Response> data = countryList.stream()
                .map(CountryParametersUtils::buildCountryParametersDtoFromModel)
                .toList();

        cache.put(key, data);

        return data;
    }

    @Override
    public List<CountryDto.Response> getAll() {
        return countryRepository.findAll().stream()
                .map(CountryParametersUtils::buildCountryParametersDtoFromModel)
                .toList();
    }

    @Override
    public CountryDto.Response get(Integer id) {
        String key = "id" + id;
        CountryDto.Response cachedData = (CountryDto.Response) cache.get(key);
        if (cachedData != null) {
            String logString = CASH_LOG + key;
            log.info(logString);
            return cachedData;
        }
        Country country = countryRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        CountryDto.Response data = buildCountryParametersDtoFromModel(country);

        cache.put(key, data);
        return data;
    }

    @Override
    public CountryDto.Response create(CountryDto.RequestBody createForm) {
        if (countryRepository.findByNameIgnoreCaseOrShortNameIgnoreCase(createForm.getCountry(),
                        createForm.getCountryShortName())
                .isPresent()) {
            throw new IllegalArgumentException("Duplicate country");
        }
        Region region = regionRepository.findByName(createForm.getRegion())
                .orElseThrow(IllegalArgumentException::new);
        Country country = buildCountryParameters(createForm, region);
        countryRepository.save(country);
        return buildCountryParametersDtoFromModel(country);
    }

    @Override
    public CountryDto.Response update(Integer id, CountryDto.RequestBody updateForm) {
        cache.clear();
        Country country = countryRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        Region region = regionRepository.findByName(updateForm.getRegion())
                .orElseThrow(IllegalArgumentException::new);
        Country newCountry = updateCountryParameters(country, updateForm, region);
        countryRepository.save(newCountry);
        return buildCountryParametersDtoFromModel(country);
    }

    @Override
    public void delete(Integer id) {
        cache.clear();
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
