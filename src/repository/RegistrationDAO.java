package repository;

import model.Registration;
import model.RegistrationAddress;
import org.jetbrains.annotations.NotNull;
import repository.exceptions.ExceptionRegistrationDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RegistrationDAO {
    public static void registrationAdd(Registration registration) {
        String sql = "INSERT INTO registration (registration_type, registration_name, registration_fantasy_name, registration_document, registration_ie, registration_contact_number, registration_email, registration_address_ID) values(?,?,?,?,?,?,?,?)";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, registration.getRegistrationType());
            preparedStatement.setString(2, registration.getRegistrationName());
            preparedStatement.setString(3, registration.getRegistrationFantasyName());
            preparedStatement.setString(4, registration.getRegistrationDocument());
            preparedStatement.setString(5, registration.getRegistrationIE());
            preparedStatement.setString(6, registration.getRegistrationContactNumber());
            preparedStatement.setString(7, registration.getRegistrationEmail());
            preparedStatement.setInt(8,registration.getRegistrationAddress().getAddressId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionRegistrationDAO("error while adding registration", e);
        }
    }

    public static void registrationUpdate(Registration registration) {
        StringBuilder sql = new StringBuilder("UPDATE registration SET ");
        boolean hasRegistrationType = false;
        boolean hasRegistrationName = false;
        boolean hasRegistrationFantasyName = false;
        boolean hasRegistrationDocument = false;
        boolean hasRegistrationIE = false;
        boolean hasRegistrationContactNumber = false;
        boolean hasRegistrationEmail = false;
        boolean hasRegistrationAddressId = false;
        boolean isFirst = true;

        if (registration.getRegistrationType() != null) {
            sql.append("registration_type = ?");
            hasRegistrationType = true;
            isFirst = false;
        }

        if (registration.getRegistrationName() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("registration_name = ?");
            hasRegistrationName = true;
            isFirst = false;
        }

        if (registration.getRegistrationFantasyName() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("registration_fantasy_name = ?");
            hasRegistrationFantasyName = true;
            isFirst = false;
        }

        if (registration.getRegistrationDocument() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("registration_document = ?");
            hasRegistrationDocument = true;
            isFirst = false;
        }

        if (registration.getRegistrationIE() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("registration_ie = ?");
            hasRegistrationIE = true;
            isFirst = false;
        }

        if (registration.getRegistrationContactNumber() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("registration_contact_number = ?");
            hasRegistrationContactNumber = true;
            isFirst = false;
        }

        if (registration.getRegistrationEmail() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("registration_email = ?");
            hasRegistrationEmail = true;
            isFirst = false;
        }

        if (registration.getRegistrationAddress().getAddressId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("registration_address_ID = ?");
            hasRegistrationAddressId = true;
        }

        sql.append(" WHERE registration_id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())){
            int parameterIndex = 1;

            if (hasRegistrationType) {
                preparedStatement.setString(parameterIndex++, registration.getRegistrationType());
            }

            if (hasRegistrationName) {
                preparedStatement.setString(parameterIndex++, registration.getRegistrationName());
            }

            if (hasRegistrationFantasyName) {
                preparedStatement.setString(parameterIndex++, registration.getRegistrationFantasyName());
            }

            if (hasRegistrationDocument) {
                preparedStatement.setString(parameterIndex++, registration.getRegistrationDocument());
            }

            if (hasRegistrationIE) {
                preparedStatement.setString(parameterIndex++, registration.getRegistrationIE());
            }

            if (hasRegistrationContactNumber) {
                preparedStatement.setString(parameterIndex++, registration.getRegistrationContactNumber());
            }

            if (hasRegistrationEmail) {
                preparedStatement.setString(parameterIndex++, registration.getRegistrationEmail());
            }

            if (hasRegistrationAddressId) {
                preparedStatement.setInt(parameterIndex++, registration.getRegistrationAddress().getAddressId());
            }

            preparedStatement.setInt(parameterIndex++, registration.getRegistrationId());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(sql.toString());
            throw new ExceptionRegistrationDAO("error while editing registration", e);
        }
    }

    public static void deleteRegistration(Registration registration) {
        String sql = "DELETE FROM registration WHERE registration_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, registration.getRegistrationId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ExceptionRegistrationDAO("error while deleting registration", e);
        }
    }

    public static @NotNull Optional<ArrayList<Registration>> getByRegistrationId(Integer registrationId) {
        String sql = "SELECT * FROM registration WHERE registration_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, registrationId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getRegistrations(resultSet);
            }
        } catch (SQLException e) {
            throw new ExceptionRegistrationDAO("error while retrieving registration", e);
        }
    }

    public static @NotNull  Optional<ArrayList<Registration>> getByName(String registrationName) {
        String sql = "SELECT * FROM registration WHERE registration_name LIKE ?";
        registrationName = "%" + registrationName + "%";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, registrationName);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getRegistrations(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationDAO("error while retrieving registration", e);
        }
    }

    public static @NotNull Optional<ArrayList<Registration>> getByFantasyName(String registrationFantasyName) {
        String sql = "SELECT * FROM registration WHERE registration_fantasy_name LIKE ?";
        registrationFantasyName = "%" + registrationFantasyName + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, registrationFantasyName);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getRegistrations(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationDAO("error while retrieving registration", e);
        }
    }

    public static @NotNull  Optional<ArrayList<Registration>> getByDocument(String registrationDocument) {
        String sql = "SELECT * FROM registration WHERE registration_document LIKE ?";
        registrationDocument = "%" + registrationDocument + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, registrationDocument);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getRegistrations(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationDAO("error while retrieving registration", e);
        }
    }

    public static @NotNull  Optional<ArrayList<Registration>> getByContact(String registrationContact) {
        String sql = "SELECT * FROM registration WHERE registration_contact_number LIKE ?";
        registrationContact = "%" + registrationContact + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, registrationContact);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getRegistrations(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationDAO("error while retrieving registration", e);
        }
    }

    public static @NotNull Optional<ArrayList<Registration>> getAllRegistrations() {
        String sql = "SELECT * FROM registration";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getRegistrations(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationDAO("error while retrieving registration", e);
        }
    }

    @NotNull
    public static Optional<ArrayList<Registration>> getRegistrations(@NotNull ResultSet resultSet) {
        ArrayList<Registration> registrations = new ArrayList<>();
        try {
            while (resultSet.next()){
                Registration registration = new Registration();
                @NotNull Optional<ArrayList<RegistrationAddress>> registrationAddress = RegistrationAddressDAO.getRegistrationAddressesById(resultSet.getInt("registration_address_id"));

                registration.setRegistrationId(resultSet.getInt("registration_id"));
                registration.setRegistrationType(resultSet.getString("registration_type"));
                registration.setRegistrationName(resultSet.getString("registration_name"));
                registration.setRegistrationFantasyName(resultSet.getString("registration_fantasy_name"));
                registration.setRegistrationDocument(resultSet.getString("registration_document"));
                registration.setRegistrationIE(resultSet.getString("registration_ie"));
                registration.setRegistrationContactNumber(resultSet.getString("registration_contact_number"));
                registration.setRegistrationEmail(resultSet.getString("registration_email"));

                if(registrationAddress.isPresent()){
                    registration.setRegistrationAddress(registrationAddress.get().getFirst());
                }else {
                    throw new ExceptionRegistrationDAO("not found address", null);
                }
                registrations.add(registration);
            }
            return registrations.isEmpty() ? Optional.empty() : Optional.of(registrations);

        }catch (SQLException e){
            throw new ExceptionRegistrationDAO("error while retrieving registrations", e);
        }
    }

    public static boolean existsById(String id) {
        String sql = "SELECT 1 FROM registration WHERE registration_id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,Integer.parseInt(id));
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
        } catch (Exception e) {
            throw new ExceptionRegistrationDAO("error while retrieving registration", e);
        }
    }
}
