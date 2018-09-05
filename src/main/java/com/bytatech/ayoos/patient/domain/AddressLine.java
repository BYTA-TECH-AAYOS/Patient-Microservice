package com.bytatech.ayoos.patient.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AddressLine.
 */
@Entity
@Table(name = "address_line")
@Document(indexName = "addressline")
public class AddressLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street_or_house_number")
    private String streetOrHouseNumber;

    @Column(name = "zip_code")
    private String zipCode;

    @ManyToOne
    @JsonIgnoreProperties("addressLines")
    private ContactInfo contactInfo;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetOrHouseNumber() {
        return streetOrHouseNumber;
    }

    public AddressLine streetOrHouseNumber(String streetOrHouseNumber) {
        this.streetOrHouseNumber = streetOrHouseNumber;
        return this;
    }

    public void setStreetOrHouseNumber(String streetOrHouseNumber) {
        this.streetOrHouseNumber = streetOrHouseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public AddressLine zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public AddressLine contactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
        return this;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Location getLocation() {
        return location;
    }

    public AddressLine location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Country getCountry() {
        return country;
    }

    public AddressLine country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressLine addressLine = (AddressLine) o;
        if (addressLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressLine{" +
            "id=" + getId() +
            ", streetOrHouseNumber='" + getStreetOrHouseNumber() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            "}";
    }
}
