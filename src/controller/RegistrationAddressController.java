package controller;

import controller.exceptions.RegistrationAddressException;
import model.RegistrationAddress;
import repository.RegistrationAddressDAO;

import java.util.ArrayList;
import java.util.Optional;

public class RegistrationAddressController {
    public static void addRegistrationAddress(String cep, String street, String number, String neighborhood, String city, String state) {
        if (cep == null || cep.trim().isEmpty() || !cep.matches("\\d+")) {
            throw new RegistrationAddressException("O CEP deve ser preenchido apenas com números");
        }
        if(street == null || street.trim().isEmpty()){
            throw new RegistrationAddressException("Rua inválida ou nula");
        }
        if(number == null || number.trim().isEmpty()){
            throw new RegistrationAddressException("Número invalido ou nulo");
        }
        if(neighborhood == null || neighborhood.trim().isEmpty()){
            throw new RegistrationAddressException("Bairro invalido ou nulo");
        }
        if(city == null || city.trim().isEmpty()){
            throw new RegistrationAddressException("Número invalido ou nulo");
        }

        RegistrationAddress address = new RegistrationAddress();
        address.setAddressCep(cep);
        address.setAddressStreet(street);
        address.setAddressNumber(number);
        address.setAddressNeighborhood(neighborhood);
        address.setAddressCity(city);
        address.setAddressState(state);

        try {
            RegistrationAddressDAO.addAddress(address);
        } catch (Exception ex) {
            throw new RegistrationAddressException("Erro ao adicionar endereço");
        }
    }

    public static void updateRegistrationAddress(String id, String cep, String street, String number, String neighborhood, String city, String state) {
        if(id == null || id.trim().isEmpty()){
           throw new RegistrationAddressException("ID inválido");
        }
        if (cep == null || cep.trim().isEmpty() || !cep.matches("\\d+")) {
            throw new RegistrationAddressException("O CEP deve ser preenchido apenas com números");
        }
        if(street == null || street.trim().isEmpty()){
            throw new RegistrationAddressException("Rua inválida ou nula");
        }
        if(number == null || number.trim().isEmpty()){
            throw new RegistrationAddressException("Número invalido ou nulo");
        }
        if(neighborhood == null || neighborhood.trim().isEmpty()){
            throw new RegistrationAddressException("Bairro invalido ou nulo");
        }
        if(city == null || city.trim().isEmpty()){
            throw new RegistrationAddressException("Número invalido ou nulo");
        }

        RegistrationAddress address = new RegistrationAddress();
        address.setAddressId(Integer.parseInt(id));
        address.setAddressCep(cep);
        address.setAddressStreet(street);
        address.setAddressNumber(number);
        address.setAddressNeighborhood(neighborhood);
        address.setAddressCity(city);
        address.setAddressState(state);

        try {
            RegistrationAddressDAO.updateAddress(address);
        } catch (Exception ex) {
            throw new RegistrationAddressException("Erro ao adicionar endereço");
        }
    }

    public static void deleteRegistrationAddress(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new RegistrationAddressException("O ID não pode ser nulo ou inválido");
        }
        RegistrationAddress address = new RegistrationAddress();
        address.setAddressId(Integer.parseInt(id));

        RegistrationAddressDAO.deleteAddress(address);
    }

    public static Object[][] searchAddress(String cmbSearch, String addressSearch) {
        ArrayList<RegistrationAddress> data;
        Optional<ArrayList<RegistrationAddress>> result;

        if (addressSearch == null) {
            throw new RegistrationAddressException("A busca não pode ser nula");
        }
        if (addressSearch.trim().isEmpty()) {
            cmbSearch = "ALL";
        }

        switch (cmbSearch) {
            case "CEP":
                try {
                    if (!addressSearch.matches("\\d+")) {
                        throw new RegistrationAddressException("Rua CEP inválido");
                    }

                    result = RegistrationAddressDAO.getRegistrationAddressesByCep(addressSearch);

                    if (result.isPresent()) {
                        data = result.get();
                        return convertRegistrationAddressToTableData(data);
                    }
                    return new Object[0][0];
                } catch (Exception e) {
                    throw new RegistrationAddressException("Rua CEP inválido");
                }

            case "RUA":
                try {
                    result = RegistrationAddressDAO.getRegistrationAddressesByStreet(addressSearch);
                    if (result.isPresent()) {
                        data = result.get();
                        return convertRegistrationAddressToTableData(data);
                    }
                    return new Object[0][0];
                } catch (Exception e) {
                    throw new RegistrationAddressException("Rua inválida");
                }

            case "BAIRRO":
                try {
                    result = RegistrationAddressDAO.getRegistrationAddressesByNeighborhood(addressSearch);
                    if (result.isPresent()) {
                        data = result.get();
                        return convertRegistrationAddressToTableData(data);
                    }
                    return new Object[0][0];
                } catch (Exception e) {
                    throw new RegistrationAddressException("Bairro inválido");
                }

            case "CIDADE":
                try {
                    result = RegistrationAddressDAO.getRegistrationAddressesByCity(addressSearch);
                    if (result.isPresent()) {
                        data = result.get();
                        return convertRegistrationAddressToTableData(data);
                    }
                    return new Object[0][0];
                } catch (Exception e) {
                    throw new RegistrationAddressException("Cidade inválida");
                }

            case "ESTADO":
                try {
                    result = RegistrationAddressDAO.getRegistrationAddressesByState(addressSearch);
                    if (result.isPresent()) {
                        data = result.get();
                        return convertRegistrationAddressToTableData(data);
                    }
                    return new Object[0][0];
                } catch (Exception e) {
                    throw new RegistrationAddressException("Estado inválido");
                }

            default:
                result = RegistrationAddressDAO.getAllRegistrationAddresses();
                if (result.isPresent()) {
                    data = result.get();
                    return convertRegistrationAddressToTableData(data);
                }
                return new Object[0][0];
        }
    }

    public static String returnLastIdRegister(){
        try {
            Optional<ArrayList<RegistrationAddress>> data = RegistrationAddressDAO.getAllRegistrationAddresses();
            ArrayList<RegistrationAddress> registrationAddress = data.get();
            return String.valueOf(registrationAddress.get(registrationAddress.size() - 1).getAddressId());
        }catch (Exception e) {
            throw new RegistrationAddressException("Erro ao retornar id do endereço");
        }
    }

    private static Object[][] convertRegistrationAddressToTableData(ArrayList<RegistrationAddress> addresses) {
        Object[][] data = new Object[addresses.size()][7];

        for (int i = 0; i < addresses.size(); i++) {
            RegistrationAddress address = addresses.get(i);
            data[i][0] = address.getAddressId();
            data[i][1] = address.getAddressCep();
            data[i][2] = address.getAddressStreet();
            data[i][3] = address.getAddressNumber();
            data[i][4] = address.getAddressNeighborhood();
            data[i][5] = address.getAddressCity();
            data[i][6] = address.getAddressState();
        }
        return data;
    }
}
