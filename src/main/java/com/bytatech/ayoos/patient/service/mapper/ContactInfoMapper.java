package com.bytatech.ayoos.patient.service.mapper;

import com.bytatech.ayoos.patient.domain.*;
import com.bytatech.ayoos.patient.service.dto.ContactInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContactInfo and its DTO ContactInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactInfoMapper extends EntityMapper<ContactInfoDTO, ContactInfo> {


    @Mapping(target = "addressLines", ignore = true)
    ContactInfo toEntity(ContactInfoDTO contactInfoDTO);

    default ContactInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(id);
        return contactInfo;
    }
}
