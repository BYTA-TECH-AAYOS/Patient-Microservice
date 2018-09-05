package com.bytatech.ayoos.patient.service;

import com.bytatech.ayoos.patient.service.dto.AddressLineDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AddressLine.
 */
public interface AddressLineService {

    /**
     * Save a addressLine.
     *
     * @param addressLineDTO the entity to save
     * @return the persisted entity
     */
    AddressLineDTO save(AddressLineDTO addressLineDTO);

    /**
     * Get all the addressLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AddressLineDTO> findAll(Pageable pageable);


    /**
     * Get the "id" addressLine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AddressLineDTO> findOne(Long id);

    /**
     * Delete the "id" addressLine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the addressLine corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AddressLineDTO> search(String query, Pageable pageable);
}
