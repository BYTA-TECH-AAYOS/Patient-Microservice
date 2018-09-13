package com.bytatech.ayoos.patient.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MedicalReference.
 */
@Entity
@Table(name = "medical_reference")
@Document(indexName = "medicalreference")
public class MedicalReference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    @JsonIgnoreProperties("medicalReferences")
    private MedicalRecords medicalRecords;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public MedicalReference reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public MedicalRecords getMedicalRecords() {
        return medicalRecords;
    }

    public MedicalReference medicalRecords(MedicalRecords medicalRecords) {
        this.medicalRecords = medicalRecords;
        return this;
    }

    public void setMedicalRecords(MedicalRecords medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MedicalReference medicalReference = (MedicalReference) o;
        if (medicalReference.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalReference.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalReference{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
