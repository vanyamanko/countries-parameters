package com.manko.counties.dao;

import com.manko.counties.model.CountryParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryParametersRepository extends JpaRepository<CountryParameters, Long> {
    Optional<CountryParameters> findByCountryIgnoreCaseOrIdIgnoreCase(String country, String id);

    Optional<List<CountryParameters>> findByCode(Integer code);
}
