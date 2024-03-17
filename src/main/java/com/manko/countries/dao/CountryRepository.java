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
    @Query("SELECT o FROM Country o WHERE LOWER(o.name) = LOWER(:name) OR LOWER(o.shortName) = LOWER(:shortName)")
    Optional<Country> findByNameIgnoreCaseOrShortNameIgnoreCase(@Param("name") String name, @Param("shortName") String shortName);

    @Query(value = "select * from country o where o.name in (:name)", nativeQuery = true)
    List<Country> findByNames(@Param("name") List<String> names);

    List<Country> findByCode(Integer code);
}