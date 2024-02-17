package com.manko.phonecode;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneCodeService {

    private final PhoneCodeRepository phoneCodeRepository;

    public PhoneCodeService(PhoneCodeRepository phoneCodeRepository) {
        this.phoneCodeRepository = phoneCodeRepository;
    }

    public Integer getCodeByCountryOrId(String countryOrId) {
        PhoneCode phoneCode = phoneCodeRepository.findByCountryOrId(countryOrId, countryOrId);
        if (phoneCode != null) {
            return phoneCode.getCode();
        } else {
            return null;
        }
    }

    public List<String> getCountriesByCode(Integer code) {
        List<PhoneCode> phoneCodes = phoneCodeRepository.findByCode(code);
        List<String> countries = new ArrayList<>();
        for (PhoneCode phoneCode : phoneCodes) {
            String country = phoneCode.getCountry() + " " + phoneCode.getId();
            countries.add(country);
        }
        if (countries.isEmpty()) {
            countries.add("Nothing found :(");
        }
        return countries;
    }
}
