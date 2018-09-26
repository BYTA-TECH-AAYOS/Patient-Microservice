package com.bytatech.ayoos.patient.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A ContactInfo.
 */
@Entity
@Table(name = "contact_info")
@Document(indexName = "contactinfo")
public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "website_url")
    private String websiteURL;

    @Column(name = "facebook_url")
    private String facebookURL;

    @Column(name = "twitter_url")
    private String twitterURL;

    @OneToMany(mappedBy = "contactInfo")
    private Set<AddressLine> addressLines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public ContactInfo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ContactInfo phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public ContactInfo websiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
        return this;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public ContactInfo facebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
        return this;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }

    public String getTwitterURL() {
        return twitterURL;
    }

    public ContactInfo twitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
        return this;
    }

    public void setTwitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
    }

    public Set<AddressLine> getAddressLines() {
        return addressLines;
    }

    public ContactInfo addressLines(Set<AddressLine> addressLines) {
        this.addressLines = addressLines;
        return this;
    }

    public ContactInfo addAddressLines(AddressLine addressLine) {
        this.addressLines.add(addressLine);
        addressLine.setContactInfo(this);
        return this;
    }

    public ContactInfo removeAddressLines(AddressLine addressLine) {
        this.addressLines.remove(addressLine);
        addressLine.setContactInfo(null);
        return this;
    }

    public void setAddressLines(Set<AddressLine> addressLines) {
        this.addressLines = addressLines;
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
        ContactInfo contactInfo = (ContactInfo) o;
        if (contactInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", websiteURL='" + getWebsiteURL() + "'" +
            ", facebookURL='" + getFacebookURL() + "'" +
            ", twitterURL='" + getTwitterURL() + "'" +
            "}";
    }
}
