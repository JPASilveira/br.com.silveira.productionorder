package view;

import controller.ProductCompositionController;
import controller.ProductController;
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

    public ProductView(boolean isUpdate, String productId, String productReference, String productName, String productPrice, String productQuantity, String productGroup, String productUnit, boolean productCompose) {
        setTitle("Produto");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setResizable(false);

        if (isUpdate) {
            btnSave.setText("(F6) Atualizar");
            lblId.setText("ID: " + productId);
            txtReference.setText(productReference);
            txtName.setText(productName);
            txtPrice.setText(productPrice);
            txtQuantity.setText(productQuantity);
            txtGroup.setText(productGroup);
            txtUnit.setText(productUnit);
            cbIsCompose.setSelected(productCompose);
        }

        changeTheme();

        //Ação do botão busca grupo
        btnGroupSearch.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        new ProductGroupTableView(ProductView.this);
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                    }
                }
            });
        });

        //Atalho para buscar grupo (F2)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F2"), "searchGroup");
        pnlMain.getActionMap().put("searchGroup", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGroupSearch.doClick();
            }
        });

        //Ação do botão busca unidade
        btnUnitSearch.addActionListener(e ->  {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        new ProductUnitTableView(ProductView.this);
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                    }
                }
            });
        });

        //Atalho para buscar unidade (F2)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F1"), "searchUnit");
        pnlMain.getActionMap().put("searchUnit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnUnitSearch.doClick();
            }
        });

        //Ação do botão salvar
        btnSave.addActionListener(e -> {
            String reference = txtReference.getText();
            String name = txtName.getText();
            String price = txtPrice.getText();
            String quantity = txtQuantity.getText();
            String unit = txtUnit.getText();
            String group = txtGroup.getText();
            boolean compose = cbIsCompose.isSelected();
            if(isUpdate){
                try {
                    if(cbIsCompose.isSelected() && ProductCompositionController.isEmptyRecord(productId)){
                        AppsStyle.showErrorDialog("A caixa de composição está marcada, mas não existe nenhum produto na composição", "Erro de produto");
                    }else {
                        ProductController.updateProduct(productId, reference, name, price, quantity, unit, group, compose);
                        dispose();
                    }
                } catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao editar");
                }
            }
            else {
                try {
                    ProductController.addProduct(reference, name, price, quantity, unit, group, compose);
                    dispose();
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao adicionar");
                }
            }
        });

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

        // Atalho para Retornar (Esc)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "return");
        pnlMain.getActionMap().put("return", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnReturn.doClick();
            }
        });

        //Ação da caixa de composição
        cbIsCompose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isUpdate){
                    AppsStyle.showErrorDialog("Salve o produto primeiro, depois edite para habilitar a composição","Habilitar composição");
                    cbIsCompose.setSelected(false);
                }else {
                    try {
                        ProductController.enableComposite(productId);
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao habilitar composição");
                    }
                }
            }
        });

        //Ação do botão de composição
        btnCompose.addActionListener(e -> {
            if(!isUpdate){
                cbIsCompose.setSelected(false);
            }else {
                cbIsCompose.setSelected(true);
            }
            if(cbIsCompose.isSelected()){
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new ProductCompositionTableView(productId);
                        }catch (Exception ex){
                            AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                        }
                    }
                });
            }else {
                AppsStyle.showErrorDialog("A opção de produto composto deve estar selecionada", "Erro composição");
            }
        });
        // Atalho para composição (F3)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F3"), "composition");
        pnlMain.getActionMap().put("composition", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCompose.doClick();
            }
        });


        configureEnterNavigation(txtReference, txtName);
        configureEnterNavigation(txtName, txtPrice);
        configureEnterNavigation(txtPrice, txtQuantity);
        configureEnterNavigation(txtQuantity, txtUnit);
        configureEnterNavigation(txtUnit, txtGroup);
        configureEnterNavigation(txtGroup, txtReference);

        setVisible(true);
    }

    public void setTxtGroup(String txtGroup) {
        this.txtGroup.setText(txtGroup);
    }

    public void setTxtUnit(String txtUnit) {
        this.txtUnit.setText(txtUnit);
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
        AppsStyle.stylePanel(pnlCenterThree);
        AppsStyle.stylePanel(pnlCenterFour);
        AppsStyle.stylePanel(pnlCenterFive);
        AppsStyle.stylePanel(pnlCenterSix);
        AppsStyle.stylePanel(pnlSeven);
        AppsStyle.stylePanel(pnlSevenLeft);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.styleLabelBold(lblId);
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
