package com.bytatech.ayoos.patient.service.mapper;

import com.bytatech.ayoos.patient.domain.*;
import com.bytatech.ayoos.patient.service.dto.MedicalReferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MedicalReference and its DTO MedicalReferenceDTO.
 */
@Mapper(componentModel = "spring", uses = {MedicalRecordsMapper.class})
public interface MedicalReferenceMapper extends EntityMapper<MedicalReferenceDTO, MedicalReference> {

    @Mapping(source = "medicalRecords.id", target = "medicalRecordsId")
    MedicalReferenceDTO toDto(MedicalReference medicalReference);

    @Mapping(source = "medicalRecordsId", target = "medicalRecords")
    MedicalReference toEntity(MedicalReferenceDTO medicalReferenceDTO);

    default MedicalReference fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalReference medicalReference = new MedicalReference();
        medicalReference.setId(id);
        return medicalReference;
    }
}
