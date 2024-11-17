package view;

import controller.ProductGroupController;
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
    private JLabel lblId;

    public ProductGroupView(boolean isUpdate, String productGroupId, String productGroupName) throws HeadlessException {
        setTitle("Grupo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(resolutionCapture.getWidth() / 2, resolutionCapture.getHeight() / 2);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(pnlMain);
        changeTheme();

        if (isUpdate) {
            btnSave.setText("Atualizar");
            setTxtName(productGroupName);
            setLblId(productGroupId);
        }

        btnSave.addActionListener(e -> {
            if (isUpdate){
                try {
                    ProductGroupController.editProductGroup(productGroupId, txtName.getText());
                    txtName.setText("");
                    this.dispose();
                } catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao atualizar!");
                }
            }else{
                try {
                    ProductGroupController.addProductGroup(txtName.getText());
                    txtName.setText("");
                    this.dispose();
                } catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao adicionar!");
                }
            }
        });

        btnReturn.addActionListener(e -> {
            this.dispose();
        });

        setVisible(true);
    }

    public void setTxtName(String name) {
        txtName.setText(name);
    }

    public void setLblId(String id) {
        lblId.setText("ID: " + id);
    }

    public void changeTheme() {
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
}
