package view;

import controller.ProductGroupController;
import util.ResolutionCapture;
import view.styles.AppsStyle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class ProductGroupTableView extends JFrame {
    private JPanel pnlMain;
    private JComboBox cmbSearch;
    private JTextField txtSearch;
    private JTable tbeItens;
    private JButton btnReturn;
    private JButton btnSelect;
    private JButton btnRemove;
    private JButton btnEdit;
    private JButton btnAdd;
    private JPanel pnlTop;
    private JButton btnSearch;
    private JScrollPane scpCenter;
    private JPanel pnlLow;
    private JPanel pnlLowRight;
    private JPanel pnlLowLeft;
    private JLabel lblSearch;
    private ProductView parentView;
    private DefaultTableModel model;
    Object[][] data;

    public ProductGroupTableView(ProductView parentView) {
        this.parentView = parentView;
        setTitle("Grupo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setSize(resolutionCapture.getMinWidth(), resolutionCapture.getMinHeight());
        setLocationRelativeTo(null);
        setContentPane(pnlMain);
        changeTheme();

        DefaultTableModel model = new DefaultTableModel(data, new String[] {"ID", "NOME"});
        tbeItens.setModel(model);

        //Ação do botão de busca
        btnSearch.addActionListener(e -> {
            try {
                searchData();
            } catch (Exception ex) {
                AppsStyle.showErrorDialog(ex.getMessage(), "Erro de Busca");
            }
        });

        //Focar no combox dos filtros
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F4"), "focusFilter");
        pnlMain.getActionMap().put("focusFilter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbSearch.requestFocus();
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

        //Ação do botão de Adicionar
        btnAdd.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        new ProductGroupView(false, "", "");
                    }catch (Exception e){
                        AppsStyle.showErrorDialog(e.getMessage(), "Erro de produto");
                    }
                }
            });
        });

        //Atalho para Adicionar (F1)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F1"), "add");
        pnlMain.getActionMap().put("add", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAdd.doClick();
                tbeItens.requestFocus();
            }
        });

        //Ação do botão Editar
        btnEdit.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();

            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                String name = tbeItens.getValueAt(selectedRow, 1).toString();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new ProductGroupView(true, id, name);
                        }catch (Exception e){
                            AppsStyle.showErrorDialog(e.getMessage(), "Erro de produto");
                        }
                    }
                } );
            } else {
                AppsStyle.showErrorDialog("Nenhum grupo selecionado", "Erro de Busca");
            }
        });

        //Atalho para Editar (F2)
        pnlMain.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke("F2"), "editU");
        pnlMain.getActionMap().put("editU", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEdit.doClick();
                //tbeItens.requestFocus();
            }
        });

        //Ação do botão Remover
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbeItens.getSelectedRow();

                if (selectedRow != -1) {
                    String id = tbeItens.getValueAt(selectedRow, 0).toString();
                    try {
                        ProductGroupController.deleteProductGroup(id);
                        searchData();
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                    }
                }else {
                    AppsStyle.showErrorDialog("Nenhum grupo selecionado", "Erro ao deletar");
                }
            }
        });

        //Atalho para Remover (F3)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F3"), "remove");
        pnlMain.getActionMap().put("remove", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRemove.doClick();
                tbeItens.requestFocus();
            }
        });


        //Ação do botão Remover
        btnSelect.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();
            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();

                if (parentView != null) {
                    parentView.setTxtGroup(id);
                }

                dispose();
            }
        });

        //Atalho para Selecionar (Enter)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F6"), "select");
        pnlMain.getActionMap().put("select", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSelect.doClick();
            }
        });

        //Ação do botão Retornar
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

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                searchData();
                tbeItens.requestFocus();
            }
        });

        configureEnterNavigation(cmbSearch, txtSearch);
        setVisible(true);
    }

    private void updateTable(Object[][] tableData) {
        if (tbeItens == null) {
            return;
        }

        String[] columnNames = {"ID", "NOME"};
        DefaultTableModel model = new DefaultTableModel(tableData, columnNames);

        tbeItens.setModel(model);
        tbeItens.setDefaultEditor(Object.class, null);
        tbeItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void searchData(){
        String searchText = txtSearch.getText();
        String searchOption = cmbSearch.getSelectedItem().toString();
        data = ProductGroupController.searchProductGroup(searchOption, searchText);

        updateTable(data);
    }

    public void changeTheme() {
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.styleScrollPanel(scpCenter);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.stylePanel(pnlLowRight);
        AppsStyle.stylePanel(pnlLowLeft);
        AppsStyle.styleComboBox(cmbSearch);
        AppsStyle.styleTextField(txtSearch);
        AppsStyle.styleButton(btnSearch);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleButton(btnSelect);
        AppsStyle.styleButton(btnRemove);
        AppsStyle.styleButton(btnEdit);
        AppsStyle.styleButton(btnAdd);
        AppsStyle.styleTable(tbeItens);
        AppsStyle.styleLabelBold(lblSearch);
    }

    public static void configureEnterNavigation(JComboBox comboBox, JComponent nextField) {
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

