package com.manko.phonecode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/phone-code")
public class PhoneCodeController {
    private final PhoneCodeService phoneCodeService;

    public PhoneCodeController(PhoneCodeService phoneCodeService) {
        this.phoneCodeService = phoneCodeService;
    }
    @GetMapping("/country")
    public ResponseEntity<PhoneCode> getCodeByCountryOrId(@RequestParam String country) {
        PhoneCode code = phoneCodeService.getCodeByCountryOrId(country);
        return ResponseEntity.ok(code);
    }

    @GetMapping("/code")
    public ResponseEntity<List<PhoneCode>> getCountryByCode(@RequestParam Integer code) {
        List<PhoneCode> countres = phoneCodeService.getCountriesByCode(code);
        return ResponseEntity.ok(countres);
    }
}