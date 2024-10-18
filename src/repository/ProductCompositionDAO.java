package repository;

import model.ProductComposition;
import repository.exceptions.ExceptionProductCompositionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductCompositionDAO {
    /*private static void addProductComposition(ProductComposition productComposition) {
        String sql = "INSERT INTO ProductComposition (Parent_Product_ID, Composition_Product_ID, Quantity_Used) VALUES(?,?,?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productComposition.getParentProduct().getId());
            preparedStatement.setInt(2, productComposition.getCompositionProduct().getId());
            preparedStatement.setDouble(3, productComposition.getQuantityUsed());

            preparedStatement.executeUpdate(sql);
        }catch (SQLException e) {
            throw new ExceptionProductCompositionDAO("error while adding product composition", e);
        }
    }

    private static void updateProductComposition(ProductComposition productComposition) {
        StringBuilder sql = new StringBuilder("UPDATE ProductComposition SET ");
        boolean hasParentProductId = false;
        boolean hasCompositionProductId = false;
        boolean hasQuantityUsed = false;
        boolean isFirst = true;

        if (productComposition.getParentProduct().getId() != null) {
            sql.append("Parent_Product_ID = ?");
            hasParentProductId = true;
            isFirst = false;
        }

        if (productComposition.getCompositionProduct().getId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("Composition_Product_ID = ?");
            hasCompositionProductId = true;
            isFirst = false;
        }

        if (productComposition.getQuantityUsed() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("Quantity_Used = ?");
            hasQuantityUsed = true;
        }

        sql.append(" WHERE id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())){
            int parameterIndex = 1;

            if (hasParentProductId) {
                preparedStatement.setInt(parameterIndex++, productComposition.getParentProduct().getId());
            }

            if (hasCompositionProductId) {
                preparedStatement.setInt(parameterIndex++, productComposition.getCompositionProduct().getId());
            }

            if (hasQuantityUsed) {
                preparedStatement.setDouble(parameterIndex++, productComposition.getCompositionProduct().getQuantity());
            }

            preparedStatement.setInt(parameterIndex++, productComposition.getId());
            preparedStatement.executeUpdate(sql.toString());
        }catch (SQLException e) {
            throw new ExceptionProductCompositionDAO("error while updating product composition", e);
        }
    }*/
}
