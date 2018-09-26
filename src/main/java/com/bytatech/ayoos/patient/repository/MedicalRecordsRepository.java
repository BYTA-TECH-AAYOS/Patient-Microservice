package com.bytatech.ayoos.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytatech.ayoos.patient.domain.MedicalRecords;


/**
 * Spring Data  repository for the MedicalRecords entity.
 */
@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, Long> {

}
