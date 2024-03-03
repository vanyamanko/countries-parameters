package com.manko.counties.dao;

import com.manko.counties.model.Region;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    Optional<Region> findByRegionName(String regionName);
}
