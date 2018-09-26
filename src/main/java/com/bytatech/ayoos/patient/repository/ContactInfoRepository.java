package com.bytatech.ayoos.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytatech.ayoos.patient.domain.ContactInfo;


/**
 * Spring Data  repository for the ContactInfo entity.
 */
@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {

}
