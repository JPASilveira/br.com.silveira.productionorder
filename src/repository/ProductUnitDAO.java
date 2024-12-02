package repository;

import model.ProductUnit;
import org.jetbrains.annotations.NotNull;
import repository.exceptions.ExceptionProductUnitDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ProductUnitDAO {
    public static void addUnit(@NotNull ProductUnit productUnit) {
        String sql = "INSERT INTO product_unit (unit_name, unit_acronym) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, productUnit.getUnitName());
            preparedStatement.setString(2, productUnit.getUnitAcronym());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionProductUnitDAO("error while adding productUnit", e);
        }
    }

    public static void updateUnit(@NotNull ProductUnit productUnit) {
        StringBuilder sql = new StringBuilder("UPDATE product_unit SET ");
        boolean hasUnitName = false;
        boolean hasUnitAcronym = false;
        boolean isFirst = true;

        if (productUnit.getUnitName() != null) {
            sql.append("unit_name = ?");
            hasUnitName = true;
            isFirst = false;
        }

        if (productUnit.getUnitAcronym() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("unit_acronym = ?");
            hasUnitAcronym = true;
        }

        sql.append(" WHERE unit_id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())){
            int parameterIndex = 1;

            if (hasUnitName) {
                preparedStatement.setString(parameterIndex++, productUnit.getUnitName());
            }
            if (hasUnitAcronym) {
                preparedStatement.setString(parameterIndex++, productUnit.getUnitAcronym());
            }

            preparedStatement.setInt(parameterIndex++, productUnit.getUnitId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionProductUnitDAO("error while updating productUnit", e);
        }
    }

    public static void deleteUnit(@NotNull ProductUnit productUnit) {
        String sql = "DELETE FROM product_unit WHERE unit_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, productUnit.getUnitId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionProductUnitDAO("error while deleting productUnit", e);
        }
    }

    public static @NotNull Optional<ArrayList<ProductUnit>> getProductUnitById(Integer productUnitId) {
        String sql = "SELECT * FROM product_unit WHERE unit_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, productUnitId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProductUnits(resultSet);
            }
        } catch (SQLException e) {
            throw new ExceptionProductUnitDAO("error while getting product units", e);
        }
    }

    public static @NotNull Optional<ArrayList<ProductUnit>> getProductUnitByName(String productUnitName) {
        String sql = "SELECT * FROM product_unit WHERE unit_name LIKE ?";
        productUnitName = "%" + productUnitName + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, productUnitName);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    return getProductUnits(resultSet);
                }
        }catch (SQLException e) {
            throw new ExceptionProductUnitDAO("error while getting product units", e);
        }
    }

    public static @NotNull Optional<ArrayList<ProductUnit>> getProductUnitsByAcronym(String unitAcronym) {
        String sql = "SELECT * FROM product_unit WHERE unit_acronym LIKE ?";
        unitAcronym = "%" + unitAcronym + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, unitAcronym);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    return getProductUnits(resultSet);
                }
        }catch (SQLException e) {
            throw new ExceptionProductUnitDAO("error while getting productUnits", e);
        }
    }

    public static @NotNull Optional<ArrayList<ProductUnit>> getAllProductUnits() {
        String sql = "SELECT * FROM product_unit";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProductUnits(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductUnitDAO("error while getting product units", e);
        }
    }

    @NotNull
    public static Optional<ArrayList<ProductUnit>> getProductUnits(@NotNull ResultSet resultSet) throws SQLException {
        ArrayList<ProductUnit> productUnits = new ArrayList<>();
        while (resultSet.next()) {
            ProductUnit productUnit = new ProductUnit();
            productUnit.setUnitId(resultSet.getInt("unit_id"));
            productUnit.setUnitName(resultSet.getString("unit_name"));
            productUnit.setUnitAcronym(resultSet.getString("unit_acronym"));
            productUnits.add(productUnit);
        }

        return productUnits.isEmpty() ? Optional.empty() : Optional.of(productUnits);
    }
}