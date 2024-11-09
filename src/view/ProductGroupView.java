package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.*;

public class ProductGroupView extends JFrame {
    ResolutionCapture resolutionCapture = new ResolutionCapture();
    private JPanel pnlMain;
    private JButton btnReturn;
    private JButton btnSave;
    private JTextField txtName;
    private JPanel pnlCenter;
    private JPanel pnlCenterOne;
    private JPanel pnlTop;
    private JLabel lblName;
    private JPanel pnlButton;

    public ProductGroupView() throws HeadlessException {
        setTitle("Grupo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(pnlMain);
        changeTheme();
        setVisible(true);
    }
    
    public void changeTheme(){
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlCenterOne);
        AppsStyle.stylePanel(pnlButton);
        AppsStyle.styleLabel(lblName);
        AppsStyle.styleTextField(txtName);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleButton(btnSave);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductGroupView::new);
    }
}
