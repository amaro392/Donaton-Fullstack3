package com.donaton.donation.repository.factory;

import com.donaton.donation.model.Donation;
import com.donaton.donation.model.IndividualDonation;
import com.donaton.donation.model.CorporateDonation;

public class DonationFactory {
    public static Donation createDonation(String type) {
        if ("INDIVIDUAL".equalsIgnoreCase(type)) {
            return new IndividualDonation();
        } else if ("CORPORATE".equalsIgnoreCase(type)) {
            return new CorporateDonation();
        }
        throw new IllegalArgumentException("Tipo de donación no soportado: " + type);
    }
}