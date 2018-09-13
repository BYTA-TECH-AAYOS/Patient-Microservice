package com.bytatech.ayoos.patient.service;

import com.bytatech.ayoos.patient.service.dto.MedicalReferenceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MedicalReference.
 */
public interface MedicalReferenceService {

    /**
     * Save a medicalReference.
     *
     * @param medicalReferenceDTO the entity to save
     * @return the persisted entity
     */
    MedicalReferenceDTO save(MedicalReferenceDTO medicalReferenceDTO);

    /**
     * Get all the medicalReferences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MedicalReferenceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" medicalReference.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MedicalReferenceDTO> findOne(Long id);

    /**
     * Delete the "id" medicalReference.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the medicalReference corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MedicalReferenceDTO> search(String query, Pageable pageable);
}
