package com.donaton.donation.repository;

import com.donaton.donation.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    // Spring Data JPA provee los métodos CRUD automáticamente
}