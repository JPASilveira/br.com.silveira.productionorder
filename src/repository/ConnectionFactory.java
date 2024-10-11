package repository;

import repository.exceptions.ExceptionConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
    private static final String URL = "jdbc:h2:file:./src/database/productionOrder";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ExceptionConnectionFactory("Error creating connection", e);
        }
    }

    public static void createTables(Connection connection) {
        String createTableAddress = """
        CREATE TABLE IF NOT EXISTS RegistrationAddress (
        id INT AUTO_INCREMENT PRIMARY KEY,
        street VARCHAR(255) NOT NULL,
        number VARCHAR(10) NOT NULL,
        neighborhood VARCHAR(100) NOT NULL,
        city VARCHAR(100) NOT NULL,
        state VARCHAR(2) NOT NULL
        );
        """;

        String createTableRegistration = """
        CREATE TABLE IF NOT EXISTS Registration (
        id INT AUTO_INCREMENT PRIMARY KEY,
        registrationType VARCHAR(50) NOT NULL,
        name VARCHAR(255) NOT NULL,
        document VARCHAR(100) NOT NULL,
        address_id INT,
        CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES RegistrationAddress(id)
        );
        """;

        String createTableUnit = """
        CREATE TABLE IF NOT EXISTS ProductUnit (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        unit VARCHAR(50) NOT NULL
        );
        """;

        String createTableGroup = """
        CREATE TABLE IF NOT EXISTS ProductGroup (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100) NOT NULL
        );
        """;

        String createTableProduct = """
        CREATE TABLE IF NOT EXISTS Product (
        id INT AUTO_INCREMENT PRIMARY KEY,
        reference VARCHAR(50) NOT NULL,
        name VARCHAR(255) NOT NULL,
        price DECIMAL(10, 2) NOT NULL,
        quantity DECIMAL(10, 2) NOT NULL,
        is_composite BOOLEAN NOT NULL,
        group_id INT,
        unit_id INT,
        CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES ProductGroup(id),
        CONSTRAINT fk_unit FOREIGN KEY (unit_id) REFERENCES ProductUnit(id)
        );
        """;

        String createTableComposition = """
        CREATE TABLE IF NOT EXISTS ProductComposition (
        id INT AUTO_INCREMENT PRIMARY KEY,
        parent_product_id INT NOT NULL,
        composition_product_id INT NOT NULL,
        quantity_used DECIMAL(10, 2) NOT NULL,
        CONSTRAINT fk_parent_product FOREIGN KEY (parent_product_id) REFERENCES Product(id),
        CONSTRAINT fk_composition_product FOREIGN KEY (composition_product_id) REFERENCES Product(id)
        );
        """;

        String createTableProductionOrder = """
        CREATE TABLE IF NOT EXISTS ProductionOrder (
        id INT AUTO_INCREMENT PRIMARY KEY,
        recipient_id INT NOT NULL,
        product_id INT NOT NULL,
        quantity DECIMAL(10, 2) NOT NULL,
        status VARCHAR(50) NOT NULL,
        orderCost DECIMAL(10, 2) NOT NULL,
        CONSTRAINT fk_recipient FOREIGN KEY (recipient_id) REFERENCES Registration(id),
        CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES Product(id)
        );
        """;

        String createTableProductionOrderComposition = """
        CREATE TABLE IF NOT EXISTS ProductionOrderComposition (
        production_order_id INT NOT NULL,
        composition_id INT NOT NULL,
        quantity_used DECIMAL(10, 2) NOT NULL,
        CONSTRAINT fk_production_order FOREIGN KEY (production_order_id) REFERENCES ProductionOrder(id),
        CONSTRAINT fk_composition FOREIGN KEY (composition_id) REFERENCES ProductComposition(id),
        PRIMARY KEY (production_order_id, composition_id)
        );
        """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableAddress);
            statement.execute(createTableRegistration);
            statement.execute(createTableUnit);
            statement.execute(createTableGroup);
            statement.execute(createTableProduct);
            statement.execute(createTableComposition);
            statement.execute(createTableProductionOrder);
            statement.execute(createTableProductionOrderComposition);
        } catch (SQLException e) {
            throw new ExceptionConnectionFactory("Could not create tables in database", e);
        }
    }
}
