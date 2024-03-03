package com.manko.counties.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "countries_parameters")
public class CountryParameters {

    @Id
    private String id;

    private String country;

    private Integer code;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id")
    private Region region;
}
