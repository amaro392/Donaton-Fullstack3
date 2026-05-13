package com.donaton.donation.strategy;

public class CorporateDonationStrategy implements DonationStrategy {

    @Override
    public String processDonation() {
        return "Procesando donación corporativa";
    }
}