package com.donaton.donation.dto;

/**
 * DTO estándar para representar donaciones de cualquier tipo.
 * Implementa el patrón Adapter para normalizar diferentes tipos de donaciones.
 */
public class DonationDTO {
    private Long id;
    private String type;
    private String description;
    private String donorIdentifier;
    private String donorName;

    public DonationDTO() {}

    public DonationDTO(Long id, String type, String description, String donorIdentifier, String donorName) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.donorIdentifier = donorIdentifier;
        this.donorName = donorName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDonorIdentifier() { return donorIdentifier; }
    public void setDonorIdentifier(String donorIdentifier) { this.donorIdentifier = donorIdentifier; }

    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }
}
