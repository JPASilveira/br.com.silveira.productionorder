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
            preparedStatement.setDouble(4, product.getQuantity());
            preparedStatement.setBoolean(5, product.getIsComposite());
            preparedStatement.setInt(6, product.getGroup().getId());
            preparedStatement.setInt(7, product.getUnit().getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionProductDAO("error while adding product", e);
        }
    }

    public static void updateProduct(Product product) {
        StringBuilder sql = new StringBuilder("UPDATE Product SET ");
        boolean hasReference = false;
        boolean hasName = false;
        boolean hasPrice = false;
        boolean hasQuantity = false;
        boolean hasComposite = false;
        boolean hasGroupId = false;
        boolean hasUnitId = false;
        boolean isFirst = true;

        if (product.getReference() != null) {
            sql.append("Reference = ?");
            hasReference = true;
            isFirst = false;
        }

        if (product.getName() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("Name = ?");
            hasName = true;
            isFirst = false;
        }

        if (product.getPrice() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("Price = ?");
            hasPrice = true;
            isFirst = false;
        }

        if (product.getQuantity() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("Quantity = ?");
            hasQuantity = true;
            isFirst = false;
        }

        if (product.getIsComposite() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("IsComposite = ?");
            hasComposite = true;
            isFirst = false;
        }

        if (product.getGroup().getId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("Group_Id = ?");
            hasGroupId = true;
            isFirst = false;
        }

        if (product.getUnit().getId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("Unit_Id = ?");
            hasUnitId = true;
        }

        sql.append(" WHERE Reference = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())){
            int parameterIndex = 1;

            if (hasReference) {
                preparedStatement.setString(parameterIndex++, product.getReference());
            }

            if (hasName) {
                preparedStatement.setString(parameterIndex++, product.getName());
            }

            if (hasPrice) {
                preparedStatement.setDouble(parameterIndex++, product.getPrice());
            }

            if (hasQuantity) {
                preparedStatement.setDouble(parameterIndex++, product.getQuantity());
            }

            if (hasComposite) {
                preparedStatement.setBoolean(parameterIndex++, product.getIsComposite());
            }

            if (hasGroupId) {
                preparedStatement.setInt(parameterIndex++, product.getGroup().getId());
            }

            if (hasUnitId) {
                preparedStatement.setInt(parameterIndex++, product.getUnit().getId());
            }

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ExceptionProductDAO("error while updating product", e);
        }
    }

    public static void deleteProduct(Product product) {
        String sql = "DELETE FROM Product WHERE Reference = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, product.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ExceptionProductDAO("error while deleting product", e);
        }
    }
}
