package controller;

import controller.exceptions.RegistrationControllerException;
import model.Registration;
import repository.RegistrationDAO;
import util.CpfCnpjValidator;

import java.util.ArrayList;
import java.util.Optional;

public class RegistrationController {
    public static void addRegistration(String registrationType, String registrationName, String registrationFantasyName, String registrationDocument, String registrationIE, String registrationContactNumber, String registrationEmail, String registrationAddressId) {
        if(registrationName == null || registrationName.trim().isEmpty() || registrationFantasyName.length() > 100) {
            throw new RegistrationControllerException("Nome inválido");
        }
        if(registrationDocument == null || registrationDocument.trim().isEmpty() || registrationDocument.length() > 20) {
            throw new RegistrationControllerException("Documento inválido, deve ser inserido apenas números");
        }
        if(registrationContactNumber == null || registrationContactNumber.trim().isEmpty() || registrationContactNumber.length() > 20) {
            throw new RegistrationControllerException("Contato inválido");
        }
        if(registrationEmail == null || registrationEmail.trim().isEmpty() || registrationEmail.length() > 50) {
            throw new RegistrationControllerException("Email inválido");
        }
        if(registrationAddressId == null || registrationAddressId.trim().isEmpty() || !registrationAddressId.matches("\\d+")) {
            throw new RegistrationControllerException("Endereço inválido");
        }
        if(CpfCnpjValidator.isValidCnpj(registrationDocument)){
            if(registrationFantasyName == null || registrationFantasyName.trim().isEmpty() || registrationFantasyName.length() > 100) {
                throw new RegistrationControllerException("Nome fantasia inválido.");
            }
            if(registrationIE == null || registrationIE.trim().isEmpty() || !registrationIE.matches("\\d+") || registrationIE.length() > 20) {
                throw new RegistrationControllerException("Inscrição estadual inválida, deve ser inserido apenas números");
            }
        }

        Registration registration = new Registration();

        registration.setRegistrationType(registrationType);
        registration.setRegistrationName(registrationName);
        registration.setRegistrationFantasyName(registrationFantasyName);
        registration.setRegistrationDocument(registrationDocument);
        registration.setRegistrationIE(registrationIE);
        registration.setRegistrationContactNumber(registrationContactNumber);
        registration.setRegistrationEmail(registrationEmail);
        try {
            registration.setRegistrationAddress(RegistrationAddressController.getRegistrationAddressById(registrationAddressId));
        }catch (Exception e) {
            throw new RegistrationControllerException("Erro ao obter endereço do registro");
        }

        try {
            RegistrationDAO.registrationAdd(registration);
        }catch (Exception e) {
            throw new RegistrationControllerException("Erro ao inserir registro");
        }
    }

    public static void updateRegistration(String registrationId ,String registrationType, String registrationName, String registrationFantasyName, String registrationDocument, String registrationIE, String registrationContactNumber, String registrationEmail, String registrationAddressId) {
        if(registrationName == null || registrationName.trim().isEmpty() || registrationFantasyName.length() > 100) {
            throw new RegistrationControllerException("Nome inválido");
        }
        if(registrationDocument == null || registrationDocument.trim().isEmpty() || registrationDocument.length() > 20) {
            throw new RegistrationControllerException("Documento inválido, deve ser inserido apenas números");
        }
        if(registrationContactNumber == null || registrationContactNumber.trim().isEmpty() || registrationContactNumber.length() > 20) {
            throw new RegistrationControllerException("Contato inválido");
        }
        if(registrationEmail == null || registrationEmail.trim().isEmpty() || registrationEmail.length() > 50) {
            throw new RegistrationControllerException("Email inválido");
        }
        if(registrationAddressId == null || registrationAddressId.trim().isEmpty() || !registrationAddressId.matches("\\d+")) {
            throw new RegistrationControllerException("Endereço inválido");
        }
        if(CpfCnpjValidator.isValidCnpj(registrationDocument)){
            if(registrationFantasyName == null || registrationFantasyName.trim().isEmpty() || registrationFantasyName.length() > 100) {
                throw new RegistrationControllerException("Nome fantasia inválido.");
            }
            if(registrationIE == null || registrationIE.trim().isEmpty() || !registrationIE.matches("\\d+") || registrationIE.length() > 20) {
                throw new RegistrationControllerException("Inscrição estadual inválida, deve ser inserido apenas números");
            }
        }

        Registration registration = new Registration();

        registration.setRegistrationId(Integer.valueOf(registrationId));
        registration.setRegistrationType(registrationType);
        registration.setRegistrationName(registrationName);
        registration.setRegistrationFantasyName(registrationFantasyName);
        registration.setRegistrationDocument(registrationDocument);
        registration.setRegistrationIE(registrationIE);
        registration.setRegistrationContactNumber(registrationContactNumber);
        registration.setRegistrationEmail(registrationEmail);
        try {
            registration.setRegistrationAddress(RegistrationAddressController.getRegistrationAddressById(registrationAddressId));
        }catch (Exception e) {
            throw new RegistrationControllerException("Erro ao obter endereço do registro");
        }
        try {
            registration.setRegistrationAddress(RegistrationAddressController.getRegistrationAddressById(registrationAddressId));
        }catch (Exception e) {
            throw new RegistrationControllerException("Erro ao obter endereço do registro");
        }

        try {
            RegistrationDAO.registrationUpdate(registration);
        }catch (Exception e) {
            throw new RegistrationControllerException(e.getMessage());
        }
    }

