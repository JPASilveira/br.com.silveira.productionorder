package repository;

import model.ProductUnit;
import repository.exceptions.ExceptionProductUnitDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductUnitDAO {
    public static void addUnit(ProductUnit productUnit) {
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

    public static void updateUnit(ProductUnit productUnit) {
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

    public static void deleteUnit(ProductUnit productUnit) {
        String sql = "DELETE FROM product_unit WHERE unit_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, productUnit.getUnitId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionProductUnitDAO("error while deleting productUnit", e);
        }
    }
}
