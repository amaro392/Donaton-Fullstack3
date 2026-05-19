package com.donaton.donation;

import com.donaton.donation.model.CorporateDonation;
import com.donaton.donation.model.IndividualDonation;
import com.donaton.donation.strategy.CorporateDonationStrategy;
import com.donaton.donation.strategy.DonationStrategy;
import com.donaton.donation.strategy.IndividualDonationStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DonationStrategyTest {

    @Test
    public void testIndividualDonationStrategy() {
        DonationStrategy strategy = new IndividualDonationStrategy();
        String result = strategy.processDonation();
        
        assertNotNull(result);
        assertTrue(result.contains("individual") || result.contains("Individual"));
    }

    @Test
    public void testCorporateDonationStrategy() {
        DonationStrategy strategy = new CorporateDonationStrategy();
        String result = strategy.processDonation();
        
        assertNotNull(result);
        assertTrue(result.contains("corporate") || result.contains("Corporate"));
    }

    @Test
    public void testStrategyPolymorphism() {
        DonationStrategy individualStrategy = new IndividualDonationStrategy();
        DonationStrategy corporateStrategy = new CorporateDonationStrategy();
        
        String individualResult = individualStrategy.processDonation();
        String corporateResult = corporateStrategy.processDonation();
        
        assertNotEquals(individualResult, corporateResult);
    }
}
