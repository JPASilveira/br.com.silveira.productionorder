package repository;

import model.RegistrationAddress;
import repository.exceptions.ExceptionAddressDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationAddressDAO {
    public static void addAddress(RegistrationAddress registrationAddress) {
        String sql = "INSERT INTO registration_address (address_street, address_number, address_neighborhood, address_city, address_state) values(?,?,?,?,?)";

        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,registrationAddress.getAddressStreet());
            preparedStatement.setString(2, registrationAddress.getAddressNumber());
            preparedStatement.setString(3, registrationAddress.getAddressNeighborhood());
            preparedStatement.setString(4, registrationAddress.getAddressCity());
            preparedStatement.setString(5, registrationAddress.getAddressState());

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionAddressDAO("error while adding address", e);
        }
    }

    public static void updateAddress(RegistrationAddress registrationAddress) {
        StringBuilder sql = new StringBuilder("UPDATE registration_address SET ");
        boolean hasAddressStreet = false;
        boolean hasAddressNumber = false;
        boolean hasAddressNeighborhood = false;
        boolean hasAddressCity = false;
        boolean hasAddressState = false;
        boolean isFirst = true;

        if (registrationAddress.getAddressStreet() != null) {
            sql.append("address_street = ?");
            hasAddressStreet = true;
            isFirst = false;
        }

        if (registrationAddress.getAddressNumber() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("address_number = ?");
            hasAddressNumber = true;
            isFirst = false;
        }

        if (registrationAddress.getAddressNeighborhood() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("address_neighborhood = ?");
            hasAddressNeighborhood = true;
            isFirst = false;
        }

        if (registrationAddress.getAddressCity() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("address_city = ?");
            hasAddressCity = true;
            isFirst = false;
        }

        if (registrationAddress.getAddressState() != null) {
            sql.append("address_state = ?");
            hasAddressState = true;
        }

        sql.append(" WHERE address_id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            int parameterIndex = 1;

            if (hasAddressStreet) {
                preparedStatement.setString(parameterIndex++, registrationAddress.getAddressStreet());
            }

            if (hasAddressNumber) {
                preparedStatement.setString(parameterIndex++, registrationAddress.getAddressNumber());
            }

            if (hasAddressNeighborhood) {
                preparedStatement.setString(parameterIndex++, registrationAddress.getAddressNeighborhood());
            }

            if (hasAddressCity) {
                preparedStatement.setString(parameterIndex++, registrationAddress.getAddressCity());
            }

            if (hasAddressState) {
                preparedStatement.setString(parameterIndex++, registrationAddress.getAddressState());
            }

            preparedStatement.setInt(parameterIndex, registrationAddress.getAddressId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionAddressDAO("error while updating address", e);
        }
    }

    public static void deleteAddress(RegistrationAddress registrationAddress) {
        String sql = "DELETE FROM registration_address WHERE address_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, registrationAddress.getAddressId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ExceptionAddressDAO("error while deleting address", e);
        }
    }
}
