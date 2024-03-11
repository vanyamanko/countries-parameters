package com.manko.countries.dao;

import com.manko.countries.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query(value = "SELECT * FROM country WHERE LOWER(name) = LOWER(:name) OR LOWER(short_name) = LOWER(:shortName)", nativeQuery = true)
    Optional<Country> findByNameIgnoreCaseOrShortNameIgnoreCase(@Param("name") String name, @Param("shortName") String shortName);

    @Query(value = "select * from country o where o.name in (?1)", nativeQuery = true)
    List<Country> findByNames(@Param("1") List<String> names);

    Optional<List<Country>> findByCode(Integer code);
}