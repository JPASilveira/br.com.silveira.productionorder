package view;

import controller.ProductGroupController;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getMinWidth(), resolutionCapture.getMinHeight());
        setLocationRelativeTo(null);
        setContentPane(pnlMain);
        changeTheme();

        if (isUpdate) {
            btnSave.setText("Atualizar");
            setTxtName(productGroupName);
            setLblId(productGroupId);
        }

        //Ação do botão Salvar
        btnSave.addActionListener(e -> {
            if (isUpdate){
                try {
                    ProductGroupController.updateProductGroup(productGroupId, txtName.getText());
                    this.dispose();
                } catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                }
            }else{
                try {
                    ProductGroupController.addProductGroup(txtName.getText());
                    txtName.setText("");
                    this.dispose();
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                }
            }
        });

        //Atalho para salvar (F6)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F6"), "save");
        pnlMain.getActionMap().put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSave.doClick();
            }
        });

        //Ação do botão de retorno
        btnReturn.addActionListener(e -> {
            this.dispose();
        });

        // Atalho para Retornar (Esc)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "return");
        pnlMain.getActionMap().put("return", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnReturn.doClick();
            }
        });

        txtName.requestFocus();
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
        AppsStyle.styleLabelBold(lblId);
        AppsStyle.styleTextField(txtName);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleButton(btnSave);
    }
}
