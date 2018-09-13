package com.bytatech.ayoos.patient.repository;

import com.bytatech.ayoos.patient.domain.MedicalReference;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalReference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalReferenceRepository extends JpaRepository<MedicalReference, Long> {

}
