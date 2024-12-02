package view;

import controller.ProductController;
import controller.ProductGroupController;
import controller.ProductUnitController;
import util.BooleanString;
import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    String[] columnNames = {"ID", "REFERÊNCIA", "NOME", "PREÇO", "QUANTIDADE","UN", "GRUPO", "COMPOSTO"};
    Object[][] data;

    public ProductTableView() {
        setTitle("Produtos");
        setContentPane(pnlMain);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();

        changeTheme();

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tbeItens.setModel(model);

        //Ação do botão buscar
        btnSearch.addActionListener(e -> {
            searchData();
            tbeItens.requestFocus();
        });

        //Focar no combox dos filtros
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F4"), "focusFilter");
        pnlMain.getActionMap().put("focusFilter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbFilter.requestFocus();
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

        //Ação do botão adicionar
        btnAdd.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                ProductView productView = new ProductView(false, "", "", "", "", "", "", "", false);

                productView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        searchData();
                        tbeItens.requestFocus();
                    }
                });
            });
        });

        //Atalho para adicionar unidade (F1)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F1"), "addProduct");
        pnlMain.getActionMap().put("addProduct", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAdd.doClick();
            }
        });

        //Ação do botão Editar
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
                }catch (Exception ex) {
                    group = "0";
                    AppsStyle.showErrorDialog("Erro ao retornar Grupo, Colocado 0 no campo", "Erro ao retornar Grupo");
                }

                boolean isCompose = BooleanString.toBoolean(tbeItens.getValueAt(selectedRow, 7).toString());

                String finalGroup = group;
                String finalUnit = unit;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ProductView productView = new ProductView(true, id, reference, name, price, quantity, finalGroup, finalUnit, isCompose);

                        productView.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                searchData();
                                tbeItens.requestFocus();
                            }
                        });
                    }
                } );
            } else {
                AppsStyle.showErrorDialog("Nenhum produto selecionado", "Erro de Busca");
            }
        });

        //Atalho para editar produto (F2)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F2"), "productEdit");
        pnlMain.getActionMap().put("productEdit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEdit.doClick();
            }
        });

        //Ação do botão Remover
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbeItens.getSelectedRow();

                if (selectedRow != -1) {
                    String id = tbeItens.getValueAt(selectedRow, 0).toString();
                    ProductController.deleteProduct(id);
                    searchData();
                    tbeItens.requestFocus();
                }else {
                    AppsStyle.showErrorDialog("Nenhum produto selecionado", "Erro ao deletar");
                }
            }
        });

        //Atalho para remover produto (F3)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F3"), "removeProduct");
        pnlMain.getActionMap().put("removeProduct", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRemove.doClick();
                tbeItens.requestFocus();
            }
        });

        tbeItens.requestFocus();
    }


    public void changeTheme(){
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlLow);
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
}
