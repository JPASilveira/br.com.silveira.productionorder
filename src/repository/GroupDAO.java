package repository;

import model.Group;
import repository.exceptions.ExceptionGroupDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GroupDAO {
    public static void addGroup(Group group) {
        String sql = "INSERT INTO ProductGroup (name) VALUES (?)";

        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, group.getName());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionGroupDAO("error while adding group", e);
        }
    }

    public static void updateGroup(Group group) {
        String sql = "UPDATE ProductGroup SET name = ? WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, group.getName());
            preparedStatement.setInt(2, group.getId());
        }catch (SQLException e) {
            throw new ExceptionGroupDAO("error while updating group", e);
        }
    }

    public static void deleteGroup(Group group) {
        String sql = "DELETE FROM ProductGroup WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, group.getId());
        }catch (SQLException e) {
            throw new ExceptionGroupDAO("error while deleting group", e);
        }
    }
}
