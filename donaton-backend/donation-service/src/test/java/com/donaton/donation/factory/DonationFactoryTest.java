package com.donaton.donation.factory;

import com.donaton.donation.model.Donation;
import com.donaton.donation.model.IndividualDonation;
import com.donaton.donation.model.CorporateDonation;
import com.donaton.donation.repository.factory.DonationFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el patrón Factory Pattern.
 * Verifica que DonationFactory cree correctamente instancias de diferentes tipos de donaciones.
 */
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
    public void testCreateIndividualDonationCaseInsensitive() {
        Donation donation = DonationFactory.createDonation("individual");
        assertNotNull(donation);
        assertInstanceOf(IndividualDonation.class, donation);
    }

    @Test
    public void testCreateCorporateDonationCaseInsensitive() {
        Donation donation = DonationFactory.createDonation("corporate");
        assertNotNull(donation);
        assertInstanceOf(CorporateDonation.class, donation);
    }

    @Test
    public void testCreateDonationWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            DonationFactory.createDonation("INVALID_TYPE");
        });
    }

    @Test
    public void testCreateDonationWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> {
            DonationFactory.createDonation(null);
        });
    }
}
