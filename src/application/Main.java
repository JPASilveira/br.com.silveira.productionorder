package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.ConnectionFactory;
import repository.exceptions.ExceptionConnectionFactory;
import util.PathUtil;
import view.MainWindowView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        File logsDir = new File(PathUtil.getCurrentDirectory() + File.separator + "logs");
        if (!logsDir.exists()) {
            logsDir.mkdir();
        }

        try (Connection connection = ConnectionFactory.getConnection()) {
            ConnectionFactory.createTables(connection);
        } catch (ExceptionConnectionFactory | SQLException e) {
            logger.error("Database error: {}", e.getMessage(), e);
        }

        try {
            SwingUtilities.invokeLater(MainWindowView::new);

        } catch (Exception e) {
            logger.error("Execution error: {}", e.getMessage(), e);
        }
    }
}
