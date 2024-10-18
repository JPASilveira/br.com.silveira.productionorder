package repository;

import model.ProductGroup;
import repository.exceptions.ExceptionGroupDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductGroupDAO {
    public static void addGroup(ProductGroup productGroup) {
        String sql = "INSERT INTO product_group (group_name) VALUES (?)";

        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, productGroup.getGroupName());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionGroupDAO("error while adding productGroup", e);
        }
    }

    public static void updateGroup(ProductGroup productGroup) {
        String sql = "UPDATE product_group SET group_name = ? WHERE group_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, productGroup.getGroupName());
            preparedStatement.setInt(2, productGroup.getGroupId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionGroupDAO("error while updating productGroup", e);
        }
    }

    public static void deleteGroup(ProductGroup productGroup) {
        String sql = "DELETE FROM product_group WHERE group_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, productGroup.getGroupId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionGroupDAO("error while deleting productGroup", e);
        }
    }
}
