package view;

import controller.ProductController;
import controller.ProductionOrderController;
import services.ProductionOrderService;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;

public class ProductionConfirmView extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlTop;
    private JPanel pnlCenter;
    private JPanel pnlCenterFour;
    private JTable tbeItensOne;
    private JTable tbeItensTwo;
    private JButton btnCGenerate;
    private JButton btnSGenerate;
    private JButton btnReturn;
    private JPanel pnlCenterOne;
    private JLabel lblProductOne;
    private JLabel lblProductTwo;
    private JLabel lblQuantityOne;
    private JLabel lblQuantityTwo;
    private JPanel pnlCenterTwo;
    private JPanel pnlCenterThree;
    private JScrollPane scpCenterLeft;
    private JScrollPane scpCenterRight;
    private JLabel lblInputLeft;
    private JLabel lblInputRight;
    private JLabel lblAtention;
    private JTextPane txtpAtencion;
    private JPanel pnlCenterBottomRight;
    private JLabel lblStatusOne;
    private JLabel lblStatusTwo;
    private JLabel lblValueOne;
    private JLabel lblValueTwo;
    private JLabel separator;

    Object[][] data = {};
    String[] columnNames = {"PRODUTO", "QUANTIDADE", "UN"};

    public ProductionConfirmView(ProductionView productionView) {
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setTitle("Confirmação");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setResizable(false);
        changeTheme();
        DefaultTableModel modelOne = new DefaultTableModel(ProductionOrderService.getBasicProducts(productionView.getProductId(),productionView.getQuantity()), columnNames);
        DefaultTableModel modelTwo = new DefaultTableModel(ProductionOrderService.getCompositeProducts(productionView.getProductId(),productionView.getQuantity()), columnNames);
        tbeItensOne.setModel(modelOne);
        tbeItensTwo.setModel(modelTwo);

        lblProductTwo.setText(ProductionOrderService.getParentProductName(productionView.getProductId()));
        lblQuantityTwo.setText(String.format("%s %s",productionView.getQuantity(), ProductionOrderService.getParentProductUnit(productionView.getProductId())));
        lblValueTwo.setText(String.format("R$ %.2f", ProductionOrderService.getProductionCost(productionView.getProductId(), productionView.getQuantity())));
        lblStatusTwo.setText(productionView.getStatus());

        btnCGenerate.addActionListener(e -> {
            try {
                ProductionOrderController.addRecursiveProductionOrder(productionView.getRequesterId(), productionView.getProductId(), productionView.getQuantity(), productionView.getStatus());
                productionView.setDispose();
                dispose();
            }catch (Exception ex){
                AppsStyle.showErrorDialog(ex.getMessage(), "Erro de ordem de produção");
            }
        });

        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F6"), "generateC");
        pnlMain.getActionMap().put("generateC", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCGenerate.doClick();
            }
        });


        btnSGenerate.addActionListener(e -> {
            try {
                ProductionOrderController.addProductionOrder(productionView.getRequesterId(), productionView.getProductId(), productionView.getQuantity(), productionView.getStatus());
                productionView.setDispose();
                dispose();
            }catch (Exception ex){
                AppsStyle.showErrorDialog(ex.getMessage(), "Erro de ordem de produção");
            }
        });

        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F5"), "generateS");
        pnlMain.getActionMap().put("generateS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSGenerate.doClick();
            }
        });

        btnReturn.addActionListener(e -> {
            productionView.setDispose();
            dispose();
        });

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

    public void changeTheme(){
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlCenterOne);
        AppsStyle.stylePanel(pnlCenterTwo);
        AppsStyle.stylePanel(pnlCenterThree);
        AppsStyle.stylePanel(pnlCenterFour);
        AppsStyle.stylePanel(pnlCenterBottomRight);
        AppsStyle.styleScrollPanel(scpCenterLeft);
        AppsStyle.styleScrollPanel(scpCenterRight);
        AppsStyle.styleTable(tbeItensOne);
        AppsStyle.styleTable(tbeItensTwo);
        AppsStyle.styleLabelBold(lblAtention);
        AppsStyle.styleLabelBold(lblProductOne);
        AppsStyle.styleLabel(lblProductTwo);
        AppsStyle.styleLabelBold(lblQuantityOne);
        AppsStyle.styleLabel(lblQuantityTwo);
        AppsStyle.styleLabel(lblInputLeft);
        AppsStyle.styleLabel(lblInputRight);
        AppsStyle.styleLabelBold(lblStatusOne);
        AppsStyle.styleLabelBold(lblStatusTwo);
        AppsStyle.styleLabelBold(lblValueOne);
        AppsStyle.styleLabelBold(lblValueTwo);
        AppsStyle.styleButton(btnCGenerate);
        AppsStyle.styleButton(btnSGenerate);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleTextPanel(txtpAtencion);
    }

    public void disableSaveButton(){
        btnCGenerate.setEnabled(false);
        btnSGenerate.setEnabled(false);
        btnCGenerate.setVisible(false);
        btnSGenerate.setVisible(false);
    }
}
