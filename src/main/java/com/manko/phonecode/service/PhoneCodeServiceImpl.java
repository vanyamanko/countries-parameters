package com.manko.phonecode.service;

import com.manko.phonecode.dao.PhoneCodeRepository;
import com.manko.phonecode.model.PhoneCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneCodeServiceImpl implements PhoneCodeService {
    private final PhoneCodeRepository phoneCodeDAO;

    public PhoneCodeServiceImpl(PhoneCodeRepository phoneCodeDAO) {
        this.phoneCodeDAO = phoneCodeDAO;
    }

    @Override
    public PhoneCode getCodeByCountryOrId(String countryOrId) {
        return phoneCodeDAO.findByCountryIgnoreCaseOrIdIgnoreCase(countryOrId, countryOrId)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<PhoneCode> getCountriesByCode(Integer code) {
        return phoneCodeDAO.findByCode(code)
                .orElseThrow(IllegalArgumentException::new);
    }
}
