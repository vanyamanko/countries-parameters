package com.manko.countries.model;

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

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "timeZones", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Country> countrySet;
}
