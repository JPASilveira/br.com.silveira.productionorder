package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;

public class ProductUnitView extends JFrame{
    ResolutionCapture resolutionCapture = new ResolutionCapture();

    private JPanel pnlMain;
    private JPanel pnlTop;
    private JLabel lblId;
    private JPanel pnlLow;
    private JPanel pnlCenter;
    private JButton btnReturn;
    private JButton btnSave;
    private JTextField txtName;
    private JTextField txtUnit;
    private JLabel lblName;
    private JPanel pnlCenterTwo;
    private JPanel pnlCenterOne;
    private JLabel lblUnit;

    public ProductUnitView(){
        setTitle("Unidade de Produto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(pnlMain);

        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlCenterOne);
        AppsStyle.stylePanel(pnlCenterTwo);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.styleLabel(lblId);
        AppsStyle.styleLabel(lblName);
        AppsStyle.styleTextField(txtName);
        AppsStyle.styleLabel(lblUnit);
        AppsStyle.styleTextField(txtUnit);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleButton(btnSave);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductUnitView::new);
    }
}
