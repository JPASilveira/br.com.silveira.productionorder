package repository;


import model.RegistrationAddress;
import org.jetbrains.annotations.NotNull;
import repository.exceptions.ExceptionProductDAO;
import repository.exceptions.ExceptionRegistrationAddressDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RegistrationAddressDAO {
    public static void addAddress(RegistrationAddress registrationAddress) {
        String sql = "INSERT INTO registration_address (address_cep, address_street, address_number, address_neighborhood, address_city, address_state) values(?,?,?,?,?,?)";

        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, registrationAddress.getAddressCep());
            preparedStatement.setString(2,registrationAddress.getAddressStreet());
            preparedStatement.setString(3, registrationAddress.getAddressNumber());
            preparedStatement.setString(4, registrationAddress.getAddressNeighborhood());
            preparedStatement.setString(5, registrationAddress.getAddressCity());
            preparedStatement.setString(6, registrationAddress.getAddressState());

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionRegistrationAddressDAO("error while adding address", e);
        }
    }

    public static void updateAddress(RegistrationAddress registrationAddress) {
        StringBuilder sql = new StringBuilder("UPDATE registration_address SET ");
        boolean hasAddressCep = false;
        boolean hasAddressStreet = false;
        boolean hasAddressNumber = false;
        boolean hasAddressNeighborhood = false;
        boolean hasAddressCity = false;
        boolean hasAddressState = false;
        boolean isFirst = true;
        if (registrationAddress.getAddressCep() != null) {
            sql.append("address_cep = ?");
            hasAddressCep = true;
            isFirst = false;
        }

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

            if (hasAddressCep) {
                preparedStatement.setString(parameterIndex++, registrationAddress.getAddressCep());
            }

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
            throw new ExceptionRegistrationAddressDAO("error while updating address", e);
        }
    }

    public static void deleteAddress(RegistrationAddress registrationAddress) {
        String sql = "DELETE FROM registration_address WHERE address_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, registrationAddress.getAddressId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ExceptionRegistrationAddressDAO("error while deleting address", e);
        }
    }

    public static @NotNull Optional<ArrayList<RegistrationAddress>> getRegistrationAddressesById(Integer id) {
        String sql = "SELECT * FROM registration_address WHERE address_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement. executeQuery()){
                return getRegistrationAddress(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationAddressDAO("error while getting registration addresses", e);
        }
    }
    public static @NotNull Optional<ArrayList<RegistrationAddress>> getRegistrationAddressesByCep(String cep) {
        String sql = "SELECT * FROM registration_address WHERE address_cep LIKE ?";
       cep = "%" + cep + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, cep);
            try (ResultSet resultSet = preparedStatement. executeQuery()){
                return getRegistrationAddress(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationAddressDAO("error while getting registration addresses", e);
        }
    }

    public static @NotNull Optional<ArrayList<RegistrationAddress>> getRegistrationAddressesByStreet(String street) {
        String sql = "SELECT * FROM registration_address WHERE address_street LIKE ?";
        street = "%" + street + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, street);
            try (ResultSet resultSet = preparedStatement. executeQuery()){
                return getRegistrationAddress(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationAddressDAO("error while getting registration addresses", e);
        }
    }

    public static @NotNull Optional<ArrayList<RegistrationAddress>> getRegistrationAddressesByNeighborhood(String neighborhood) {
        String sql = "SELECT * FROM registration_address WHERE address_neighborhood LIKE ?";
        neighborhood = "%" + neighborhood + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, neighborhood);
            try (ResultSet resultSet = preparedStatement. executeQuery()){
                return getRegistrationAddress(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationAddressDAO("error while getting registration addresses", e);
        }
    }

    public static @NotNull Optional<ArrayList<RegistrationAddress>> getRegistrationAddressesByCity(String city) {
        String sql = "SELECT * FROM registration_address WHERE address_city LIKE ?";
        city = "%" + city + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, city);
            try (ResultSet resultSet = preparedStatement. executeQuery()){
                return getRegistrationAddress(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationAddressDAO("error while getting registration addresses", e);
        }
    }

    public static @NotNull Optional<ArrayList<RegistrationAddress>> getRegistrationAddressesByState(String state) {
        String sql = "SELECT * FROM registration_address WHERE address_state LIKE ?";
        state = "%" + state + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, state);
            try (ResultSet resultSet = preparedStatement. executeQuery()){
                return getRegistrationAddress(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationAddressDAO("error while getting registration addresses", e);
        }
    }

    public static @NotNull Optional<ArrayList<RegistrationAddress>> getAllRegistrationAddresses() {
        String sql = "SELECT * FROM registration_address";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try (ResultSet resultSet = preparedStatement. executeQuery()){
                return getRegistrationAddress(resultSet);
            }
        }catch (SQLException e){
            throw new ExceptionRegistrationAddressDAO("error while getting registration addresses", e);
        }
    }

    @NotNull
    public static Optional<ArrayList<RegistrationAddress>> getRegistrationAddress(@NotNull ResultSet resultSet) {
        ArrayList<RegistrationAddress> addresses = new ArrayList<>();
        try {
            while (resultSet.next()) {
                RegistrationAddress registrationAddress = new RegistrationAddress();

                registrationAddress.setAddressId(resultSet.getInt("address_id"));
                registrationAddress.setAddressCep(resultSet.getString("address_cep"));
                registrationAddress.setAddressStreet(resultSet.getString("address_street"));
                registrationAddress.setAddressNumber(resultSet.getString("address_number"));
                registrationAddress.setAddressNeighborhood(resultSet.getString("address_neighborhood"));
                registrationAddress.setAddressCity(resultSet.getString("address_city"));
                registrationAddress.setAddressState(resultSet.getString("address_state"));

                addresses.add(registrationAddress);
            }

            return addresses.isEmpty() ? Optional.empty() : Optional.of(addresses);

        } catch (SQLException e) {
            throw new ExceptionProductDAO("error while getting addresses", e);
        }
    }
}
