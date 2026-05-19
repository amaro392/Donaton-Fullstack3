package com.donaton.logistics;

import com.donaton.logistics.model.Inventory;
import com.donaton.logistics.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class InventoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InventoryRepository inventoryRepository;

    private Inventory testInventory;

    @BeforeEach
    public void setUp() {
        testInventory = new Inventory();
        testInventory.setItemName("Medicinas");
        testInventory.setQuantity(50);
        entityManager.persistAndFlush(testInventory);
    }

    @Test
    public void testFindAll() {
        List<Inventory> inventories = inventoryRepository.findAll();
        assertFalse(inventories.isEmpty());
        assertTrue(inventories.stream().anyMatch(i -> i.getItemName().equals("Medicinas")));
    }

    @Test
    public void testFindById() {
        Optional<Inventory> found = inventoryRepository.findById(testInventory.getId());
        assertTrue(found.isPresent());
        assertEquals("Medicinas", found.get().getItemName());
    }

    @Test
    public void testSave() {
        Inventory newInventory = new Inventory();
        newInventory.setItemName("Ropa");
        newInventory.setQuantity(100);

        Inventory saved = inventoryRepository.save(newInventory);

        assertNotNull(saved.getId());
        assertEquals("Ropa", saved.getItemName());
    }

    @Test
    public void testUpdate() {
        testInventory.setQuantity(75);
        inventoryRepository.save(testInventory);

        Optional<Inventory> updated = inventoryRepository.findById(testInventory.getId());
        assertTrue(updated.isPresent());
        assertEquals(75, updated.get().getQuantity());
    }

    @Test
    public void testDelete() {
        Long id = testInventory.getId();
        inventoryRepository.delete(testInventory);

        Optional<Inventory> deleted = inventoryRepository.findById(id);
        assertTrue(deleted.isEmpty());
    }
}
