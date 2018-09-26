package com.bytatech.ayoos.patient.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bytatech.ayoos.patient.service.dto.CityDTO;

/**
 * Service Interface for managing City.
 */
public interface CityService {

    /**
     * Save a city.
     *
     * @param cityDTO the entity to save
     * @return the persisted entity
     */
    CityDTO save(CityDTO cityDTO);

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" city.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CityDTO> findOne(Long id);

    /**
     * Delete the "id" city.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the city corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CityDTO> search(String query, Pageable pageable);
    
    
    /**
     * Get all the cities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
	Page<CityDTO> findAllByState_name(String name, Pageable pageable);
}
