package view;

import controller.ProductionOrderController;
import repository.ProductDAO;
import repository.RegistrationDAO;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ProductionView extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlTop;
    private JPanel pnlCenter;
    private JPanel pnlBottom;
    private JLabel lblId;
    private JPanel pnlCenterOne;
    private JPanel pnlCenterThree;
    private JPanel pnlCenterTwo;
    private JPanel pnlCenterFour;
    private JTextField txtRequester;
    private JButton btnRequester;
    private JTextField txtProduct;
    private JButton btnProduct;
    private JTextField txtQuantity;
    private JComboBox cmbStatus;
    private JButton btnSave;
    private JButton btnReturn;
    private JLabel lblRequester;
    private JLabel lblProduct;
    private JLabel lblQuantity;
    private JLabel lblStatus;

    public ProductionView(boolean isUpdate, String productionOrderId, String requesterId, String productId, String quantity) {
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setTitle("Ordem de produção");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setResizable(false);
        changeTheme();

        txtRequester.setText(requesterId);
        txtProduct.setText(productId);
        txtQuantity.setText(quantity);

        if (!isUpdate) {
            cmbStatus.setEnabled(false);
            configureEnterNavigation(txtRequester, txtProduct);
            configureEnterNavigation(txtProduct, txtQuantity);
            configureEnterNavigation(txtQuantity, txtRequester);
        }else {
            disableComponentsForEditing();
            btnSave.setText("(F6) Atualizar");
            cmbStatus.requestFocus();
        }

        //Buscar requisitante
        btnRequester.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        RegistrationTableView registrationTableView = new RegistrationTableView(txtRequester);
                        registrationTableView.requestFocusOnTable();
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de ordem de produção");
                    }
                }
            });
        });

        //Atalho para buscar requisitante (F1)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F1"), "searchRequester");
        pnlMain.getActionMap().put("searchRequester", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRequester.doClick();
            }
        });

        //Ação do botão de buscar produto
        btnProduct.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        ProductTableView productTableView = new ProductTableView(txtProduct);
                        productTableView.requestFocusOnTable();
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de ordem de produção");
                    }
                }
            });
        });

        //Atalho para buscar produto (F2)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F2"), "searchProduct");
        pnlMain.getActionMap().put("searchProduct", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnProduct.doClick();
            }
        });

        //Ação do botão de salvar
        btnSave.addActionListener(e -> {
            if (!isUpdate) {
                if (validateFields()) {
                    disableComponents();

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ProductionConfirmView confirmView = new ProductionConfirmView(ProductionView.this);
                                confirmView.addWindowListener(new java.awt.event.WindowAdapter() {
                                    @Override
                                    public void windowClosed(java.awt.event.WindowEvent e) {
                                        enableComponents();
                                    }
                                });
                            } catch (Exception ex) {
                                AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao carregar os dados");
                            }
                        }
                    });
                }
            }else {
                try {
                    ProductionOrderController.updateProductionOrder(productionOrderId,cmbStatus.getSelectedItem().toString());
                    dispose();
                } catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao atualizar");
                }
            }
        });

        //Atalho para buscar salvar (F6)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F6"), "save");
        pnlMain.getActionMap().put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSave.doClick();
            }
        });

        //Ação do botão retornar
        btnReturn.addActionListener(e -> {
            dispose();
        });

        //Atalho para retornar (ESC)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "return");
        pnlMain.getActionMap().put("return", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnReturn.doClick();
            }
        });

        setVisible(true);
    }



    public ProductionView(boolean preview, boolean isUpdate, String productionOrderId, String requesterId, String productId, String quantity) {
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtRequester.setText(requesterId);
        txtProduct.setText(productId);
        txtQuantity.setText(quantity);

        btnSave.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        ProductionConfirmView confirmView = new ProductionConfirmView(ProductionView.this);
                        confirmView.disableSaveButton();

                        confirmView.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent e) {
                                dispose();
                            }
                        });
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de ordem de produção");
                    }
                }
            });
        });

        setVisible(false);
        btnSave.doClick();
    }

    public void changeTheme() {
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlCenterOne);
        AppsStyle.stylePanel(pnlCenterTwo);
        AppsStyle.stylePanel(pnlCenterThree);
        AppsStyle.stylePanel(pnlCenterFour);
        AppsStyle.stylePanel(pnlBottom);
        AppsStyle.styleLabelBold(lblId);
        AppsStyle.styleLabel(lblProduct);
        AppsStyle.styleLabel(lblQuantity);
        AppsStyle.styleLabel(lblStatus);
        AppsStyle.styleLabel(lblRequester);
        AppsStyle.styleTextField(txtRequester);
        AppsStyle.styleTextField(txtProduct);
        AppsStyle.styleTextField(txtQuantity);
        AppsStyle.styleComboBox(cmbStatus);
        AppsStyle.styleButton(btnRequester);
        AppsStyle.styleButton(btnProduct);
        AppsStyle.styleButton(btnSave);
        AppsStyle.styleButton(btnReturn);
    }

    public void disableComponents() {
        txtRequester.setEnabled(false);
        btnRequester.setEnabled(false);
        txtProduct.setEnabled(false);
        btnProduct.setEnabled(false);
        txtQuantity.setEnabled(false);
        btnSave.setEnabled(false);
        btnReturn.setEnabled(false);
    }

    private void enableComponents() {
        txtRequester.setEnabled(true);
        btnRequester.setEnabled(true);
        txtProduct.setEnabled(true);
        btnProduct.setEnabled(true);
        txtQuantity.setEnabled(true);
        btnSave.setEnabled(true);
        btnReturn.setEnabled(true);
    }

    private void disableComponentsForEditing() {
        txtRequester.setEnabled(false);
        btnRequester.setEnabled(false);
        txtProduct.setEnabled(false);
        btnProduct.setEnabled(false);
        txtQuantity.setEnabled(false);
    }

    public void disableComponentesPreview() {
        txtRequester.setEnabled(false);
        btnRequester.setEnabled(false);
        txtProduct.setEnabled(false);
        btnProduct.setEnabled(false);
        txtQuantity.setEnabled(false);
        cmbStatus.setEnabled(false);
    }

    private boolean validateFields() {
        String requesterText = txtRequester.getText().trim();
        if (requesterText.isEmpty()) {
            AppsStyle.showErrorDialog("O campo 'ID Solicitante' deve ser preenchido.", "Erro de solicitante");
            return false;
        }
        try {
            int requesterId = Integer.parseInt(requesterText);
            if (requesterId <= 0) {
                AppsStyle.showErrorDialog("O campo 'ID Solicitante' deve conter um número maior que zero.", "Erro de solicitante");
                return false;
            }
        } catch (NumberFormatException e) {
            AppsStyle.showErrorDialog("O campo 'ID Solicitante' deve conter apenas números inteiros.", "Erro de solicitante");
            return false;
        }
        try {
            if(!RegistrationDAO.existsById(requesterText)){
                AppsStyle.showErrorDialog("O campo 'ID Solicitante' deve conter um cadastro existente.", "Erro de solicitante");
                return false;
            }
        } catch (Exception e) {
            AppsStyle.showErrorDialog("O campo 'ID Solicitante' deve conter um cadastro existente.", "Erro de solicitante");
            return false;
        }

        String productText = txtProduct.getText().trim();
        if (productText.isEmpty()) {
            AppsStyle.showErrorDialog("O campo 'ID Produto' deve ser preenchido.", "Erro de produto");
            return false;
        }
        try {
            int productId = Integer.parseInt(productText);
            if (productId <= 0) {
                AppsStyle.showErrorDialog("O campo 'ID Produto' deve conter um número maior que zero.", "Erro de produto");
                return false;
            }
        } catch (NumberFormatException e) {
            AppsStyle.showErrorDialog("O campo 'ID Produto' deve conter apenas números inteiros.", "Erro de produto");
            return false;
        }
        try {
            if(!ProductDAO.existsById(productText)){
                AppsStyle.showErrorDialog("O campo 'ID Produto' deve conter um cadastro existente.", "Erro de produto");
                return false;
            }
        }catch (Exception e) {
            AppsStyle.showErrorDialog("O campo 'ID Produto' deve conter um cadastro existente.", "Erro de produto");
            return false;
        }
        try {
            if(!ProductDAO.isComposeById(productText)){
                AppsStyle.showErrorDialog("O campo 'ID Produto' deve conter um cadastro composto.", "Erro de produto");
                return false;
            }
        }catch (Exception e) {
            AppsStyle.showErrorDialog("O campo 'ID Produto' deve conter um cadastro composto.", "Erro de produto");
            return false;
        }

        String quantityText = txtQuantity.getText().trim();
        if (quantityText.isEmpty()) {
            AppsStyle.showErrorDialog("O campo 'Quantidade' deve ser preenchido.", "Erro de quantidade");
            return false;
        }
        try {
            double quantity = Double.parseDouble(quantityText);
            if (quantity <= 0) {
                AppsStyle.showErrorDialog("O campo 'Quantidade' deve conter um número maior que zero.", "Erro de quantidade");
                return false;
            }
        } catch (NumberFormatException e) {
            AppsStyle.showErrorDialog("O campo 'Quantidade' deve conter apenas números inteiros.", "Erro de quantidade");
            return false;
        }

        return true;
    }
    public String getRequesterId() {
        return txtRequester.getText().trim();
    }

    public String getProductId() {
        return txtProduct.getText().trim();
    }

    public String getQuantity() {
        return txtQuantity.getText().trim();
    }

    public String getStatus() {
        return cmbStatus.getSelectedItem().toString();
    }

    public void setDispose(){
        this.dispose();
    }

    public static void configureEnterNavigation(JTextField textField, JComponent nextField) {
        textField.addActionListener(e -> nextField.requestFocusInWindow());
    }


}
