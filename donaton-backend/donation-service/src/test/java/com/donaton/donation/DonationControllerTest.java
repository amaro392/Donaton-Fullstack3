package com.donaton.donation;

import com.donaton.donation.controller.DonationController;
import com.donaton.donation.model.CorporateDonation;
import com.donaton.donation.model.IndividualDonation;
import com.donaton.donation.repository.DonationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DonationRepository donationRepository;

    @Autowired
    private DonationController donationController;

    @Test
    public void contextLoads() {
        // Verifica que el controlador está disponible
        assert donationController != null;
    }

    @Test
    public void shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/donations"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllDonations() throws Exception {
        IndividualDonation donation1 = new IndividualDonation();
        donation1.setId(1L);
        donation1.setDescription("Donación de un individuo");

        List<Object> donations = Arrays.asList(donation1);
        when(donationRepository.findAll()).thenReturn(donations);

        mockMvc.perform(get("/api/donations"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDonationById() throws Exception {
        IndividualDonation donation = new IndividualDonation();
        donation.setId(1L);
        donation.setDescription("Test donation");

        when(donationRepository.findById(1L)).thenReturn(Optional.of(donation));

        mockMvc.perform(get("/api/donations/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateIndividualDonation() throws Exception {
        IndividualDonation donation = new IndividualDonation();
        donation.setId(1L);
        donation.setDescription("Nueva donación individual");

        when(donationRepository.save(any())).thenReturn(donation);

        String requestBody = "{\"description\":\"Nueva donación individual\"}";

        mockMvc.perform(post("/api/donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateCorporateDonation() throws Exception {
        CorporateDonation donation = new CorporateDonation();
        donation.setId(2L);
        donation.setDescription("Donación corporativa");

        when(donationRepository.save(any())).thenReturn(donation);

        String requestBody = "{\"description\":\"Donación corporativa\"}";

        mockMvc.perform(post("/api/donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteDonation() throws Exception {
        mockMvc.perform(delete("/api/donations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateDonation() throws Exception {
        String requestBody = "{\"description\":\"Donación actualizada\"}";

        mockMvc.perform(put("/api/donations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }
}