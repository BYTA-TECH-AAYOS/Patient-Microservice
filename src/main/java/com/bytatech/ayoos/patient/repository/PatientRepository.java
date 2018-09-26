package com.bytatech.ayoos.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytatech.ayoos.patient.domain.Patient;


/**
 * Spring Data  repository for the Patient entity.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
