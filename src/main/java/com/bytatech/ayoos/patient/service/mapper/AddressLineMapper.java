package com.bytatech.ayoos.patient.service.mapper;

import com.bytatech.ayoos.patient.domain.*;
import com.bytatech.ayoos.patient.service.dto.AddressLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AddressLine and its DTO AddressLineDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactInfoMapper.class, LocationMapper.class, CountryMapper.class})
public interface AddressLineMapper extends EntityMapper<AddressLineDTO, AddressLine> {

    @Mapping(source = "contactInfo.id", target = "contactInfoId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "country.id", target = "countryId")
    AddressLineDTO toDto(AddressLine addressLine);

    @Mapping(source = "contactInfoId", target = "contactInfo")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "countryId", target = "country")
    AddressLine toEntity(AddressLineDTO addressLineDTO);

    default AddressLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        AddressLine addressLine = new AddressLine();
        addressLine.setId(id);
        return addressLine;
    }
}
