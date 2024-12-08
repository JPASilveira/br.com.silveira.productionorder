package repository;

import model.Registration;
import repository.exceptions.ExceptionRegistrationDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
                preparedStatement.setString(parameterIndex, registration.getRegistrationDocument());
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
            throw new ExceptionRegistrationDAO("error while updating registration", e);
        }
    }

    public static void RegistrationDelete(Registration registration) {
        String sql = "DELETE FROM Registration WHERE registration_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, registration.getRegistrationId());
        } catch (SQLException e) {
            throw new ExceptionRegistrationDAO("error while deleting registration", e);
        }
    }
}
