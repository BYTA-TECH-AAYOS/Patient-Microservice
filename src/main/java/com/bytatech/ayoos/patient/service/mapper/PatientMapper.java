package com.bytatech.ayoos.patient.service.mapper;

import com.bytatech.ayoos.patient.domain.*;
import com.bytatech.ayoos.patient.service.dto.PatientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Patient and its DTO PatientDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactInfoMapper.class})
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {

    @Mapping(source = "contactInfo.id", target = "contactInfoId")
    PatientDTO toDto(Patient patient);

    @Mapping(source = "contactInfoId", target = "contactInfo")
    @Mapping(target = "medicalRecords", ignore = true)
    Patient toEntity(PatientDTO patientDTO);

    default Patient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setId(id);
        return patient;
    }
}
