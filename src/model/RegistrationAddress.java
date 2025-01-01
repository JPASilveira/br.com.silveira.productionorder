package model;

public class RegistrationAddress {
    private Integer addressId;
    private String addressCep;
    private String addressStreet;
    private String addressNumber;
    private String addressNeighborhood;
    private String addressCity;
    private String addressState;

    public RegistrationAddress(){}

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddressCep() {
        return addressCep;
    }

    public void setAddressCep(String addressCep) {
        this.addressCep = addressCep;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressNeighborhood() {
        return addressNeighborhood;
    }

    public void setAddressNeighborhood(String addressNeighborhood) {
        this.addressNeighborhood = addressNeighborhood;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }
}
