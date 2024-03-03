package com.manko.counties.service;

import com.manko.counties.dao.CountryParametersRepository;
import com.manko.counties.dao.RegionRepository;
import com.manko.counties.model.CountryParameters;
import com.manko.counties.model.Region;
import com.manko.counties.model.dto.RegionDto;
import com.manko.counties.service.utility.RegionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.manko.counties.service.utility.RegionUtils.buildRegionResponseFromModel;

@AllArgsConstructor
@Slf4j
@Service
public class RegionService implements CrudService<RegionDto.Response, RegionDto.RequestBody> {

    private final RegionRepository regionRepository;
    private final CountryParametersRepository countryParametersRepository;

    @Override
    public List<RegionDto.Response> getAll() {
        return regionRepository.findAll()
                .stream().map(RegionUtils::buildRegionResponseFromModel)
                .toList();
    }

    @Override
    public RegionDto.Response get(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return buildRegionResponseFromModel(region);
    }

    @Override
    public RegionDto.Response create(RegionDto.RequestBody createForm) {
        Region region = saveRegion(new Region(), createForm);
        return buildRegionResponseFromModel(region);
    }

    @Override
    public RegionDto.Response update(Integer id, RegionDto.RequestBody updateForm) {
        Region region = regionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        Region newRegion = saveRegion(region, updateForm);
        return buildRegionResponseFromModel(newRegion);
    }

    @Override
    public void delete(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        for (CountryParameters countryParameters : region.getCountryParameters()) {
            countryParameters.setRegion(null);
            countryParametersRepository.save(countryParameters);
        }

        regionRepository.deleteById(id);
    }

    private Region saveRegion(Region region, RegionDto.RequestBody requestBody) {
        List<CountryParameters> countryParameters = countryParametersRepository.findByCountries(requestBody.getCountries());

        region.setRegionName(requestBody.getName());
        regionRepository.save(region);

        for (CountryParameters country : countryParameters) {
            country.setRegion(region);
            countryParametersRepository.save(country);
        }

        region.setCountryParameters(countryParameters);

        return region;
    }
}