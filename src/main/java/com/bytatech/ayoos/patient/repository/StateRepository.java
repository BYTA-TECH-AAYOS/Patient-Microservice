package com.bytatech.ayoos.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytatech.ayoos.patient.domain.State;


/**
 * Spring Data  repository for the State entity.
 */
@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	Page<State> findAllByCountry_name(String name, Pageable pageable);
	
}
