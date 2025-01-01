package repository;

import model.ProductionOrder;
import org.jetbrains.annotations.NotNull;
import repository.exceptions.ExceptionProductionOrderDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ProductionOrderDAO {
    public static void addProductionOrder(ProductionOrder productionOrder) {
        String sql = "INSERT INTO production_order (production_order_recipient_id, production_order_product_id, " +
                "production_order_quantity, production_order_status) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productionOrder.getProductionOrderRecipient().getRegistrationId());
            preparedStatement.setInt(2, productionOrder.getProductionOrderProduct().getProductId());
            preparedStatement.setDouble(3, productionOrder.getProductionOrderQuantity());
            preparedStatement.setString(4, productionOrder.getProductionOrderStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionProductionOrderDAO("Error while adding production order", e);
        }
    }

    public static void updateProductionOrder(ProductionOrder productionOrder) {
        StringBuilder sql = new StringBuilder("UPDATE production_order SET ");
        boolean hasProductionOrderRecipient = false;
        boolean hasProductionOrderProduct = false;
        boolean hasProductionOrderQuantity = false;
        boolean hasProductionOrderStatus = false;
        boolean isFirst = true;

        if (productionOrder.getProductionOrderRecipient().getRegistrationId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("production_order_recipient_id = ?");
            hasProductionOrderRecipient = true;
            isFirst = false;
        }

        if (productionOrder.getProductionOrderProduct().getProductId() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("production_order_product_id = ?");
            hasProductionOrderProduct = true;
            isFirst = false;
        }

        if (productionOrder.getProductionOrderQuantity() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("production_order_quantity = ?");
            hasProductionOrderQuantity = true;
            isFirst = false;
        }

        if (productionOrder.getProductionOrderStatus() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("production_order_status = ?");
            hasProductionOrderStatus = true;
        }

        sql.append(" WHERE production_order_id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            int parameterIndex = 1;

            if (hasProductionOrderRecipient) {
                preparedStatement.setInt(parameterIndex++, productionOrder.getProductionOrderRecipient().getRegistrationId());
            }

            if (hasProductionOrderProduct) {
                preparedStatement.setInt(parameterIndex++, productionOrder.getProductionOrderProduct().getProductId());
            }

            if (hasProductionOrderQuantity) {
                preparedStatement.setDouble(parameterIndex++, productionOrder.getProductionOrderQuantity());
            }

            if (hasProductionOrderStatus) {
                preparedStatement.setString(parameterIndex++, productionOrder.getProductionOrderStatus());
            }

            preparedStatement.setInt(parameterIndex++, productionOrder.getProductionOrderId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionProductionOrderDAO("Error while updating production order", e);
        }
    }

    public static void deleteProductionOrder(ProductionOrder productionOrder) {
        String sql = "DELETE FROM production_order WHERE production_order_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productionOrder.getProductionOrderId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionProductionOrderDAO("Error while deleting production order", e);
        }
    }

    public static Optional<ArrayList<ProductionOrder>> getByProductionOrderId(Integer productionOrderId, boolean includeDispatched) {
        String sql = """
        SELECT * 
        FROM production_order
        WHERE production_order_id = ? 
        AND (production_order_status NOT IN ('DESPACHADO', 'FINALIZADO') OR ? = TRUE);
        """;

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, productionOrderId);
            preparedStatement.setBoolean(2, includeDispatched);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProductions(resultSet);
            }
        }catch (SQLException e) {
            throw new ExceptionProductionOrderDAO("Error while getting production order", e);
        }
    }

    public static Optional<ArrayList<ProductionOrder>> getOrdersByRecipientName(String recipientName, boolean includeDispatched) {
        String sql = """
            SELECT po.*
            FROM production_order po
            JOIN registration r ON po.production_order_recipient_id = r.registration_id
            WHERE r.registration_name LIKE ?
            AND (po.production_order_status NOT IN ('DESPACHADO', 'FINALIZADO') OR ? = TRUE);
        """;

        recipientName = "%" + recipientName + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, recipientName);
            preparedStatement.setBoolean(2, includeDispatched);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getProductions(resultSet);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching production orders by recipient name", e);
        }
    }

    public static Optional<ArrayList<ProductionOrder>> getOrdersByProductName(String productName, boolean includeDispatched) {
        String sql = """
            SELECT po.*
            FROM production_order po
            JOIN product p ON po.production_order_product_id = p.product_id
            WHERE p.product_name LIKE ?
            AND (po.production_order_status NOT IN ('DESPACHADO', 'FINALIZADO') OR ? = TRUE);
            """;

        productName = "%" + productName + "%";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, productName);
            preparedStatement.setBoolean(2, includeDispatched);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return getProductions(resultSet);
            }
        }catch (SQLException e){
            throw new RuntimeException("Error fetching production orders by product name", e);
        }
    }

    public static Optional<ArrayList<ProductionOrder>> getOrdersByStatus(String status, boolean includeDispatched) {
        String sql = "SELECT * FROM production_order WHERE production_order_status LIKE ? " +
                "AND (production_order_status NOT IN ('DESPACHADO', 'FINALIZADO') OR ? = TRUE);";

        status = "%" + status + "%";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            preparedStatement.setBoolean(2, includeDispatched);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getProductions(resultSet);
            }
        } catch (SQLException e) {
            throw new ExceptionProductionOrderDAO("Error while getting production orders", e);
        }
    }

    public static Optional<ArrayList<ProductionOrder>> getAllProductionOrders(boolean includeDispatched) {
        String sql = "SELECT * FROM production_order " +
                "WHERE (production_order_status NOT IN ('DESPACHADO', 'FINALIZADO') OR ? = TRUE);";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, includeDispatched);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getProductions(resultSet);
            }
        } catch (SQLException e) {
            throw new ExceptionProductionOrderDAO("Error while retrieving all production orders", e);
        }
    }

    private static Optional<ArrayList<ProductionOrder>> getProductions(@NotNull ResultSet resultSet){
        ArrayList<ProductionOrder> productionOrders = new ArrayList<>();
        try {
            while (resultSet.next()){
                ProductionOrder productionOrder = new ProductionOrder();

                productionOrder.setProductionOrderId(resultSet.getInt("production_order_id"));
                try {
                    productionOrder.setProductionOrderRecipient(RegistrationDAO.getByRegistrationId(resultSet.getInt("production_order_recipient_id")).get().getFirst());
                }catch (SQLException e) {
                    throw new ExceptionProductionOrderDAO("Error while retrieving recipient",e);
                }
                try {
                    productionOrder.setProductionOrderProduct(ProductDAO.getProductById(resultSet.getInt("production_order_product_id")).get().getFirst());
                }catch (SQLException e) {
                    throw new ExceptionProductionOrderDAO("Error while retrieving product",e);
                }
                productionOrder.setProductionOrderQuantity(resultSet.getDouble("production_order_quantity"));
                productionOrder.setProductionOrderStatus(resultSet.getString("production_order_status"));

                productionOrders.add(productionOrder);
            }
            return productionOrders.isEmpty() ? Optional.empty() : Optional.of(productionOrders);
        }catch (SQLException e) {
            throw new ExceptionProductionOrderDAO("error while retrieving production orders",e);
        }
    }
}
