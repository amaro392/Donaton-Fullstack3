package com.donaton.bff;

import com.donaton.bff.controller.BffController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BffController bffController;

    @Test
    public void testContextLoads() {
        // Verifica que la aplicación se inicia correctamente
        assert bffController != null;
    }

    @Test
    public void testBffServiceIsAvailable() throws Exception {
        // Verifica que el BFF responde a las peticiones
        mockMvc.perform(get("/api/donations"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGatewayEndpointExists() throws Exception {
        // Verifica que el gateway está disponible
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }
}
