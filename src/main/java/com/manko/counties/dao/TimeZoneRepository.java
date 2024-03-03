package com.manko.counties.dao;

import com.manko.counties.model.TimeZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeZoneRepository extends JpaRepository<TimeZone, Integer> {

}
