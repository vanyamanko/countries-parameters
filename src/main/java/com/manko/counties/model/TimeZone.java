package com.manko.counties.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Entity
@Table
@Data
@AllArgsConstructor
public class TimeZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_time_zone")
    private String nameTimeZone;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "timeZones", cascade = CascadeType.ALL)
    private Set<CountryParameters> countryParametersSet;
}
