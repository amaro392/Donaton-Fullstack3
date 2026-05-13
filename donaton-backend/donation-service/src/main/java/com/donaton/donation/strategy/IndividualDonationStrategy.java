package com.donaton.donation.strategy;

public class IndividualDonationStrategy implements DonationStrategy {

    @Override
    public String processDonation() {
        return "Procesando donación individual";
    }
}