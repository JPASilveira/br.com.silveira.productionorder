package model;

public class Registration {
    private Integer registrationId;
    private String registrationType;
    private String registrationName;
    private String registrationFantasyName;
    private String registrationDocument;
    private String registrationIE;
    private String registrationContactNumber;
    private String registrationEmail;
    private RegistrationAddress registrationAddress;

    public Registration() {}

    public Integer getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getRegistrationName() {
        return registrationName;
    }

    public void setRegistrationName(String registrationName) {
        this.registrationName = registrationName;
    }

    public String getRegistrationFantasyName() {
        return registrationFantasyName;
    }

    public void setRegistrationFantasyName(String registrationFantasyName) {
        this.registrationFantasyName = registrationFantasyName;
    }

    public String getRegistrationDocument() {
        return registrationDocument;
    }

    public void setRegistrationDocument(String registrationDocument) {
        this.registrationDocument = registrationDocument;
    }

    public String getRegistrationIE() {
        return registrationIE;
    }

    public void setRegistrationIE(String registrationIE) {
        this.registrationIE = registrationIE;
    }

    public String getRegistrationContactNumber() {
        return registrationContactNumber;
    }

    public void setRegistrationContactNumber(String registrationContactNumber) {
        this.registrationContactNumber = registrationContactNumber;
    }

    public String getRegistrationEmail() {
        return registrationEmail;
    }

    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }

    public RegistrationAddress getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(RegistrationAddress registrationAddress) {
        this.registrationAddress = registrationAddress;
    }
}
