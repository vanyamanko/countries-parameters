package com.manko.counties.service;

import com.manko.counties.dao.CountryParametersRepository;
import com.manko.counties.dao.TimeZoneRepository;
import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.TimeZone;
import com.manko.counties.model.dto.BaseDto;
import com.manko.counties.service.utility.TimeZoneUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.manko.counties.service.utility.TimeZoneUtils.buildTimeZoneResponseFromModel;

@AllArgsConstructor
@Service
public class TimeZoneService implements CrudService<BaseDto.Response, BaseDto.RequestBody> {
    private final TimeZoneRepository timeZoneRepository;
    private final CountryParametersRepository countryParametersRepository;

    @Override
    public List<BaseDto.Response> getAll() {
        return timeZoneRepository.findAll().stream()
                .map(TimeZoneUtils::buildTimeZoneResponseFromModel)
                .toList();
    }

    @Override
    public BaseDto.Response get(Integer id) {
        return buildTimeZoneResponseFromModel(timeZoneRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public BaseDto.Response create(BaseDto.RequestBody createForm) {
        TimeZone timeZone = saveTimeZone(createForm);
        return buildTimeZoneResponseFromModel(timeZone);
    }

    @Override
    public BaseDto.Response update(Integer id, BaseDto.RequestBody updateForm) {
        TimeZone timeZoneModel = timeZoneRepository.findById(id)
                .map(timeZone -> saveTimeZone(timeZone, updateForm))
                .orElseThrow(IllegalArgumentException::new);
        return buildTimeZoneResponseFromModel(timeZoneModel);
    }

    @Override
    public void delete(Integer id) {
        TimeZone timeZone = timeZoneRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        for (CountryParameters countryParameters : timeZone.getCountryParametersSet()) {
            countryParameters.getTimeZones().remove(timeZone);
        }

        timeZoneRepository.deleteById(id);
    }

    private TimeZone saveTimeZone(BaseDto.RequestBody requestBody) {
        TimeZone timeZone = new TimeZone();
        return saveTimeZone(timeZone, requestBody);
    }

    private TimeZone saveTimeZone(TimeZone timeZone, BaseDto.RequestBody requestBody) {
        List<CountryParameters> countryParameters = countryParametersRepository.findByCountries(requestBody.getCountries());
        timeZone.setName(requestBody.getName());
        timeZoneRepository.save(timeZone);

        countryParameters.forEach(country -> {
            Set<TimeZone> timeZoneSet = country.getTimeZones();
            if (timeZoneSet == null) {
                timeZoneSet = new HashSet<>();
            }
            timeZoneSet.add(timeZone);
            country.setTimeZones(timeZoneSet);
            countryParametersRepository.save(country);
        });

        timeZone.setCountryParametersSet(countryParameters);
        return timeZone;
    }
}