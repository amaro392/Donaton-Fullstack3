package com.donaton.logistics.respository;

import com.donaton.logistics.model.Inventary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventary, Long> {
}

