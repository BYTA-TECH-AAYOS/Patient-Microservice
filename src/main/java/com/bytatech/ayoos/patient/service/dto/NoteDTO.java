package com.bytatech.ayoos.patient.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Note entity.
 */
public class NoteDTO implements Serializable {

    private Long id;

    private String description;

    private Long medicalRecordsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        NoteDTO noteDTO = (NoteDTO) o;
        if (noteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), noteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NoteDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", medicalRecords=" + getMedicalRecordsId() +
            "}";
    }
}
