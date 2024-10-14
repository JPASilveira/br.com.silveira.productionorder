package model;

import model.enums.EnumRegistration;

public class Registration {
    private Integer id;
    private EnumRegistration registrationType;
    private String name;
    private String document;
    private Address address;

    public Registration() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EnumRegistration getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(EnumRegistration registrationType) {
        this.registrationType = registrationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
