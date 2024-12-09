package application;

import repository.ConnectionFactory;
import view.MainWindowView;

import javax.swing.*;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        ConnectionFactory.createTables(ConnectionFactory.getConnection());
        SwingUtilities.invokeLater(MainWindowView::new);
    }
}
