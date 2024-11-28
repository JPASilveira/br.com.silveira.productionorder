package view;

import controller.ProductUnitController;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.*;

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

    public ProductUnitView(boolean isUpdate, String productUnitId, String productUnitName, String productUnitAcronym) throws HeadlessException {
        setTitle("Unidade");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(pnlMain);
        changeTheme();

        if(isUpdate){
            btnSave.setText("Atualizar");
            lblId.setText("ID: " + productUnitId);
            txtName.setText(productUnitName);
            txtUnit.setText(productUnitAcronym);
        }

        btnSave.addActionListener(e -> {
            if(isUpdate){
                try {
                    ProductUnitController.updateProductUnit(productUnitId, txtName.getText(), txtUnit.getText());
                    this.dispose();
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao atualizar");
                }
            }else {
                try {
                    ProductUnitController.addProductUnit(txtName.getText(), txtUnit.getText());
                    this.dispose();
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao adicionar");
                }
            }
        });

        btnReturn.addActionListener(e -> {
            this.dispose();
        });

        setVisible(true);
    }

    public void changeTheme(){
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
    }
}
