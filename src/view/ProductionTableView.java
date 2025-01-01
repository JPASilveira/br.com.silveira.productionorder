package view;

import controller.ProductController;
import controller.ProductionOrderController;
import services.ProductionOrderService;
import util.PathUtil;
import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class ProductionTableView extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlTop;
    private JComboBox<String> cmbFilter;
    private JTextField txtSearch;
    private JTable tbeItens;
    private JScrollPane scpCenter;
    private JPanel pnlLow;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSearch;
    private JLabel lblFilter;
    private JButton btnPreview;
    private JButton btnPrint;
    private JPanel pnlLowRight;
    private JPanel pnlLowLeft;
    private JRadioButton rbOrdersCompleted;
    private JPanel pnlTopRight;

    String[] columnNames = {"ID", "REQUISITANTE", "PRODUTO", "STATUS"};
    Object[][] data;

    public ProductionTableView() {
        setTitle("Ordem de produção");
        setContentPane(pnlMain);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();

        searchData();
        changeTheme();

        //Ação do botão de busca
        btnSearch.addActionListener(e ->{
            searchData();
            tbeItens.requestFocus();
        });

        //Ação do botão filtro
        rbOrdersCompleted.addActionListener(e ->{
            searchData();
        });

        //Focar no combox dos filtros
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F4"), "focusFilter");
        pnlMain.getActionMap().put("focusFilter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rbOrdersCompleted.requestFocus();
            }
        });

        //Focar no Campo de Busca (F5)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F5"), "focusSearch");
        pnlMain.getActionMap().put("focusSearch", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSearch.requestFocus();
            }
        });

        //Executar Busca
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ENTER"), "search");
        pnlMain.getActionMap().put("search", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSearch.doClick();
                tbeItens.requestFocus();
            }
        });

        //Ação do botão de adicionar
        btnAdd.addActionListener(e ->{
            SwingUtilities.invokeLater(() -> {
                try {
                ProductionView productionView = new ProductionView(false,"","", "", "");

                productionView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        searchData();
                    }
                });
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de ordem de produção");
                }
            });
        });

        //Atalho do botão adicionar(F1)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F1"), "addProductionComposition");
        pnlMain.getActionMap().put("addProductionComposition", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAdd.doClick();
            }
        });

        //Ação do botão editar
        btnEdit.addActionListener(e ->{
            int selectedRow = tbeItens.getSelectedRow();
            String requesterId = "";
            String productId = "";
            String quantity = "";

            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                try {
                    requesterId = ProductionOrderController.getRequestIdByProductionOrderId(id);
                    productId = ProductionOrderController.getProductIdByProductionOrderId(id);
                    quantity = ProductionOrderController.getQuantityByProductionOrderId(id);
                } catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de busca");
                }

                String finalRequesterId = requesterId;
                String finalProductId = productId;
                String finalQuantity = quantity;


                SwingUtilities.invokeLater(() -> {
                    try {
                        ProductionView productionView = new ProductionView(true, id, finalRequesterId, finalProductId, finalQuantity);

                        productionView.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent e) {
                                searchData();
                            }
                        });
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de ordem de produção");
                    }
                });
            }else {
                AppsStyle.showErrorDialog("Nenhuma ordem selecionada", "Erro de busca");
            }
        });

        //Ação do botão editar
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F2"), "editProductionComposition");
        pnlMain.getActionMap().put("editProductionComposition", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEdit.doClick();
            }
        });

        //Ação do botão remover
        btnDelete.addActionListener(e ->{
            int selectedRow = tbeItens.getSelectedRow();
            String id;
            String status;

            if (selectedRow != -1) {
                id = tbeItens.getValueAt(selectedRow, 0).toString();
                status = tbeItens.getValueAt(selectedRow, 3).toString();
                try {
                    ProductionOrderController.removeProductionOrder(id,status);
                    searchData();
                }catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao remover");
                }
            }else {
                AppsStyle.showErrorDialog("Nenhuma ordem selecionada", "Erro de busca");
            }
        });

        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F3"), "removeProductionComposition");
        pnlMain.getActionMap().put("removeProductionComposition", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDelete.doClick();
            }
        });

        btnPreview.addActionListener(e ->{
            int selectedRow = tbeItens.getSelectedRow();
            String requesterId = "";
            String productId = "";
            String quantity = "";

            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                try {
                    requesterId = ProductionOrderController.getRequestIdByProductionOrderId(id);
                    productId = ProductionOrderController.getProductIdByProductionOrderId(id);
                    quantity = ProductionOrderController.getQuantityByProductionOrderId(id);
                } catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de busca");
                }

                String finalRequesterId = requesterId;
                String finalProductId = productId;
                String finalQuantity = quantity;


                SwingUtilities.invokeLater(() -> {
                    try {
                        ProductionView productionView = new ProductionView(true, true, id, finalRequesterId, finalProductId, finalQuantity);

                        productionView.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent e) {
                                searchData();
                            }
                        });
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de ordem de produção");
                    }
                });
            }else {
                AppsStyle.showErrorDialog("Nenhuma ordem selecionada", "Erro de busca");
            }
        });

        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F6"), "previewProductionComposition");
        pnlMain.getActionMap().put("previewProductionComposition", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPreview.doClick();
            }
        });

        //Ação do botão imprimir
        btnPrint.addActionListener(e ->{
            int selectedRow = tbeItens.getSelectedRow();

            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                String originalText = btnPrint.getText();
                btnPrint.setText("Aguarde ...");
                btnPrint.setEnabled(false);

                new Thread(() -> {
                    try {
                        ProductionOrderService.getPDFReport(id);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de CNPJ");
                    } finally {
                        SwingUtilities.invokeLater(() -> {
                            btnPrint.setText(originalText);
                            btnPrint.setEnabled(true);
                            try {
                                // Caminho para o arquivo PDF
                                File pdfFile = new File(PathUtil.getCurrentDirectory()+"/report/report.pdf");

                                // Verifica se o Desktop está disponível e tenta abrir o arquivo
                                if (Desktop.isDesktopSupported()) {
                                    Desktop desktop = Desktop.getDesktop();
                                    desktop.open(pdfFile);
                                } else {
                                    System.out.println("Desktop não suportado.");
                                }
                            } catch (Exception ex){
                                AppsStyle.showErrorDialog(ex.getMessage(), "Erro de ordem de produção");
                            }
                        });
                    }
                }).start();
            }else {
                AppsStyle.showErrorDialog("Nenhuma ordem selecionada", "Erro de busca");
            }
        });

        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F7"), "printProductionComposition");
        pnlMain.getActionMap().put("printProductionComposition", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPrint.doClick();
            }
        });


        configureEnterNavigation(rbOrdersCompleted,cmbFilter);
        configureEnterNavigation(cmbFilter,txtSearch);
    }

    public void changeTheme(){
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlTopRight);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.stylePanel(pnlLowRight);
        AppsStyle.stylePanel(pnlLowLeft);
        AppsStyle.styleScrollPanel(scpCenter);
        AppsStyle.styleTable(tbeItens);
        AppsStyle.styleComboBox(cmbFilter);
        AppsStyle.styleTextField(txtSearch);
        AppsStyle.styleLabelBold(lblFilter);
        AppsStyle.styleButton(btnSearch);
        AppsStyle.styleButton(btnDelete);
        AppsStyle.styleButton(btnEdit);
        AppsStyle.styleButton(btnAdd);
        AppsStyle.styleButton(btnPreview);
        AppsStyle.styleButton(btnPrint);
        AppsStyle.styleRadioButton(rbOrdersCompleted);
        scpCenter.getVerticalScrollBar().setBackground(AppsStyle.btnColor);
        scpCenter.getVerticalScrollBar().setBorder(BorderFactory.createLineBorder(AppsStyle.btnColor));
    }

    private void updateTable(Object[][] tableData){
        if (tbeItens == null){
            return;
        }

        DefaultTableModel model = new DefaultTableModel(tableData, columnNames);
        tbeItens.setModel(model);
        tbeItens.setDefaultEditor(Object.class, null);
        tbeItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void searchData(){
        String searchOption = cmbFilter.getSelectedItem().toString();
        String searchText = txtSearch.getText();
        try {
            data = ProductionOrderController.searchProductionOrder(searchOption, searchText, rbOrdersCompleted.isSelected());
        }catch (Exception e){
            AppsStyle.showErrorDialog(e.getMessage(), "Erro ao buscar");
            txtSearch.setText("");
        }


        updateTable(data);
    }

    public JTable getTbeItens() {
        return tbeItens;
    }

    private void createUIComponents() {
        tbeItens = new JTable(new DefaultTableModel(data, columnNames));
        scpCenter = new JScrollPane(tbeItens);
    }

    public JPanel getPnlMain() {
        return pnlMain;
    }

    public void requestFocusOnTable() {
        SwingUtilities.invokeLater(() -> tbeItens.requestFocusInWindow());
    }

    public static void configureEnterNavigation(JRadioButton radioButton, JComponent nextField) {
        InputMap im = radioButton.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = radioButton.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        am.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextField.requestFocusInWindow();
            }
        });
    }

    public static void configureEnterNavigation(JComboBox<?> comboBox, JComponent nextField) {
        InputMap im = comboBox.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = comboBox.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        am.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextField.requestFocusInWindow();
            }
        });
    }
}
