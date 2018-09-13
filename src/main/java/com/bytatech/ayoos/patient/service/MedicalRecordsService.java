package com.bytatech.ayoos.patient.service;

import com.bytatech.ayoos.patient.service.dto.MedicalRecordsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MedicalRecords.
 */
public interface MedicalRecordsService {

    /**
     * Save a medicalRecords.
     *
     * @param medicalRecordsDTO the entity to save
     * @return the persisted entity
     */
    MedicalRecordsDTO save(MedicalRecordsDTO medicalRecordsDTO);

    /**
     * Get all the medicalRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MedicalRecordsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" medicalRecords.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MedicalRecordsDTO> findOne(Long id);

    /**
     * Delete the "id" medicalRecords.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the medicalRecords corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MedicalRecordsDTO> search(String query, Pageable pageable);
}
