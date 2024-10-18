package model;

import model.enums.EnumRegistration;

public class Registration {
    private Integer registrationId;
    private EnumRegistration registrationType;
    private String registrationName;
    private String registrationDocument;
    private Address registrationAddress;

    public Registration() {}

    public Integer getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
    }

    public EnumRegistration getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(EnumRegistration registrationType) {
        this.registrationType = registrationType;
    }

    public String getRegistrationName() {
        return registrationName;
    }

    public void setRegistrationName(String registrationName) {
        this.registrationName = registrationName;
    }

    public String getRegistrationDocument() {
        return registrationDocument;
    }

    public void setRegistrationDocument(String registrationDocument) {
        this.registrationDocument = registrationDocument;
    }

    public Address getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(Address registrationAddress) {
        this.registrationAddress = registrationAddress;
    }
}
