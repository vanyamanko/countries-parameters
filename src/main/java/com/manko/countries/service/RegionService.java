package com.manko.countries.service;

import com.manko.countries.component.Cache;
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
    private final Cache cache;

    @Override
    public List<BaseDto.Response> getAll() {
        return regionRepository.findAll()
                .stream().map(RegionUtils::buildRegionResponseFromModel)
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
        Region region = regionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        BaseDto.Response data = buildRegionResponseFromModel(region);
        cache.put(key, data);
        return data;
    }

    @Override
    public BaseDto.Response create(BaseDto.RequestBody createForm) {
        if (regionRepository.findByName(createForm.getName())
                .isPresent()) {
            throw new IllegalArgumentException("Duplicate region");
        }
        Region region = saveRegion(new Region(), createForm);
        return buildRegionResponseFromModel(region);
    }

    @Override
    public BaseDto.Response update(Integer id, BaseDto.RequestBody updateForm) {
        String key = "id" + id;
        cache.remove(key);
        Region region = regionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        Region newRegion = saveRegion(region, updateForm);
        BaseDto.Response data = buildRegionResponseFromModel(newRegion);
        cache.put(key, data);
        return data;
    }

    @Override
    public void delete(Integer id) {
        String key = "id" + id;
        cache.remove(key);
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

        region.setName(requestBody.getName());
        regionRepository.save(region);

        for (Country country : countryParameters) {
            country.setRegion(region);
            countryRepository.save(country);
        }

        region.setCountryParameters(countryParameters);

        return region;
    }
}