package com.donaton.donation.controller;
import com.donaton.donation.strategy.*;
import com.donaton.donation.model.Donation;
import com.donaton.donation.repository.DonationRepository;
import com.donaton.donation.repository.factory.DonationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationRepository repository;

    // Obtener todas las donaciones
    @GetMapping
    public List<Donation> getAllDonations() {
        return repository.findAll();
    }

    // Crear una donación usando el Patrón Factory
    @PostMapping("/{type}")
    public Donation createDonation(@PathVariable String type, @RequestBody String description) {
        Donation newDonation = DonationFactory.createDonation(type);
        newDonation.setDescription(description);
        return repository.save(newDonation);
    }
}

@GetMapping("/process/{type}")
public String processDonation(@PathVariable String type) {

    DonationStrategy strategy;

    if(type.equals("individual")) {
        strategy = new IndividualDonationStrategy();
    } else {
        strategy = new CorporateDonationStrategy();
    }

    return strategy.processDonation();
}