package view.styles;

import util.FontLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.* ;


public class AppsStyle {
    public static final Color whiteBackgroundColor = new Color(255, 255, 255);
    public static final Color whiteTextColor = new Color(0, 0, 0);
    public static final Color whiteBorderColor = new Color(163,184,204);
    public static final Color whiteBtnColor = new Color(163,184,204);

    public static final Color blackBackgroundColor = new Color(33,33,33);
    public static final Color blackTextColor = new Color(255,255,255);
    public static final Color blackBorderColor = new Color(89, 89, 89);
    public static final Color blackBtnColor = new Color(44, 44, 44);

    public static Color backgroundColor = whiteBackgroundColor;
    public static Color textColor = whiteTextColor;
    public static Color borderColor = whiteBorderColor;
    public static Color btnColor = whiteBtnColor;

    public static final Font regularFont = FontLoader.loadFontRegular();
    public static final Font boldFont = FontLoader.loadFontBold();

    public static void stylePanel(JPanel panel) {
        panel.setBackground(backgroundColor);
    }

    public static void styleScrollPanel(JScrollPane panel) {
        panel.setBackground(backgroundColor);
    }

    public static void changeTheme() {
        if (backgroundColor.equals(whiteBackgroundColor)) {
            backgroundColor = blackBackgroundColor;
            textColor = blackTextColor;
            borderColor = blackBorderColor;
            btnColor = blackBtnColor;
        } else if (backgroundColor.equals(blackBackgroundColor)) {
            backgroundColor = whiteBackgroundColor;
            textColor = whiteTextColor;
            borderColor = whiteBorderColor;
            btnColor = whiteBtnColor;
        }
    }


    public static void styleLabel(JLabel label) {
        label.setBackground(backgroundColor);
        label.setForeground(textColor);
        label.setFont(regularFont);
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
        comboBox.setFont(regularFont);
    }

    public static void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        //Header
        header.setBackground(AppsStyle.btnColor);
        header.setForeground(AppsStyle.textColor);
        header.setFont(AppsStyle.boldFont);
        //Itens
        table.setBackground(AppsStyle.backgroundColor);
        table.setForeground(AppsStyle.textColor);
        table.setFont(AppsStyle.regularFont);
        table.setFillsViewportHeight(true);
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

    }

    public static void styleCheckBox(JCheckBox checkBox){
        checkBox.setBackground(AppsStyle.backgroundColor);
        checkBox.setForeground(textColor);
        checkBox.setFont(regularFont);
    }
}
