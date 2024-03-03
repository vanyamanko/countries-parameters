package com.manko.counties.dao;

import com.manko.counties.model.CountryParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CountryParametersRepository extends JpaRepository<CountryParameters, Long> {
    Optional<CountryParameters> findByCountryIgnoreCaseOrIdIgnoreCase(String country, String id);

    @Query(value = "select * from countries_parameters o where o.country in (?1)", nativeQuery = true)
    List<CountryParameters> findByCountries(@Param("1") List<String> countries);

    Optional<List<CountryParameters>> findByCode(Integer code);
}
