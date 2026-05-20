package com.donaton.bff;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas exhaustivas para el controlador BFF.
 * Verifica que la fachada BFF funciona correctamente como intermediaria.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testBffHealth_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/bff/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testBffGetServiceStatus_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/bff/status")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testBffContentTypeIsJson() throws Exception {
        mockMvc.perform(get("/api/bff/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
