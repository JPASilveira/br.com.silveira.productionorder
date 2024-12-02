package repository;

import model.ProductGroup;
import org.jetbrains.annotations.NotNull;
import repository.exceptions.ExceptionProductGroupDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ProductGroupDAO {
    public static void addGroup(@NotNull ProductGroup productGroup) {
        String sql = "INSERT INTO product_group (group_name) VALUES (?)";

        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, productGroup.getGroupName());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionProductGroupDAO("error while adding productGroup", e);
        }
    }

    public static void updateGroup(@NotNull ProductGroup productGroup) {
        String sql = "UPDATE product_group SET group_name = ? WHERE group_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, productGroup.getGroupName());
            preparedStatement.setInt(2, productGroup.getGroupId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionProductGroupDAO("error while updating productGroup", e);
        }
    }

    public static void deleteGroup(@NotNull ProductGroup productGroup) {
        String sql = "DELETE FROM product_group WHERE group_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, productGroup.getGroupId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionProductGroupDAO("error while deleting productGroup", e);
        }
    }

    public static @NotNull Optional<ArrayList<ProductGroup>> getProductGroupById(Integer productGroupId) {
        String sql = "SELECT * FROM product_group WHERE group_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productGroupId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getProductGroups(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductGroupDAO("error while getting product group", e);
        }
    }

    public static @NotNull Optional<ArrayList<ProductGroup>> getProductGroupByName(String productGroupName) {
        String sql = "SELECT * FROM product_group WHERE group_name LIKE ?";
        productGroupName = "%" + productGroupName + "%";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, productGroupName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getProductGroups(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductGroupDAO("error while getting product group", e);
        }
    }

    public static @NotNull Optional<ArrayList<ProductGroup>> getAllProductGroups() {
        String sql = "SELECT * FROM product_group";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getProductGroups(resultSet);
            }
        } catch (SQLException e) {
            throw new ExceptionProductGroupDAO("error while getting all product groups", e);
        }
    }

    @NotNull
    private static Optional<ArrayList<ProductGroup>> getProductGroups(@NotNull ResultSet resultSet) throws SQLException {
        ArrayList<ProductGroup> productGroups = new ArrayList<>();
        while (resultSet.next()) {
            ProductGroup productGroup = new ProductGroup();
            productGroup.setGroupId(resultSet.getInt("group_id"));
            productGroup.setGroupName(resultSet.getString("group_name"));
            productGroups.add(productGroup);
        }

        return productGroups.isEmpty() ? Optional.empty() : Optional.of(productGroups);
    }
}