package view.styles;

import util.FontLoader;

import javax.swing.*;
import java.awt.* ;


public class AppsStyle {
    public static final Color backgroundColor = new Color(33,33,33);
    public static final Color textColor = new Color(255,255,255);
    public static final Color borderColor = new Color(89, 89, 89);

    public static JLabel defaultTitle(String text){
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(AppsStyle.boldFont);
        return label;
    }

    public static JLabel defaultLabel(String text){
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(AppsStyle.regularFont);
        return label;
    }

    public static JTextField defaultTextField(){
        JTextField textField = new JTextField("                                                 ");
        textField.setForeground(Color.WHITE);
        textField.setBackground(AppsStyle.backgroundColor);
        textField.setFont(AppsStyle.regularFont);
        return textField;
    }

    public static JButton defaultButton(String text){
        JButton button = new JButton(text);
        button.setBackground(AppsStyle.borderColor);
        button.setForeground(Color.white);
        button.setFont(AppsStyle.regularFont);
        return button;
    }

    public static JPanel defaultSaveDeleteReturn(){
        JPanel panel = new JPanel();
        panel.setBackground(AppsStyle.backgroundColor);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnSave = defaultButton("Salvar");
        JButton btnDelete = defaultButton("Excluir");
        JButton btnReturn = defaultButton("Voltar");

        panel.add(btnDelete);
        panel.add(btnReturn);
        panel.add(btnSave);

        return panel;
    }

    public static final Font regularFont = FontLoader.loadFontRegular();
    public static final Font boldFont = FontLoader.loadFontBold();
}
