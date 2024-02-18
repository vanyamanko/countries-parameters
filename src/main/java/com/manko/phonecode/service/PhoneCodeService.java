package com.manko.phonecode.service;

import com.manko.phonecode.model.PhoneCode;

import java.util.List;

public interface PhoneCodeService {
    PhoneCode getCodeByCountryOrId(String countryOrId);

    List<PhoneCode> getCountriesByCode(Integer code);
}
