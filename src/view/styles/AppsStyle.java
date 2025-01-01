package view.styles;

import util.FontLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AppsStyle {
    public static Color backgroundColor = new Color(255, 255, 255);
    public static Color textColor = new Color(0, 0, 0);
    public static Color borderColor = new Color(198, 231, 255);
    public static Color btnColor = new Color(198, 231, 255);

    public static final Font regularFont = FontLoader.loadFontRegular();
    public static final Font regularSmallFont = FontLoader.loadFontRegularSmall();
    public static final Font boldFont = FontLoader.loadFontBold();

    public static void stylePanel(JPanel panel) {
        panel.setBackground(backgroundColor);
    }

    public static void styleScrollPanel(JScrollPane panel) {
        panel.setBackground(backgroundColor);
    }

    public static void styleLabel(JLabel label) {
        label.setBackground(backgroundColor);
        label.setForeground(textColor);
        label.setFont(regularFont);
    }

    public static void styleLabelBold(JLabel label) {
        label.setBackground(backgroundColor);
        label.setForeground(textColor);
        label.setFont(boldFont);
    }

    public static void styleTextPanel(JTextPane textPane) {
        textPane.setForeground(textColor);
        textPane.setFont(regularSmallFont);
    }

    public static void styleButton(JButton button) {
        button.setBackground(btnColor);
        button.setForeground(textColor);
        button.setFont(boldFont);
    }

    public static void styleTextField(JTextField textField) {
        textField.setBackground(btnColor);
        textField.setForeground(textColor);
        textField.setFont(regularFont);
    }

    public static void styleComboBox(JComboBox comboBox) {
        comboBox.setBackground(backgroundColor);
        comboBox.setForeground(textColor);
        comboBox.setFont(boldFont);
    }

    public static void styleRadioButton(JRadioButton radioButton) {
        radioButton.setBackground(backgroundColor);
        radioButton.setForeground(textColor);
        radioButton.setFont(boldFont);
    }

    public static void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        //Header
        header.setBackground(AppsStyle.borderColor);
        header.setForeground(AppsStyle.textColor);
        header.setFont(AppsStyle.boldFont);
        //Itens
        table.setBackground(AppsStyle.backgroundColor);
        table.setForeground(AppsStyle.textColor);
        table.setFont(AppsStyle.regularFont);
        table.setFillsViewportHeight(true);
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        InputMap inputMap = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke("F1"), "none");
        inputMap.put(KeyStroke.getKeyStroke("F2"), "none");
        inputMap.put(KeyStroke.getKeyStroke("F3"), "none");
        inputMap.put(KeyStroke.getKeyStroke("F4"), "none");
        inputMap.put(KeyStroke.getKeyStroke("F5"), "none");
        inputMap.put(KeyStroke.getKeyStroke("F6"), "none");
        inputMap.put(KeyStroke.getKeyStroke("ESC"), "none");
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "none");

    }

    public static void styleCheckBox(JCheckBox checkBox) {
        checkBox.setBackground(AppsStyle.backgroundColor);
        checkBox.setForeground(textColor);
        checkBox.setFont(regularFont);
    }

    public static void showErrorDialog(String message, String title) {
        JLabel label = new JLabel(message);
        label.setFont(boldFont);
        label.setForeground(textColor);
        label.setOpaque(true);

        JButton closeButton = new JButton("Fechar");
        styleButton(closeButton);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.getWindowAncestor(closeButton);
                if (window != null) {
                    window.dispose();
                }
            }
        });

        Object[] options = {closeButton};
        JOptionPane optionPane = new JOptionPane(label, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        JDialog dialog = optionPane.createDialog(title);

        dialog.setBackground(backgroundColor);
        //dialog.getContentPane().setBackground(backgroundColor);
        dialog.setVisible(true);
    }
}
