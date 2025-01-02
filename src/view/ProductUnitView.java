package view;

import controller.ProductUnitController;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
        setSize(resolutionCapture.getMinWidth(), resolutionCapture.getMinHeight());
        setLocationRelativeTo(null);
        setContentPane(pnlMain);
        changeTheme();

        if(isUpdate){
            btnSave.setText("(F6) Atualizar");
            lblId.setText("ID: " + productUnitId);
            txtName.setText(productUnitName);
            txtUnit.setText(productUnitAcronym);
        }

        //Ação botão salvar
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

        //Atalho para salvar (F6)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F6"), "save");
        pnlMain.getActionMap().put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSave.doClick();
            }
        });

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

        configureEnterNavigation(txtName);
        configureEnterNavigation(txtUnit);

        pnlMain.requestFocusInWindow();
        setVisible(true);
    }

    private static void configureEnterNavigation(JTextField textField) {
        textField.addActionListener((ActionEvent e) -> textField.transferFocus());
    }

    public void changeTheme(){
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlCenterOne);
        AppsStyle.stylePanel(pnlCenterTwo);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.styleLabelBold(lblId);
        AppsStyle.styleLabel(lblName);
        AppsStyle.styleTextField(txtName);
        AppsStyle.styleLabel(lblUnit);
        AppsStyle.styleTextField(txtUnit);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleButton(btnSave);
    }
}
