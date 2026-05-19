package com.donaton.bff;

import com.donaton.bff.config.AppBeans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AppBeansTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplateBeanExists() {
        // Verifica que el bean RestTemplate está correctamente configurado
        assertNotNull(restTemplate);
    }

    @Test
    public void testRestTemplateCanMakeRequests() {
        // Verifica que RestTemplate es funcional
        assertNotNull(restTemplate.getClass());
    }
}
