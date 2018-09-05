package com.bytatech.ayoos.patient.repository;

import com.bytatech.ayoos.patient.domain.AddressLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AddressLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressLineRepository extends JpaRepository<AddressLine, Long> {

}
