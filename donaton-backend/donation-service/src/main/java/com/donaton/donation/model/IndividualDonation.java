package com.donaton.donation.model;

import jakarta.persistence.Entity;

@Entity
public class IndividualDonation extends Donation {
    private String citizenRut;

    public String getCitizenRut() { return citizenRut; }
    public void setCitizenRut(String citizenRut) { this.citizenRut = citizenRut; }
}