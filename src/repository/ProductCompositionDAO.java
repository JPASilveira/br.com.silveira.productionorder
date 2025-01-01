package repository;

import model.Product;
import model.ProductComposition;
import org.jetbrains.annotations.NotNull;
import repository.exceptions.ExceptionProductCompositionDAO;
import repository.exceptions.ExceptionProductDAO;
import repository.exceptions.ExceptionProductGroupDAO;
import repository.exceptions.ExceptionProductUnitDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ProductCompositionDAO {
    public static void addProductComposition(ProductComposition productComposition) {
        String sql = "INSERT INTO product_composition (product_composition_parent_product_id, product_composition_child_product_id, product_composition_quantity_used) VALUES(?,?,?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productComposition.getProductCompositionParentProduct().getProductId());
            preparedStatement.setInt(2, productComposition.getProductCompositionChildProduct().getProductId());
            preparedStatement.setDouble(3, productComposition.getProductCompositionQuantity());

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionProductCompositionDAO("error while adding product composition", e);
        }
    }

    public static void updateProductComposition(ProductComposition productComposition) {
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

        if (productComposition.getProductCompositionQuantity() != null) {
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
                preparedStatement.setDouble(parameterIndex++, productComposition.getProductCompositionQuantity());
            }

            preparedStatement.setInt(parameterIndex++, productComposition.getProductCompositionId());
            preparedStatement.executeUpdate();
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

    public static @NotNull Optional<ArrayList<ProductComposition>> getProductCompositionByParentProductId(Integer productCompositionParentProductId) {
        String sql = "SELECT * FROM product_composition WHERE product_composition_parent_product_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,productCompositionParentProductId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProductsComposition(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductCompositionDAO("error while getting product composition", e);
        }
    }

    public static @NotNull Optional<ArrayList<ProductComposition>> getProductCompositionById(Integer productCompositionId) {
        String sql = "SELECT * FROM product_composition WHERE product_composition_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, productCompositionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProductsComposition(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductCompositionDAO("error while getting product composition", e);
        }
    }

    public static @NotNull Optional<ArrayList<ProductComposition>> getAllProductsComposition() {
        String sql = "SELECT * FROM product_composition";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProductsComposition(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductDAO("error while retrieving products composition", e);
        }
    }

    @NotNull
    public static Optional<ArrayList<ProductComposition>> getProductsComposition(@NotNull ResultSet resultSet) {
        ArrayList<ProductComposition> productsComposition = new ArrayList<>();

        try {
            while (resultSet.next()) {
                ProductComposition productComposition = new ProductComposition();
                Optional<ArrayList<Product>> parentProduct = ProductDAO.getProductById(resultSet.getInt("PRODUCT_COMPOSITION_PARENT_PRODUCT_ID"));
                Optional<ArrayList<Product>> childProduct = ProductDAO.getProductById(resultSet.getInt("PRODUCT_COMPOSITION_CHILD_PRODUCT_ID"));

                productComposition.setProductCompositionId(resultSet.getInt("PRODUCT_COMPOSITION_ID"));
                productComposition.setProductCompositionQuantityUsed(resultSet.getDouble("PRODUCT_COMPOSITION_QUANTITY_USED"));

                if (parentProduct.isPresent() && !parentProduct.get().isEmpty()) {
                    productComposition.setProductCompositionParentProduct(parentProduct.get().getFirst());
                } else {
                    throw new ExceptionProductGroupDAO("not found parent product", null);
                }

                if (childProduct.isPresent() && !childProduct.get().isEmpty()) {
                    productComposition.setProductCompositionChildProduct(childProduct.get().getFirst());
                } else {
                    throw new ExceptionProductUnitDAO("not found product unit", null);
                }

                productsComposition.add(productComposition);
            }

            return productsComposition.isEmpty() ? Optional.empty() : Optional.of(productsComposition);

        } catch (SQLException e) {
            throw new ExceptionProductDAO("error while getting product composition", e);
        }
    }


}
