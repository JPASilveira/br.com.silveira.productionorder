package debug;

import model.RegistrationAddress;
import repository.RegistrationAddressDAO;

public class DebugRegistrationAddressDAO {
    public static void testRegistrationAddress(String operation) {
        RegistrationAddress registrationAddress = new RegistrationAddress();
        registrationAddress.setAddressId(1);
        registrationAddress.setAddressStreet("test2");
        registrationAddress.setAddressCity("test2");
        registrationAddress.setAddressState("TU");
        registrationAddress.setAddressNeighborhood("test2");
        registrationAddress.setAddressNumber("test2");

        switch (operation) {
            case "add":
                RegistrationAddressDAO.addAddress(registrationAddress);
                break;

            case "update":
                RegistrationAddressDAO.updateAddress(registrationAddress);
                break;

            case "delete":
                RegistrationAddressDAO.deleteAddress(registrationAddress);
        }
    }

    public static void main(String[] args) {
        testRegistrationAddress("add");
    }
}
