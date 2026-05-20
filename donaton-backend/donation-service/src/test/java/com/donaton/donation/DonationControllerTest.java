package com.donaton.donation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas exhaustivas para el controlador de donaciones.
 * Verifica que todos los endpoints funcionan correctamente.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllDonations_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/donations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDonationById_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/donations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateDonation_ShouldReturnCreated() throws Exception {
        String donationJson = "{\"type\":\"INDIVIDUAL\",\"description\":\"Test donation\",\"citizenRut\":\"12.345.678-9\"}";
        
        mockMvc.perform(post("/api/donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(donationJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateDonation_ShouldReturnOk() throws Exception {
        String donationJson = "{\"type\":\"CORPORATE\",\"description\":\"Updated donation\",\"companyName\":\"Test Corp\"}";
        
        mockMvc.perform(put("/api/donations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(donationJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteDonation_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/donations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetDonations_ContentTypeIsJson() throws Exception {
        mockMvc.perform(get("/api/donations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}