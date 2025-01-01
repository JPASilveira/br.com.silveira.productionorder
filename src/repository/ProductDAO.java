package repository;

import model.Product;
import model.ProductGroup;
import model.ProductUnit;
import org.jetbrains.annotations.NotNull;
import repository.exceptions.ExceptionProductDAO;
import repository.exceptions.ExceptionProductGroupDAO;
import repository.exceptions.ExceptionProductUnitDAO;
import repository.exceptions.ExceptionRegistrationDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ProductDAO {
    public static void addProduct(@NotNull Product product) {
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
        } catch (SQLException e) {
            throw new ExceptionProductDAO("error while adding product", e);
        }
    }

    public static void updateProduct(@NotNull Product product) {
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

        if (product.getProductGroup() != null && product.getProductGroup().getGroupId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("product_group_id = ?");
            hasProductGroupId = true;
            isFirst = false;
        }

        if (product.getProductUnit() != null && product.getProductUnit().getUnitId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("product_unit_id = ?");
            hasProductUnitId = true;
        }

        sql.append(" WHERE product_id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
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
        } catch (SQLException e) {
            throw new ExceptionProductDAO("error while updating product", e);
        }
    }

    public static void deleteProduct(@NotNull Product product) {
        String sql = "DELETE FROM product WHERE product_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, product.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionProductDAO("error while deleting product", e);
        }
    }

    public static @NotNull Optional<ArrayList<Product>> getProductById(Integer productId) {
        String sql = "SELECT * FROM product WHERE product_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, productId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProducts(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductDAO("error while retrieving product", e);
        }
    }

    public static @NotNull Optional<ArrayList<Product>> getProductByReference(String productReference){
        String sql = "SELECT * FROM product WHERE product_reference LIKE ?";
        productReference = "%" + productReference + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, productReference);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProducts(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductDAO("error while retrieving product", e);
        }
    }

    public static @NotNull Optional<ArrayList<Product>> getProductByName(String productName){
        String sql = "SELECT * FROM product WHERE product_name LIKE ?";
        productName = "%" + productName + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, productName);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProducts(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductDAO("error while retrieving product", e);
        }
    }

    public static @NotNull Optional<ArrayList<Product>> getProductByPrice(Double productPrice){
        String sql = "SELECT * FROM product WHERE product_price LIKE ?";
        String price = "%" + productPrice + "%";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, price);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProducts(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductDAO("error while retrieving product", e);
        }
    }

    public static @NotNull Optional<ArrayList<Product>> getAllProducts(){
        String sql = "SELECT * FROM product";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProducts(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductDAO("error while retrieving products", e);
        }
    }

    @NotNull
    public static Optional<ArrayList<Product>> getProducts(@NotNull ResultSet resultSet) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Product product = new Product();
                Optional<ArrayList<ProductGroup>> productGroup = ProductGroupDAO.getProductGroupById(resultSet.getInt("product_group_id"));
                Optional<ArrayList<ProductUnit>> productUnit = ProductUnitDAO.getProductUnitById(resultSet.getInt("product_unit_id"));

                product.setProductId(resultSet.getInt("product_id"));
                product.setProductReference(resultSet.getString("product_reference"));
                product.setProductName(resultSet.getString("product_name"));
                product.setProductPrice(resultSet.getDouble("product_price"));
                product.setProductQuantity(resultSet.getDouble("product_quantity"));
                product.setProductIsComposite(resultSet.getBoolean("product_is_composite"));

                if (productGroup.isPresent() && !productGroup.get().isEmpty()) {
                    product.setProductGroup(productGroup.get().getFirst());
                } else {
                    throw new ExceptionProductGroupDAO("not found group", null);
                }

                if (productUnit.isPresent() && !productUnit.get().isEmpty()) {
                    product.setProductUnit(productUnit.get().getFirst());
                } else {
                    throw new ExceptionProductUnitDAO("not found product unit", null);
                }

                products.add(product);
            }

            return products.isEmpty() ? Optional.empty() : Optional.of(products);

        } catch (SQLException e) {
            throw new ExceptionProductDAO("error while getting product", e);
        }
    }

    public static boolean existsById(String id) {
        String sql = "SELECT 1 FROM product WHERE product_id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,Integer.parseInt(id));
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
        } catch (Exception e) {
            throw new ExceptionRegistrationDAO("error while retrieving product", e);
        }
    }

    public static boolean isComposeById(String id) {
        String sql = "SELECT product_is_composite FROM product WHERE product_id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, Integer.parseInt(id));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("product_is_composite");
                } else {
                    // Caso não encontre o produto
                    return false; // Ou lance uma exceção, se preferir
                }
            }
        } catch (Exception e) {
            throw new ExceptionRegistrationDAO("Error while retrieving product", e);
        }
    }
}