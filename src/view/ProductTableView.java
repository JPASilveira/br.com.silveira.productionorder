package view;

import controller.ProductController;
import controller.ProductGroupController;
import controller.ProductUnitController;
import util.BooleanString;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ProductTableView extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlTop;
    private JComboBox<String> cmbFilter;
    private JTextField txtSearch;
    private JTable tbeItens;
    private JScrollPane scpCenter;
    private JPanel pnlLow;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnRemove;
    private JButton btnSearch;
    private JLabel lblFilter;
    private JPanel pnlLowRight;
    private JPanel pnlLowLeft;

    String[] columnNames = {"ID", "REFERÊNCIA", "NOME", "CUSTO R$", "QUANTIDADE","UN", "GRUPO", "COMPOSTO"};
    Object[][] data;

    public ProductTableView() {
        commonConstructorSetup();
        pack();
        tbeItens.requestFocus();
    }

    public ProductTableView(JTextField txtSearch) {
        this.txtSearch = txtSearch;
        commonConstructorSetup();

        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setTitle("Produtos");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(pnlMain);
        setSize(resolutionCapture.getMinWidth(), resolutionCapture.getMinHeight());
        setLocationRelativeTo(null);

        JButton btnSelect = new JButton("(F6)Selecionar");
        JButton btnReturn = new JButton("(ESC)Retornar");
        AppsStyle.styleButton(btnSelect);
        AppsStyle.styleButton(btnReturn);

        btnSelect.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();
            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                txtSearch.setText(id);
                dispose();
            } else {
                AppsStyle.showErrorDialog("Nenhum produto selecionado", "Erro de Seleção");
            }
        });

        btnReturn.addActionListener(e -> dispose());

        pnlLowRight.add(btnReturn);
        pnlLowRight.add(btnSelect);

        pnlLowRight.remove(btnAdd);
        pnlLowRight.remove(btnEdit);
        pnlLowRight.remove(btnRemove);

        pnlLowLeft.add(btnAdd);
        pnlLowLeft.add(btnEdit);
        pnlLowLeft.add(btnRemove);

        searchData();
        setVisible(true);
    }

    private void commonConstructorSetup() {
        setTitle("Produtos");
        setContentPane(pnlMain);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        changeTheme();
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tbeItens.setModel(model);

        setupInputMaps();

        btnSearch.addActionListener(e -> {
            searchData();
            tbeItens.requestFocus();
        });

        btnAdd.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    ProductView productView = new ProductView(false, "", "", "", "", "", "", "", false);
                    productView.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            searchData();
                            tbeItens.requestFocus();
                        }
                    });
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                }
            });
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();
            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                String reference = tbeItens.getValueAt(selectedRow, 1).toString();
                String name = tbeItens.getValueAt(selectedRow, 2).toString();
                String price = tbeItens.getValueAt(selectedRow, 3).toString();
                String quantity = tbeItens.getValueAt(selectedRow, 4).toString();
                String unit;
                String group;
                try {
                    unit = ProductUnitController.getIdByName(tbeItens.getValueAt(selectedRow, 5).toString());
                } catch (Exception ex) {
                    unit = "0";
                    AppsStyle.showErrorDialog("Erro ao retornar UN, Colocado 0 no campo", "Erro ao retornar UN");
                }
                try {
                    group = ProductGroupController.getIdByName(tbeItens.getValueAt(selectedRow, 6).toString());
                } catch (Exception ex) {
                    group = "0";
                    AppsStyle.showErrorDialog("Erro ao retornar Grupo, Colocado 0 no campo", "Erro ao retornar Grupo");
                }
                boolean isCompose = BooleanString.toBoolean(tbeItens.getValueAt(selectedRow, 7).toString());
                String finalGroup = group;
                String finalUnit = unit;
                SwingUtilities.invokeLater(() -> {
                    try {
                        ProductView productView = new ProductView(true, id, reference, name, price, quantity, finalGroup, finalUnit, isCompose);
                        productView.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                searchData();
                                tbeItens.requestFocus();
                            }
                        });
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                    }
                });
            } else {
                AppsStyle.showErrorDialog("Nenhum produto selecionado", "Erro de Busca");
            }
        });

        btnRemove.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();
            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                try {
                    ProductController.deleteProduct(id);
                    searchData();
                    tbeItens.requestFocus();
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                }
            } else {
                AppsStyle.showErrorDialog("Nenhum produto selecionado", "Erro ao deletar");
            }
        });
        tbeItens.requestFocus();
    }

    private void setupInputMaps() {
        InputMap im = pnlMain.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = pnlMain.getActionMap();

        mapAction(im, am, "F4", "focusFilter", () -> cmbFilter.requestFocusInWindow());
        mapAction(im, am, "F5", "focusSearch", () -> {
            if (!txtSearch.isFocusOwner()) {
                searchData();
                tbeItens.requestFocusInWindow();
            }
        });
        mapAction(im, am, "ENTER", "search", () -> {
            btnSearch.doClick();
            tbeItens.requestFocusInWindow();
        });
        mapAction(im, am, "F1", "addRegister", () -> btnAdd.doClick());
        mapAction(im, am, "F2", "registrationEdit", () -> btnEdit.doClick());
        mapAction(im, am, "F3", "removeRegistration", () -> btnRemove.doClick());
        mapAction(im, am, "F6", "selectRegister", () -> {
            Component[] components = pnlLowRight.getComponents();
            for (Component component : components) {
                if (component instanceof JButton && ((JButton) component).getText().contains("Selecionar")) {
                    ((JButton) component).doClick();
                    break;
                }
            }
        });
        mapAction(im, am, "ESCAPE", "return", () -> {
            Component[] components = pnlLowRight.getComponents();
            for (Component component : components) {
                if (component instanceof JButton && ((JButton) component).getText().contains("Retornar")) {
                    ((JButton) component).doClick();
                    break;
                }
            }
        });
        configureEnterNavigation(cmbFilter, txtSearch);
    }

    public void changeTheme(){
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.stylePanel(pnlLowLeft);
        AppsStyle.stylePanel(pnlLowRight);
        AppsStyle.styleScrollPanel(scpCenter);
        AppsStyle.styleTable(tbeItens);
        AppsStyle.styleComboBox(cmbFilter);
        AppsStyle.styleTextField(txtSearch);
        AppsStyle.styleButton(btnSearch);
        AppsStyle.styleButton(btnRemove);
        AppsStyle.styleButton(btnEdit);
        AppsStyle.styleButton(btnAdd);
        AppsStyle.styleLabelBold(lblFilter);

    }

    private void mapAction(InputMap im, ActionMap am, String keyStroke, String actionName, Runnable action) {
        im.put(KeyStroke.getKeyStroke(keyStroke), actionName);
        am.put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    private void updateTable(Object[][] tableData) {
        if (tbeItens == null) {
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
        data = ProductController.searchProduct(searchOption, searchText);

        updateTable(data);
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

