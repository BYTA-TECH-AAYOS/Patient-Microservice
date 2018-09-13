package com.bytatech.ayoos.patient.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MedicalReference entity.
 */
public class MedicalReferenceDTO implements Serializable {

    private Long id;

    private String reference;

    private Long medicalRecordsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getMedicalRecordsId() {
        return medicalRecordsId;
    }

    public void setMedicalRecordsId(Long medicalRecordsId) {
        this.medicalRecordsId = medicalRecordsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicalReferenceDTO medicalReferenceDTO = (MedicalReferenceDTO) o;
        if (medicalReferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalReferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalReferenceDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", medicalRecords=" + getMedicalRecordsId() +
            "}";
    }
}
