package com.bytatech.ayoos.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytatech.ayoos.patient.domain.Country;


/**
 * Spring Data  repository for the Country entity.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
