package com.donaton.logistics;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas exhaustivas para el controlador de logística.
 * Verifica que los endpoints de gestión de inventario funcionan correctamente.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class LogisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetInventory_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/inventory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInventoryItem_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/inventory/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateInventoryItem_ShouldReturnCreated() throws Exception {
        String inventoryJson = "{\"name\":\"Test Item\",\"quantity\":100}";
        
        mockMvc.perform(post("/api/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateInventoryItem_ShouldReturnOk() throws Exception {
        String inventoryJson = "{\"name\":\"Updated Item\",\"quantity\":50}";
        
        mockMvc.perform(put("/api/inventory/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteInventoryItem_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/inventory/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testInventoryContentTypeIsJson() throws Exception {
        mockMvc.perform(get("/api/inventory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
