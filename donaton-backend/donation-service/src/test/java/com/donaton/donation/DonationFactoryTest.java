package com.donaton.donation;

import com.donaton.donation.model.CorporateDonation;
import com.donaton.donation.model.Donation;
import com.donaton.donation.model.IndividualDonation;
import com.donaton.donation.repository.factory.DonationFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DonationFactoryTest {

    @Test
    public void testCreateIndividualDonation() {
        Donation donation = DonationFactory.createDonation("INDIVIDUAL");
        
        assertNotNull(donation);
        assertInstanceOf(IndividualDonation.class, donation);
    }

    @Test
    public void testCreateCorporateDonation() {
        Donation donation = DonationFactory.createDonation("CORPORATE");
        
        assertNotNull(donation);
        assertInstanceOf(CorporateDonation.class, donation);
    }

    @Test
    public void testCreateInvalidDonationType() {
        assertThrows(IllegalArgumentException.class, () -> {
            DonationFactory.createDonation("INVALID");
        });
    }

    @Test
    public void testCaseInsensitiveCreation() {
        Donation donation1 = DonationFactory.createDonation("individual");
        Donation donation2 = DonationFactory.createDonation("INDIVIDUAL");
        
        assertInstanceOf(IndividualDonation.class, donation1);
        assertInstanceOf(IndividualDonation.class, donation2);
    }
}
