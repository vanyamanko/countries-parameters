package com.manko.countries.service;

import com.manko.countries.dao.CountryRepository;
import com.manko.countries.dao.RegionRepository;
import com.manko.countries.model.Country;
import com.manko.countries.model.Region;
import com.manko.countries.model.dto.BaseDto;
import com.manko.countries.service.utility.RegionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.manko.countries.service.utility.RegionUtils.buildRegionResponseFromModel;

@AllArgsConstructor
@Slf4j
@Service
public class RegionService implements CrudService<BaseDto.Response, BaseDto.RequestBody> {

    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<BaseDto.Response> getAll() {
        return regionRepository.findAll()
                .stream().map(RegionUtils::buildRegionResponseFromModel)
                .toList();
    }

    @Override
    public BaseDto.Response get(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return buildRegionResponseFromModel(region);
    }

    @Override
    public BaseDto.Response create(BaseDto.RequestBody createForm) {
        Region region = saveRegion(new Region(), createForm);
        return buildRegionResponseFromModel(region);
    }

    @Override
    public BaseDto.Response update(Integer id, BaseDto.RequestBody updateForm) {
        Region region = regionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        Region newRegion = saveRegion(region, updateForm);
        return buildRegionResponseFromModel(newRegion);
    }

    @Override
    public void delete(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        for (Country country : region.getCountryParameters()) {
            country.setRegion(null);
            countryRepository.save(country);
        }

        regionRepository.deleteById(id);
    }

    private Region saveRegion(Region region, BaseDto.RequestBody requestBody) {
        List<Country> countryParameters = countryRepository.findByNames(requestBody.getCountries());

        region.setRegionName(requestBody.getName());
        regionRepository.save(region);

        for (Country country : countryParameters) {
            country.setRegion(region);
            countryRepository.save(country);
        }

        region.setCountryParameters(countryParameters);

        return region;
    }
}