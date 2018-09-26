package com.bytatech.ayoos.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytatech.ayoos.patient.domain.AddressLine;


/**
 * Spring Data  repository for the AddressLine entity.
 */
@Repository
public interface AddressLineRepository extends JpaRepository<AddressLine, Long> {

}
