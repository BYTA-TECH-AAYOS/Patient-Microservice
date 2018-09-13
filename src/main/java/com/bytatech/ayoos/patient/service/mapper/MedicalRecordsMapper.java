package com.bytatech.ayoos.patient.service.mapper;

import com.bytatech.ayoos.patient.domain.*;
import com.bytatech.ayoos.patient.service.dto.MedicalRecordsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MedicalRecords and its DTO MedicalRecordsDTO.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface MedicalRecordsMapper extends EntityMapper<MedicalRecordsDTO, MedicalRecords> {

    @Mapping(source = "patient.id", target = "patientId")
    MedicalRecordsDTO toDto(MedicalRecords medicalRecords);

    @Mapping(source = "patientId", target = "patient")
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "medicalReferences", ignore = true)
    MedicalRecords toEntity(MedicalRecordsDTO medicalRecordsDTO);

    default MedicalRecords fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setId(id);
        return medicalRecords;
    }
}
