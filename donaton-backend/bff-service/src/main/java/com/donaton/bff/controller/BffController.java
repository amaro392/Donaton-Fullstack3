package com.donaton.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/bff")
public class BffController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/donations")
    public Object getDonations() {

        return restTemplate.getForObject(
                "http://localhost:8081/api/donations",
                Object.class
        );
    }
}