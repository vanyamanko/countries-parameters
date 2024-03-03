package com.manko.counties.service;

import com.manko.counties.dao.CountryParametersRepository;
import com.manko.counties.dao.TimeZoneRepository;
import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.TimeZone;
import com.manko.counties.model.dto.TimeZoneDto;
import com.manko.counties.service.utility.TimeZoneUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.manko.counties.service.utility.TimeZoneUtils.buildTimeZoneResponseFromModel;

@AllArgsConstructor
@Slf4j
@Service
public class TimeZoneService implements CrudService<TimeZoneDto.Response, TimeZoneDto.RequestBody> {
    private final TimeZoneRepository timeZoneRepository;
    private final CountryParametersRepository countryParametersRepository;

    @Override
    public List<TimeZoneDto.Response> getAll() {
        return timeZoneRepository.findAll().stream()
                .map(TimeZoneUtils::buildTimeZoneResponseFromModel)
                .toList();
    }

    @Override
    public TimeZoneDto.Response get(Integer id) {
        return buildTimeZoneResponseFromModel(timeZoneRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public TimeZoneDto.Response create(TimeZoneDto.RequestBody createForm) {
        List<CountryParameters> countryParameters = countryParametersRepository.findByCountries(createForm.getCountries());
        TimeZone timeZone = new TimeZone(createForm.getName());
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
        return buildTimeZoneResponseFromModel(timeZone);
    }

    @Override
    public TimeZoneDto.Response update(Integer id, TimeZoneDto.RequestBody updateForm) {
        TimeZone timeZoneModel = timeZoneRepository.findById(id)
                .map(timeZone -> {
                    List<CountryParameters> countryParameters = countryParametersRepository.findByCountries(updateForm.getCountries());
                    timeZone.setName(updateForm.getName());
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
                })
                .orElseThrow(IllegalArgumentException::new);
        return buildTimeZoneResponseFromModel(timeZoneModel);
    }

    @Override
    public void delete(Integer id) {
        timeZoneRepository.deleteById(id);
    }
}