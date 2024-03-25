package com.manko.countries.service;

import com.manko.countries.component.Cache;
import com.manko.countries.dao.CountryRepository;
import com.manko.countries.dao.TimeZoneRepository;
import com.manko.countries.model.Country;
import com.manko.countries.model.TimeZone;
import com.manko.countries.model.dto.BaseDto;
import com.manko.countries.service.utility.TimeZoneUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.manko.countries.service.utility.TimeZoneUtils.buildTimeZoneResponseFromModel;

@AllArgsConstructor
@Service
@Slf4j
public class TimeZoneService implements CrudService<BaseDto.Response, BaseDto.RequestBody> {
    private final TimeZoneRepository timeZoneRepository;
    private final CountryRepository countryRepository;
    private final Cache cache;

    @Override
    public List<BaseDto.Response> getAll() {
        return timeZoneRepository.findAll().stream()
                .map(TimeZoneUtils::buildTimeZoneResponseFromModel)
                .toList();
    }

    @Override
    public BaseDto.Response get(Integer id) {
        String key = "id" + id;
        BaseDto.Response cachedData = (BaseDto.Response) cache.get(key);
        if (cachedData != null) {
            log.info("Cached data found for key: {}", key);
            return cachedData;
        }
        BaseDto.Response data = buildTimeZoneResponseFromModel(timeZoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("error 500 (NOT FOUND ID IN DB)")));

        cache.put(key, data);
        return data;
    }

    @Override
    public List<BaseDto.Response> create(List<BaseDto.RequestBody> createForms) {
        cache.clear();

        List<BaseDto.Response> responses = new ArrayList<>();

        for (BaseDto.RequestBody createForm : createForms) {
            TimeZone timeZone = saveTimeZone(createForm);
            BaseDto.Response response = TimeZoneUtils.buildTimeZoneResponseFromModel(timeZone);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public BaseDto.Response update(Integer id, BaseDto.RequestBody updateForm) {
        cache.clear();
        TimeZone timeZoneModel = timeZoneRepository.findById(id)
                .map(timeZone -> saveTimeZone(timeZone, updateForm))
                .orElseThrow(IllegalArgumentException::new);
        return buildTimeZoneResponseFromModel(timeZoneModel);
    }

    @Override
    public void delete(Integer id) {
        cache.clear();
        TimeZone timeZone = timeZoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("error 500 (NOT FOUND ID IN DB)"));

        for (Country country : timeZone.getCountryList()) {
            country.getTimeZones().remove(timeZone);
        }

        timeZoneRepository.deleteById(id);
    }

    private TimeZone saveTimeZone(BaseDto.RequestBody requestBody) {
        TimeZone timeZone = new TimeZone();
        return saveTimeZone(timeZone, requestBody);
    }

    public TimeZone saveTimeZone(TimeZone timeZone, BaseDto.RequestBody requestBody) {
        List<Country> countryParameters = countryRepository.findByNames(requestBody.getCountries());
        timeZone.setName(requestBody.getName());
        timeZoneRepository.save(timeZone);

        countryParameters.forEach(country -> {
            Set<TimeZone> timeZoneSet = country.getTimeZones();
            if (timeZoneSet == null) {
                timeZoneSet = new HashSet<>();
            }
            timeZoneSet.add(timeZone);
            country.setTimeZones(timeZoneSet);
            countryRepository.save(country);
        });

        timeZone.setCountryList(countryParameters);
        return timeZone;
    }
}