package com.bytatech.ayoos.patient.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MedicalRecords.
 */
@Entity
@Table(name = "medical_records")
@Document(indexName = "medicalrecords")
public class MedicalRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "disease_name")
    private String diseaseName;

    @ManyToOne
    @JsonIgnoreProperties("medicalRecords")
    private Patient patient;

    @OneToMany(mappedBy = "medicalRecords")
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "medicalRecords")
    private Set<MedicalReference> medicalReferences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public MedicalRecords diseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
        return this;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Patient getPatient() {
        return patient;
    }

    public MedicalRecords patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public MedicalRecords notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public MedicalRecords addNotes(Note note) {
        this.notes.add(note);
        note.setMedicalRecords(this);
        return this;
    }

    public MedicalRecords removeNotes(Note note) {
        this.notes.remove(note);
        note.setMedicalRecords(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<MedicalReference> getMedicalReferences() {
        return medicalReferences;
    }

    public MedicalRecords medicalReferences(Set<MedicalReference> medicalReferences) {
        this.medicalReferences = medicalReferences;
        return this;
    }

    public MedicalRecords addMedicalReferences(MedicalReference medicalReference) {
        this.medicalReferences.add(medicalReference);
        medicalReference.setMedicalRecords(this);
        return this;
    }

    public MedicalRecords removeMedicalReferences(MedicalReference medicalReference) {
        this.medicalReferences.remove(medicalReference);
        medicalReference.setMedicalRecords(null);
        return this;
    }

    public void setMedicalReferences(Set<MedicalReference> medicalReferences) {
        this.medicalReferences = medicalReferences;
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
        MedicalRecords medicalRecords = (MedicalRecords) o;
        if (medicalRecords.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalRecords.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalRecords{" +
            "id=" + getId() +
            ", diseaseName='" + getDiseaseName() + "'" +
            "}";
    }
}
