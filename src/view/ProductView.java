package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductView extends JFrame {
    ResolutionCapture resolutionCapture = new ResolutionCapture();

    private JPanel pnlMain;
    private JPanel pnlTop;
    private JLabel lblId;
    private JButton btnSave;
    private JButton btnReturn;
    private JPanel pnlCenter;
    private JPanel pnlLow;
    private JPanel pnlCenterOne;
    private JPanel pnlCenterTwo;
    private JPanel pnlCenterThree;
    private JPanel pnlCenterFour;
    private JPanel pnlCenterFive;
    private JPanel pnlCenterSix;
    private JTextField txtReference;
    private JTextField txtName;
    private JTextField txtPrice;
    private JTextField txtQuantity;
    private JTextField txtGroup;
    private JTextField txtUnit;
    private JPanel pnlSeven;
    private JCheckBox cbIsCompose;
    private JButton btnCompose;
    private JLabel lblName;
    private JLabel lblPrice;
    private JLabel lblQuantity;
    private JLabel lblGroup;
    private JLabel lblUnit;
    private JLabel lblReference;
    private JPanel pnlSevenLeft;
    private JButton btnGroupSearch;
    private JButton btnUnitSearch;

    public ProductView() {
        setTitle("Produto");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        changeTheme();

        btnGroupSearch.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ProductGroupTableView(ProductView.this);
                }
            });
        });

        btnUnitSearch.addActionListener(e ->  {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ProductUnitTableView(ProductView.this);
                }
            });
        });
    }

    public void setTxtGroup(String txtGroup) {
        this.txtGroup.setText(txtGroup);
    }

    public void setTxtUnit(String txtUnit) {
        this.txtUnit.setText(txtUnit);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductView::new);
    }

    public void changeTheme(){
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlCenterOne);
        AppsStyle.stylePanel(pnlCenterTwo);
        AppsStyle.stylePanel(pnlCenterThree);
        AppsStyle.stylePanel(pnlCenterFour);
        AppsStyle.stylePanel(pnlCenterFive);
        AppsStyle.stylePanel(pnlCenterSix);
        AppsStyle.stylePanel(pnlSeven);
        AppsStyle.stylePanel(pnlSevenLeft);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.styleLabel(lblId);
        AppsStyle.styleLabel(lblReference);
        AppsStyle.styleTextField(txtReference);
        AppsStyle.styleLabel(lblName);
        AppsStyle.styleTextField(txtName);
        AppsStyle.styleLabel(lblPrice);
        AppsStyle.styleTextField(txtPrice);
        AppsStyle.styleLabel(lblQuantity);
        AppsStyle.styleTextField(txtQuantity);
        AppsStyle.styleLabel(lblGroup);
        AppsStyle.styleTextField(txtGroup);
        AppsStyle.styleButton(btnGroupSearch);
        AppsStyle.styleLabel(lblUnit);
        AppsStyle.styleTextField(txtUnit);
        AppsStyle.styleButton(btnUnitSearch);
        AppsStyle.styleCheckBox(cbIsCompose);
        AppsStyle.styleButton(btnCompose);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleButton(btnSave);
    }
}
