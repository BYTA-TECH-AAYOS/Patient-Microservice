package com.bytatech.ayoos.patient.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AddressLine entity.
 */
public class AddressLineDTO implements Serializable {

    private Long id;

    private String streetOrHouseNumber;

    private String zipCode;

    private Long contactInfoId;

    private Long locationId;

    private Long countryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetOrHouseNumber() {
        return streetOrHouseNumber;
    }

    public void setStreetOrHouseNumber(String streetOrHouseNumber) {
        this.streetOrHouseNumber = streetOrHouseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Long getContactInfoId() {
        return contactInfoId;
    }

    public void setContactInfoId(Long contactInfoId) {
        this.contactInfoId = contactInfoId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressLineDTO addressLineDTO = (AddressLineDTO) o;
        if (addressLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressLineDTO{" +
            "id=" + getId() +
            ", streetOrHouseNumber='" + getStreetOrHouseNumber() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", contactInfo=" + getContactInfoId() +
            ", location=" + getLocationId() +
            ", country=" + getCountryId() +
            "}";
    }
}
