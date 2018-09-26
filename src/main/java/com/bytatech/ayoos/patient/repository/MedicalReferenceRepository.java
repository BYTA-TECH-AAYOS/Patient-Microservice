package com.bytatech.ayoos.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytatech.ayoos.patient.domain.MedicalReference;


/**
 * Spring Data  repository for the MedicalReference entity.
 */
@Repository
public interface MedicalReferenceRepository extends JpaRepository<MedicalReference, Long> {

}
