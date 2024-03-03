package com.manko.counties.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String regionName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region", cascade = CascadeType.PERSIST)
    private List<CountryParameters> countryParameters;
}