    public static void deleteRegistration(String registrationId) {
        if(registrationId == null || registrationId.trim().isEmpty() || !registrationId.matches("\\d+")) {
            throw new RegistrationControllerException("Erro de ID");
        }

        Registration registration = new Registration();
        registration.setRegistrationId(Integer.valueOf(registrationId));

        try {
            RegistrationDAO.deleteRegistration(registration);
        }
        catch (Exception e) {
            throw new RegistrationControllerException("Erro ao remover registro, verifique se não existe algum vinculo com outro registro");
        }
    }

    public static Object[][] searchRegistration(String cmbSearch, String registrationSearch) {
        ArrayList<Registration> data;
        Optional<ArrayList<Registration>> result;

        if(cmbSearch == null) {
            throw new RegistrationControllerException("A busca não pode ser nula");
        }
        if(registrationSearch.trim().isEmpty()) {
            cmbSearch = "ALL";
        }

        switch (cmbSearch) {
            case "NOME":
                result = RegistrationDAO.getByName(registrationSearch);
                if(result.isPresent()) {
                    data = result.get();
                    return convertRegistrationListToTableData(data);
                }
                return new Object[0][0];

            case "NOME FANTASIA":
                result = RegistrationDAO.getByFantasyName(registrationSearch);
                if(result.isPresent()) {
                    data = result.get();
                    return convertRegistrationListToTableData(data);
                }
                return new Object[0][0];

            case "DOCUMENTO":
                result = RegistrationDAO.getByDocument(registrationSearch);
                if(result.isPresent()) {
                    data = result.get();
                    return convertRegistrationListToTableData(data);
                }
                return new Object[0][0];

            case "CONTATO":
                result = RegistrationDAO.getByContact(registrationSearch);
                if(result.isPresent()) {
                    data = result.get();
                    return convertRegistrationListToTableData(data);
                }
                return new Object[0][0];

            default:
                result = RegistrationDAO.getAllRegistrations();
                if(result.isPresent()) {
                    data = result.get();
                    return convertRegistrationListToTableData(data);
                }
                return new Object[0][0];
        }
    }

    private static Object[][] convertRegistrationListToTableData(ArrayList<Registration> registrationList) {
        Object[][] data = new Object[registrationList.size()][10];

        for (int i = 0; i < registrationList.size(); i++) {
            Registration registration = registrationList.get(i);
            data[i][0] = registration.getRegistrationId();
            data[i][1] = registration.getRegistrationType();
            data[i][2] = registration.getRegistrationName();
            data[i][3] = registration.getRegistrationFantasyName();
            data[i][4] = registration.getRegistrationDocument();
            data[i][5] = registration.getRegistrationIE();
            data[i][6] = registration.getRegistrationContactNumber();
            data[i][7] = registration.getRegistrationEmail();
            data[i][8] = registration.getRegistrationAddress().getAddressId();
            data[i][9] = String.format("%s, %s", registration.getRegistrationAddress().getAddressStreet(), registration.getRegistrationAddress().getAddressNumber());
        }
        return data;
    }
}
