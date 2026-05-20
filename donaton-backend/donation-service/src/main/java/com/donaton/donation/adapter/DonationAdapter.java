package com.donaton.donation.adapter;

import com.donaton.donation.dto.DonationDTO;
import com.donaton.donation.model.Donation;
import com.donaton.donation.model.CorporateDonation;
import com.donaton.donation.model.IndividualDonation;

/**
 * Adaptador para convertir diferentes tipos de Donation a un formato estándar (DonationDTO).
 * Implementa el patrón Adapter que permite normalizar distintos tipos de donaciones
 * para su exposición a través de la API REST.
 */
public class DonationAdapter {

    /**
     * Convierte una donación a su representación DTO estándar.
     * @param donation La donación a convertir
     * @return DTO con formato estándar
     */
    public static DonationDTO adaptToDonationDTO(Donation donation) {
        if (donation instanceof IndividualDonation) {
            return adaptIndividualDonation((IndividualDonation) donation);
        } else if (donation instanceof CorporateDonation) {
            return adaptCorporateDonation((CorporateDonation) donation);
        }
        throw new UnsupportedOperationException("Tipo de donación no soportado");
    }

    /**
     * Adapta una donación individual al formato DTO.
     */
    private static DonationDTO adaptIndividualDonation(IndividualDonation donation) {
        return new DonationDTO(
                donation.getId(),
                "INDIVIDUAL",
                donation.getDescription(),
                donation.getCitizenRut(),
                "Ciudadano"
        );
    }

    /**
     * Adapta una donación corporativa al formato DTO.
     */
    private static DonationDTO adaptCorporateDonation(CorporateDonation donation) {
        return new DonationDTO(
                donation.getId(),
                "CORPORATE",
                donation.getDescription(),
                donation.getCompanyName(),
                "Empresa"
        );
    }
}
