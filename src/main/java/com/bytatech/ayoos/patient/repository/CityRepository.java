package com.bytatech.ayoos.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytatech.ayoos.patient.domain.City;


/**
 * Spring Data  repository for the City entity.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	Page<City> findAllByState_name(String name, Pageable pageable);

	
}
