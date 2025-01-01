package view;

import controller.ProductCompositionController;
import controller.exceptions.ProductCompositionControllerException;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.event.ActionEvent;

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

    public ProductCompositionView(boolean isUpdate, String productCompositionId, String parentProductId, String productChildId, String productChildProductQuantity) {
        setTitle("Subproduto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(pnlMain);
        changeTheme();

        //Ação do botão busca
        btnChildProduct.addActionListener(e ->{
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        new ProductTableView(txtChildProduct);
                    }catch (Exception e){
                        AppsStyle.showErrorDialog(e.getMessage(), "Erro de composição");
                    }
                }
            });
        });

        //Atalho para buscar (F1)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F1"), "search");
        pnlMain.getActionMap().put("search", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnChildProduct.doClick();
            }
        });

        if (isUpdate) {
            btnSave.setText("(F6)Atualizar");
            txtChildProduct.setText(productChildId);
            txtChildProductQuantity.setText(productChildProductQuantity);
        }

        //Ação do botão atualizar
        btnSave.addActionListener(e -> {
            if(parentProductId.equals(txtChildProduct.getText())) {
                AppsStyle.showErrorDialog("Os insumos não podem conter o próprio produto", "Erro de composição");
            }else {
                if (isUpdate) {
                    try {
                        if (ProductCompositionController.isDuplicated(parentProductId, txtChildProduct.getText())) {
                            throw new ProductCompositionControllerException("O subproduto não pode ser o mesmo que o produto principal");
                        }
                        ProductCompositionController.updateProductComposition(productCompositionId, parentProductId, txtChildProduct.getText(), txtChildProductQuantity.getText());
                        dispose();
                    } catch (Exception ex) {
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao editar");
                    }
                } else {
                    try {
                        ProductCompositionController.addProductComposition(parentProductId, txtChildProduct.getText(), txtChildProductQuantity.getText());
                        dispose();
                    } catch (Exception ex) {
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao salvar");
                    }
                }
            }
        });

        //Atalho para salvar / autalizar (F6)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F6"), "saveProductComposition");
        pnlMain.getActionMap().put("saveProductComposition", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSave.doClick();
            }
        });

        //Ação do botão retornar
        btnReturn.addActionListener(e -> {
            dispose();
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

        configureEnterNavigation(txtChildProduct, txtChildProductQuantity);
        configureEnterNavigation(txtChildProductQuantity, txtChildProduct);

        setVisible(true);
    }

    private void configureEnterNavigation(JTextField textField, JTextField nextField) {
        textField.addActionListener(e -> nextField.requestFocusInWindow());
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
}
