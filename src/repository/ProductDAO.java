package repository;

import model.Product;
import repository.exceptions.ExceptionProductDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO {
    public static void addProduct(Product product) {
        String sql = "INSERT INTO product (product_reference, product_name, product_price, product_quantity, product_is_composite, product_group_id, product_unit_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getProductReference());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setDouble(3, product.getProductPrice());
            preparedStatement.setDouble(4, product.getProductQuantity());
            preparedStatement.setBoolean(5, product.getProductIsComposite());
            preparedStatement.setInt(6, product.getProductGroup().getGroupId());
            preparedStatement.setInt(7, product.getProductUnit().getUnitId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionProductDAO("error while adding product", e);
        }
    }

    public static void updateProduct(Product product) {
        StringBuilder sql = new StringBuilder("UPDATE product SET ");
        boolean hasProductReference = false;
        boolean hasProductName = false;
        boolean hasProductPrice = false;
        boolean hasProductQuantity = false;
        boolean hasProductComposite = false;
        boolean hasProductGroupId = false;
        boolean hasProductUnitId = false;
        boolean isFirst = true;

        if (product.getProductReference() != null) {
            sql.append("product_reference = ?");
            hasProductReference = true;
            isFirst = false;
        }

        if (product.getProductName() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("product_name = ?");
            hasProductName = true;
            isFirst = false;
        }

        if (product.getProductPrice() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("product_price = ?");
            hasProductPrice = true;
            isFirst = false;
        }

        if (product.getProductQuantity() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("product_quantity = ?");
            hasProductQuantity = true;
            isFirst = false;
        }

        if (product.getProductIsComposite() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("product_is_composite = ?");
            hasProductComposite = true;
            isFirst = false;
        }

        if (product.getProductGroup().getGroupId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("product_group_id = ?");
            hasProductGroupId = true;
            isFirst = false;
        }

        if (product.getProductUnit().getUnitId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("product_unit_id = ?");
            hasProductUnitId = true;
        }

        sql.append(" WHERE product_id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())){
            int parameterIndex = 1;

            if (hasProductReference) {
                preparedStatement.setString(parameterIndex++, product.getProductReference());
            }

            if (hasProductName) {
                preparedStatement.setString(parameterIndex++, product.getProductName());
            }

            if (hasProductPrice) {
                preparedStatement.setDouble(parameterIndex++, product.getProductPrice());
            }

            if (hasProductQuantity) {
                preparedStatement.setDouble(parameterIndex++, product.getProductQuantity());
            }

            if (hasProductComposite) {
                preparedStatement.setBoolean(parameterIndex++, product.getProductIsComposite());
            }

            if (hasProductGroupId) {
                preparedStatement.setInt(parameterIndex++, product.getProductGroup().getGroupId());
            }

            if (hasProductUnitId) {
                preparedStatement.setInt(parameterIndex++, product.getProductUnit().getUnitId());
            }

            preparedStatement.setInt(parameterIndex++, product.getProductId());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ExceptionProductDAO("error while updating product", e);
        }
    }

    public static void deleteProduct(Product product) {
        String sql = "DELETE FROM product WHERE product_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, product.getProductId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ExceptionProductDAO("error while deleting product", e);
        }
    }
}