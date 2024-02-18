package com.manko.phonecode.service;

import com.manko.phonecode.dao.PhoneCodeDAO;
import com.manko.phonecode.model.PhoneCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneCodeServiceImpl implements PhoneCodeService {
    private final PhoneCodeDAO phoneCodeDAO;

    public PhoneCodeServiceImpl(PhoneCodeDAO phoneCodeDAO) {
        this.phoneCodeDAO = phoneCodeDAO;
    }

    public PhoneCode getCodeByCountryOrId(String countryOrId) {
        return phoneCodeDAO.findByCountryOrId(countryOrId, countryOrId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<PhoneCode> getCountriesByCode(Integer code) {
        return phoneCodeDAO.findByCode(code)
                .orElseThrow(IllegalArgumentException::new);
    }
}
