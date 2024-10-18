package repository;

import model.Address;
import repository.exceptions.ExceptionAddressDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddressDAO {
    /*public static void addAddress(Address address) {
        String sql = "INSERT INTO RegistrationAddress (street, number, neighborhood, city, state) values(?,?,?,?,?)";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,address.getStreet());
            preparedStatement.setString(2, address.getNumber());
            preparedStatement.setString(3, address.getNeighborhood());
            preparedStatement.setString(4, address.getCity());
            preparedStatement.setString(5, address.getState());

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionAddressDAO("error while adding address", e);
        }
    }

    public static void updateAddress(Address address) {
        StringBuilder sql = new StringBuilder("UPDATE Applicant SET ");
        boolean hasStreet = false;
        boolean hasNumber = false;
        boolean hasNeighborhood = false;
        boolean hasCity = false;
        boolean hasState = false;
        boolean isFirst = true;

        if (address.getStreet() != null) {
            sql.append("street = ?");
            hasStreet = true;
            isFirst = false;
        }

        if (address.getNumber() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("number = ?");
            hasNumber = true;
            isFirst = false;
        }

        if (address.getNeighborhood() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("neighborhood = ?");
            hasNeighborhood = true;
            isFirst = false;
        }

        if (address.getCity() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("city = ?");
            hasCity = true;
            isFirst = false;
        }

        if (address.getState() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("state = ?");
            hasState = true;
            isFirst = false;
        }

        sql.append(" WHERE id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            int parameterIndex = 1;

            if (hasStreet) {
                preparedStatement.setString(parameterIndex++, address.getStreet());
            }

            if (hasNumber) {
                preparedStatement.setString(parameterIndex++, address.getNumber());
            }

            if (hasNeighborhood) {
                preparedStatement.setString(parameterIndex++, address.getNeighborhood());
            }

            if (hasCity) {
                preparedStatement.setString(parameterIndex++, address.getCity());
            }

            if (hasState) {
                preparedStatement.setString(parameterIndex++, address.getState());
            }

            preparedStatement.setInt(parameterIndex, address.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionAddressDAO("error while updating address", e);
        }
    }

    public static void deleteAddress(Address address) {
        String sql = "DELETE FROM RegistrationAddress WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, address.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ExceptionAddressDAO("error while deleting address", e);
        }
    }*/
}
