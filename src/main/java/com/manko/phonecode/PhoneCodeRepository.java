package com.manko.phonecode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneCodeRepository extends JpaRepository<PhoneCode,Long> {
    PhoneCode findByCountryOrId(String country, String Id);
    List<PhoneCode> findByCode(Integer code);
}
