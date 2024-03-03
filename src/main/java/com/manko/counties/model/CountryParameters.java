package com.manko.counties.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "countries_parameters")
public class CountryParameters {

    @Id
    private String id;

    private String country;

    private Integer code;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "country_time_zone",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "time_zone_id"))
    private Set<TimeZone> timeZones = new HashSet<>();

}
