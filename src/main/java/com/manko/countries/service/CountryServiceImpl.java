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
    private static final String COUNTRY_OR_ID_PREFIX = "countryOrId";
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final Cache cache;

    @Override
    public CountryDto.Response getCodeByCountryOrId(String countryOrId) {
        String key = COUNTRY_OR_ID_PREFIX + countryOrId;
        CountryDto.Response cachedData = (CountryDto.Response) cache.get(key);
        if (cachedData != null) {
            return cachedData;
        }
        Country country = countryRepository
                .findByNameIgnoreCaseOrShortNameIgnoreCase(countryOrId, countryOrId)
                .orElseThrow(IllegalArgumentException::new);
        cache.put(key, buildCountryParametersDtoFromModel(country));
        return buildCountryParametersDtoFromModel(country);
    }

    public List<CountryDto.Response> getCountriesByCode(Integer code) {
        String key = "code" + code;

        List<CountryDto.Response> cachedData = (List<CountryDto.Response>) cache.get(key);
        if (cachedData != null) {
            log.info("Cached data found for key: {}", key);
            return cachedData;
        }

        List<Country> countryList = countryRepository.findByCode(code)
                .orElseThrow(IllegalArgumentException::new);

        List<CountryDto.Response> countryDtoList = countryList.stream()
                .map(CountryParametersUtils::buildCountryParametersDtoFromModel)
                .toList();

        cache.put(key, countryDtoList);

        return countryDtoList;
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
            log.info("Cached data found for key: {}", key);
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
        String keyId = "id" + id;
        String keyCountry = COUNTRY_OR_ID_PREFIX + updateForm.getCountry();
        String keyShortName = COUNTRY_OR_ID_PREFIX + updateForm.getCountryShortName();
        String keyCode = "code" + updateForm.getCode();
        cache.remove(keyId);
        cache.remove(keyCountry);
        cache.remove(keyShortName);
        cache.remove(keyCode);
        Country country = countryRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        Region region = regionRepository.findByName(updateForm.getRegion())
                .orElseThrow(IllegalArgumentException::new);
        Country newCountry = updateCountryParameters(country, updateForm, region);
        countryRepository.save(newCountry);
        CountryDto.Response data = buildCountryParametersDtoFromModel(country);
        cache.put(keyId, data);
        cache.put(keyCountry, data);
        cache.put(keyShortName, data);
        cache.put(keyCode, data);
        return data;
    }

    @Override
    public void delete(Integer id) {
        String key = "id" + id;
        cache.remove(key);
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
