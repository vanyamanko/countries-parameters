package com.manko.countries.model;

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

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region", cascade = CascadeType.PERSIST)
    private List<Country> countries;
}
