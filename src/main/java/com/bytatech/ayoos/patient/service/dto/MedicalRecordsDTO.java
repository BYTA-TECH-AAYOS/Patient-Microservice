package com.bytatech.ayoos.patient.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MedicalRecords entity.
 */
public class MedicalRecordsDTO implements Serializable {

    private Long id;

    private String diseaseName;

    private Long patientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicalRecordsDTO medicalRecordsDTO = (MedicalRecordsDTO) o;
        if (medicalRecordsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalRecordsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalRecordsDTO{" +
            "id=" + getId() +
            ", diseaseName='" + getDiseaseName() + "'" +
            ", patient=" + getPatientId() +
            "}";
    }
}
