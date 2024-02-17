package com.manko.phonecode;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class PhoneCode {

    @Id
    private String id;

    private String country;
    private Integer code;

    public PhoneCode(String countryCode, String country, int numberPhone) {
        this.id = countryCode;
        this.country = country;
        this.code = numberPhone;
    }

    public PhoneCode() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
