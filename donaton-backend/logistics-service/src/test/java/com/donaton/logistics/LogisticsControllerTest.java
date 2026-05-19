package com.donaton.logistics;

import com.donaton.logistics.controller.LogisticsController;
import com.donaton.logistics.model.Inventory;
import com.donaton.logistics.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LogisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryRepository inventoryRepository;

    private Inventory testInventory;

    @BeforeEach
    public void setUp() {
        testInventory = new Inventory();
        testInventory.setId(1L);
        testInventory.setItemName("Cesta de alimentos");
        testInventory.setQuantity(10);
    }

    @Test
    public void testGetAllInventory() throws Exception {
        List<Inventory> inventories = Arrays.asList(testInventory);
        when(inventoryRepository.findAll()).thenReturn(inventories);

        mockMvc.perform(get("/api/logistics/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemName").value("Cesta de alimentos"))
                .andExpect(jsonPath("$[0].quantity").value(10));
    }

    @Test
    public void testGetInventoryById() throws Exception {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(testInventory));

        mockMvc.perform(get("/api/logistics/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value("Cesta de alimentos"));
    }

    @Test
    public void testCreateInventory() throws Exception {
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(testInventory);

        String requestBody = "{\"itemName\":\"Cesta de alimentos\",\"quantity\":10}";

        mockMvc.perform(post("/api/logistics/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.itemName").value("Cesta de alimentos"));
    }

    @Test
    public void testDeleteInventory() throws Exception {
        mockMvc.perform(delete("/api/logistics/inventory/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetInventoryNotFound() throws Exception {
        when(inventoryRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/logistics/inventory/999"))
                .andExpect(status().isNotFound());
    }
}
