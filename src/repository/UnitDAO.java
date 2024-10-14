package repository;

import model.Unit;
import repository.exceptions.ExceptionUnitDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UnitDAO {
    public static void addUnit(Unit unit) {
        String sql = "INSERT INTO ProductUnit (name, unit) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, unit.getName());
            preparedStatement.setString(2, unit.getUnit());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionUnitDAO("error while adding unit", e);
        }
    }

    public static void updateUnit(Unit unit) {
        StringBuilder sql = new StringBuilder("UPDATE Applicant SET ");
        boolean hasName = false;
        boolean hasUnit = false;
        boolean isFirst = true;

        if (unit.getName() != null) {
            sql.append("name = ?");
            hasName = true;
            isFirst = false;
        }

        if (unit.getUnit() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("unit = ?");
        }

        sql.append(" WHERE id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())){
            int parameterIndex = 1;

            if (hasName) {
                preparedStatement.setString(parameterIndex++, unit.getName());
            }
            if (hasUnit) {
                preparedStatement.setString(parameterIndex++, unit.getUnit());
            }

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionUnitDAO("error while updating unit", e);
        }
    }

    public static void deleteUnit(Unit unit) {
        String sql = "DELETE FROM Applicant WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, unit.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionUnitDAO("error while deleting unit", e);
        }
    }
}
