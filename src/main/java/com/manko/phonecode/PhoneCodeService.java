package com.manko.phonecode;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneCodeService {

    private final PhoneCodeRepository phoneCodeRepository;

    public PhoneCodeService(PhoneCodeRepository phoneCodeRepository) {
        this.phoneCodeRepository = phoneCodeRepository;
    }

    public PhoneCode getCodeByCountryOrId(String countryOrId) {
        return phoneCodeRepository.findByCountryOrId(countryOrId, countryOrId);
    }

    public List<PhoneCode> getCountriesByCode(Integer code) {
        return phoneCodeRepository.findByCode(code);
    }
}
