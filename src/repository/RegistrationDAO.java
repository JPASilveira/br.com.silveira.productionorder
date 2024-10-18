package repository;

import model.Registration;
import repository.exceptions.ExceptionRegistrationDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationDAO {
    /*public static void registrationAdd(Registration registration) {
        String sql = "INSERT INTO Registration (RegistrationType, Name, Document, Address_ID) values(?,?,?,?)";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, registration.getRegistrationType());
            preparedStatement.setString(2, registration.getName());
            preparedStatement.setString(3, registration.getDocument());
            preparedStatement.setInt(4, registration.getAddress().getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionRegistrationDAO("error while adding registration", e);
        }
    }

    public static void registrationUpdate(Registration registration) {
        StringBuilder sql = new StringBuilder("UPDATE Registration SET ");
        boolean hasRegistrationType = false;
        boolean hasName = false;
        boolean hasDocument = false;
        boolean hasAddressId = false;
        boolean isFirst = true;

        if (registration.getRegistrationType() != null) {
            sql.append("RegistrationType = ?");
            hasRegistrationType = true;
            isFirst = false;
        }

        if (registration.getName() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("Name = ?");
            hasName = true;
            isFirst = false;
        }

        if (registration.getDocument() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("Document = ?");
            hasDocument = true;
            isFirst = false;
        }

        if (registration.getAddress().getId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("Address_ID = ?");
            hasAddressId = true;
        }

        sql.append(" WHERE id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())){
            int parameterIndex = 1;

            if (hasRegistrationType) {
                preparedStatement.setString(parameterIndex++, registration.getRegistrationType());
            }

            if (hasName) {
                preparedStatement.setString(parameterIndex++, registration.getName());
            }

            if (hasDocument) {
                preparedStatement.setString(parameterIndex, registration.getDocument());
            }

            if (hasAddressId) {
                preparedStatement.setInt(parameterIndex++, registration.getAddress().getId());
            }

            preparedStatement.setInt(parameterIndex++, registration.getId());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ExceptionRegistrationDAO("error while updating registration", e);
        }
    }

    public static void RegistrationDelete(Registration registration) {
        String sql = "DELETE FROM Registration WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, registration.getId());
        } catch (SQLException e) {
            throw new ExceptionRegistrationDAO("error while deleting registration", e);
        }
    }*/
}
