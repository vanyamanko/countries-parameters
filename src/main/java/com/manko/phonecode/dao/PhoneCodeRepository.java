package com.manko.phonecode.dao;

import com.manko.phonecode.model.PhoneCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhoneCodeRepository extends JpaRepository<PhoneCode, Long> {
    Optional<PhoneCode> findByCountryIgnoreCaseOrIdIgnoreCase(String country, String id);

    Optional<List<PhoneCode>> findByCode(Integer code);
}
