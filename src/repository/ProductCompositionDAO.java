package repository;

import model.ProductComposition;
import repository.exceptions.ExceptionProductCompositionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductCompositionDAO {
    private static void addProductComposition(ProductComposition productComposition) {
        String sql = "INSERT INTO product_composition (product_composition_parent_product_id, product_composition_child_product_id, product_composition_quantity_used) VALUES(?,?,?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productComposition.getProductCompositionParentProduct().getProductId());
            preparedStatement.setInt(2, productComposition.getProductCompositionChildProduct().getProductId());
            preparedStatement.setDouble(3, productComposition.getProductCompositionQuantityUsed());

            preparedStatement.executeUpdate(sql);
        }catch (SQLException e) {
            throw new ExceptionProductCompositionDAO("error while adding product composition", e);
        }
    }

    private static void updateProductComposition(ProductComposition productComposition) {
        StringBuilder sql = new StringBuilder("UPDATE product_composition SET ");
        boolean hasProductCompositionParentProductId = false;
        boolean hasProductCompositionChildProductId = false;
        boolean hasProductCompositionQuantityUsed = false;
        boolean isFirst = true;

        if (productComposition.getProductCompositionParentProduct().getProductId() != null) {
            sql.append("product_composition_parent_product_id = ?");
            hasProductCompositionParentProductId = true;
            isFirst = false;
        }

        if (productComposition.getProductCompositionChildProduct().getProductId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("product_composition_child_product_id = ?");
            hasProductCompositionChildProductId = true;
            isFirst = false;
        }

        if (productComposition.getProductCompositionQuantityUsed() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("product_composition_quantity_used = ?");
            hasProductCompositionQuantityUsed = true;
        }

        sql.append(" WHERE product_composition_id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())){
            int parameterIndex = 1;

            if (hasProductCompositionParentProductId) {
                preparedStatement.setInt(parameterIndex++, productComposition.getProductCompositionParentProduct().getProductId());
            }

            if (hasProductCompositionChildProductId) {
                preparedStatement.setInt(parameterIndex++, productComposition.getProductCompositionChildProduct().getProductId());
            }

            if (hasProductCompositionQuantityUsed) {
                preparedStatement.setDouble(parameterIndex++, productComposition.getProductCompositionQuantityUsed());
            }

            preparedStatement.setInt(parameterIndex++, productComposition.getProductCompositionId());
            preparedStatement.executeUpdate(sql.toString());
        }catch (SQLException e) {
            throw new ExceptionProductCompositionDAO("error while updating product composition", e);
        }
    }

    public static void deleteProductComposition(ProductComposition productComposition) {
        String sql = "DELETE FROM product_composition WHERE product_composition_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productComposition.getProductCompositionId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionProductCompositionDAO("error while deleting product composition", e);
        }
    }
}
