package repository;

import model.Product;
import repository.exceptions.ExceptionProductDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO {
    public static void addProduct(Product product) {
        String sql = "INSERT INTO Product (Reference, Name, Price, Quantity, Is_Composite, Group_Id, Unit_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getReference());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setBoolean(4, product.getIsComposite());
            preparedStatement.setInt(5, product.getGroup().getId());
            preparedStatement.setInt(6, product.getUnit().getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionProductDAO("error while adding product", e);
        }
    }
}
