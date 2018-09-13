package com.bytatech.ayoos.patient.repository;

import com.bytatech.ayoos.patient.domain.MedicalRecords;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalRecords entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, Long> {

}
