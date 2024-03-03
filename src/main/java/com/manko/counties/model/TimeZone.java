package com.manko.counties.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "timeZones", cascade = CascadeType.ALL)
    private List<CountryParameters> countryParametersSet;

    public TimeZone(String name, List<CountryParameters> countryParametersSet) {
        this.name = name;
        this.countryParametersSet = countryParametersSet;
    }

    public TimeZone(String name) {
        this.name = name;
    }
}
