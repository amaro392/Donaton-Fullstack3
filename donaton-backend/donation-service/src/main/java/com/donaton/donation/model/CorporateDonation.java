package com.donaton.donation.model;

import jakarta.persistence.Entity;

@Entity
public class CorporateDonation extends Donation {
    private String companyName;

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}