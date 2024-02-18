package com.manko.phonecode.dao;

import com.manko.phonecode.model.PhoneCode;

import java.util.List;
import java.util.Optional;

public interface PhoneCodeDAO {
    Optional<PhoneCode> findByCountryOrId(String country, String id);

    Optional<List<PhoneCode>> findByCode(Integer code);
}
