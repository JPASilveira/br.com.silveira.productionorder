package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;

public class ProductCompositionView extends JFrame {
    ResolutionCapture resolutionCapture = new ResolutionCapture();

    private JPanel pnlMain;
    private JPanel pnlTop;
    private JLabel lblId;
    private JPanel pnlCenter;
    private JPanel pnlCenterOne;
    private JTextField txtChildProduct;
    private JButton btnChildProduct;
    private JTextField txtChildProductQuantity;
    private JLabel lblChildProduct;
    private JLabel lblChildProductQuantity;
    private JPanel pnlCenterTwo;
    private JPanel pnlButton;
    private JButton btnSave;
    private JButton btnReturn;

    public ProductCompositionView() {
        setTitle("Subproduto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        AppsStyle.stylePanel(pnlCenterTwo);
        AppsStyle.stylePanel(pnlButton);
        AppsStyle.styleLabelBold(lblId);
        AppsStyle.styleLabel(lblChildProduct);
        AppsStyle.styleLabel(lblChildProductQuantity);
        AppsStyle.styleTextField(txtChildProduct);
        AppsStyle.styleTextField(txtChildProductQuantity);
        AppsStyle.styleButton(btnChildProduct);
        AppsStyle.styleButton(btnSave);
        AppsStyle.styleButton(btnReturn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProductCompositionView();
            }
        });
    }
}
