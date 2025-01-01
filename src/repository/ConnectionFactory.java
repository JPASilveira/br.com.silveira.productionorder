package repository;

import repository.exceptions.ExceptionConnectionFactory;
import util.PathUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
private static final String URL = "jdbc:h2:file:"+ PathUtil.getCurrentDirectory() + File.separator + "database" + File.separator + "production_order";
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
        String createTableRegistrationAddress = """
        CREATE TABLE IF NOT EXISTS registration_address (
        address_id INT AUTO_INCREMENT PRIMARY KEY,
        address_cep VARCHAR(10) NOT NULL,
        address_street VARCHAR(100) NOT NULL,
        address_number VARCHAR(50) NOT NULL,
        address_neighborhood VARCHAR(100) NOT NULL,
        address_city VARCHAR(60) NOT NULL,
        address_state VARCHAR(20) NOT NULL
        );
        """;

        String createTableRegistration = """
        CREATE TABLE IF NOT EXISTS registration (
        registration_id INT AUTO_INCREMENT PRIMARY KEY,
        registration_type VARCHAR(50) NOT NULL,
        registration_name VARCHAR(100) NOT NULL,
        registration_fantasy_name VARCHAR(100) NOT NULL,
        registration_document VARCHAR(20) NOT NULL,
        registration_ie VARCHAR(20) NOT NULL,
        registration_contact_number VARCHAR(20) NOT NULL,
        registration_email VARCHAR(50) NOT NULL,
        registration_address_id INT,
        CONSTRAINT fk_registration_address FOREIGN KEY (registration_address_id) REFERENCES registration_address(address_id)
        );
        """;

        String createTableProductUnit = """
        CREATE TABLE IF NOT EXISTS product_unit (
        unit_id INT AUTO_INCREMENT PRIMARY KEY,
        unit_name VARCHAR(50) NOT NULL,
        unit_acronym VARCHAR(10) NOT NULL
        );
        """;

        String createTableProductGroup = """
        CREATE TABLE IF NOT EXISTS product_group (
        group_id INT AUTO_INCREMENT PRIMARY KEY,
        group_name VARCHAR(50) NOT NULL
        );
        """;

        String createTableProduct = """
        CREATE TABLE IF NOT EXISTS product (
        product_id INT AUTO_INCREMENT PRIMARY KEY,
        product_reference VARCHAR(50) NOT NULL,
        product_name VARCHAR(100) NOT NULL,
        product_price DECIMAL(10, 2) NOT NULL,
        product_quantity DECIMAL(10, 2) NOT NULL,
        product_is_composite BOOLEAN NOT NULL,
        product_group_id INT,
        product_unit_id INT,
        CONSTRAINT fk_group FOREIGN KEY (product_group_id) REFERENCES product_group(group_id),
        CONSTRAINT fk_unit FOREIGN KEY (product_unit_id) REFERENCES product_unit(unit_id)
        );
        """;

        String createTableProductComposition = """
        CREATE TABLE IF NOT EXISTS product_composition (
        product_composition_id INT AUTO_INCREMENT PRIMARY KEY,
        product_composition_parent_product_id INT NOT NULL,
        product_composition_child_product_id INT NOT NULL,
        product_composition_quantity_used DECIMAL(10, 2) NOT NULL,
        CONSTRAINT fk_parent_product FOREIGN KEY (product_composition_parent_product_id) REFERENCES product(product_id),
        CONSTRAINT fk_child_product FOREIGN KEY (product_composition_child_product_id) REFERENCES product(product_id)
        );
        """;

        String createTableProductionOrder = """
        CREATE TABLE IF NOT EXISTS production_order (
        production_order_id INT AUTO_INCREMENT PRIMARY KEY,
        production_order_recipient_id INT NOT NULL,
        production_order_product_id INT NOT NULL,
        production_order_quantity DECIMAL(10, 2) NOT NULL,
        production_order_status VARCHAR(50) NOT NULL,
        CONSTRAINT fk_recipient FOREIGN KEY (production_order_recipient_id) REFERENCES registration(registration_id),
        CONSTRAINT fk_product FOREIGN KEY (production_order_product_id) REFERENCES product(product_id)
        );
        """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableRegistrationAddress);
            statement.execute(createTableRegistration);
            statement.execute(createTableProductUnit);
            statement.execute(createTableProductGroup);
            statement.execute(createTableProduct);
            statement.execute(createTableProductComposition);
            statement.execute(createTableProductionOrder);
        } catch (SQLException e) {
            throw new ExceptionConnectionFactory("Could not create tables in database", e);
        }
    }
}
