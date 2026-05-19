package com.donaton.donation.adapter;

import com.donaton.donation.dto.DonationDTO;
import com.donaton.donation.model.IndividualDonation;
import com.donaton.donation.model.CorporateDonation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el patrón Adapter Pattern.
 * Verifica que DonationAdapter convierta correctamente diferentes tipos de donaciones a DTO.
 */
public class DonationAdapterTest {

    @Test
    public void testAdaptIndividualDonationToDTO() {
        // Arrange
        IndividualDonation donation = new IndividualDonation();
        donation.setId(1L);
        donation.setDescription("Donación individual");
        donation.setCitizenRut("12.345.678-9");

        // Act
        DonationDTO dto = DonationAdapter.adaptToDonationDTO(donation);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("INDIVIDUAL", dto.getType());
        assertEquals("Donación individual", dto.getDescription());
        assertEquals("12.345.678-9", dto.getDonorIdentifier());
        assertEquals("Ciudadano", dto.getDonorName());
    }

    @Test
    public void testAdaptCorporateDonationToDTO() {
        // Arrange
        CorporateDonation donation = new CorporateDonation();
        donation.setId(2L);
        donation.setDescription("Donación corporativa");
        donation.setCompanyName("Tech Corp S.A.");

        // Act
        DonationDTO dto = DonationAdapter.adaptToDonationDTO(donation);

        // Assert
        assertNotNull(dto);
        assertEquals(2L, dto.getId());
        assertEquals("CORPORATE", dto.getType());
        assertEquals("Donación corporativa", dto.getDescription());
        assertEquals("Tech Corp S.A.", dto.getDonorIdentifier());
        assertEquals("Empresa", dto.getDonorName());
    }

    @Test
    public void testAdaptIndividualDonationWithNullDescription() {
        // Arrange
        IndividualDonation donation = new IndividualDonation();
        donation.setId(3L);
        donation.setDescription(null);
        donation.setCitizenRut("10.123.456-7");

        // Act
        DonationDTO dto = DonationAdapter.adaptToDonationDTO(donation);

        // Assert
        assertNotNull(dto);
        assertNull(dto.getDescription());
        assertEquals("10.123.456-7", dto.getDonorIdentifier());
    }

    @Test
    public void testAdaptNullDonation() {
        assertThrows(NullPointerException.class, () -> {
            DonationAdapter.adaptToDonationDTO(null);
        });
    }
}
