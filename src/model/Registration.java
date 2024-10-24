package model;

import model.enums.EnumRegistration;

public class Registration {
    private Integer registrationId;
    private EnumRegistration registrationType;
    private String registrationName;
    private String registrationDocument;
    private RegistrationAddress registrationAddress;

    public Registration() {}

    public Integer getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationType() {
        if(this.registrationType == null){
            return null;
        }
        return this.registrationType.name();
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

    public RegistrationAddress getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(RegistrationAddress registrationAddress) {
        this.registrationAddress = registrationAddress;
    }
}
