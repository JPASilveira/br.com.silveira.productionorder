package view;

import controller.ProductUnitController;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProductUnitTableView extends JFrame{
    ResolutionCapture resolutionCapture = new ResolutionCapture();
    private JPanel pnlMain;
    private JPanel pnlTop;
    private JComboBox cmbSearch;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTable tbeItens;
    private JButton btnReturn;
    private JButton btnSelect;
    private JButton btnRemove;
    private JButton btnEdit;
    private JButton btnAdd;
    private JScrollPane scpCenter;
    private JPanel pnlLow;
    private JPanel pnlLowLeft;
    private JPanel pnlLowRight;
    private JLabel lblSearch;
    private ProductView parentView;
    private DefaultTableModel model;
    private Object[][] data;

    public ProductUnitTableView(ProductView parentView) {
        this.parentView = parentView;
        setTitle("Unidade");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setContentPane(pnlMain);
        setResizable(false);
        changeTheme();

        DefaultTableModel model = new DefaultTableModel(data, getTableColumns());
        tbeItens.setModel(model);
        tbeItens.setDefaultEditor(Object.class, null);
        tbeItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Ação do botão busca
        btnSearch.addActionListener(e -> {
            try {
                searchData();
            }catch (Exception ex) {
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

        //Ação do botão adicionar
        btnAdd.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable()  {
                @Override
                public void run() {
                    try {
                        new ProductUnitView(false, "", "", "");
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
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

        //Ação botão editar
        btnEdit.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();

            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                String name = tbeItens.getValueAt(selectedRow, 1).toString();
                String acronym = tbeItens.getValueAt(selectedRow, 2).toString();

                SwingUtilities.invokeLater(new Runnable()  {
                    @Override
                    public void run() {
                        try {
                            new ProductUnitView(true, id, name, acronym);
                        }catch (Exception ex){
                            AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                        }
                    }
                });
            }else {
                AppsStyle.showErrorDialog("Nenhuma unidade selecionada", "Erro ao Editar");
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

        //Ação do botão remover
        btnRemove.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();

            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                try {
                    ProductUnitController.deleteProductUnit(id);
                    searchData();
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de produto");
                }
            }else {
                AppsStyle.showErrorDialog("Nenhum grupo selecionado", "Erro ao Deletar");
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

        //Ação do botão selecionar
        btnSelect.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();

            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();

                if (parentView != null) {
                    parentView.setTxtUnit(id);
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

        //Ação do botão return
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

    private String[] getTableColumns(){
        return new String[] {"ID", "NOME", "UNIDADE"};
    }

    private void updateTable(Object[][] tableData) {
        if (tbeItens == null) {
            return;
        }

        model = new DefaultTableModel(tableData, getTableColumns());
        tbeItens.setModel(model);
        tbeItens.setDefaultEditor(Object.class, null);
        tbeItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void searchData(){
        String searchText = txtSearch.getText();
        String searchOption = cmbSearch.getSelectedItem().toString();
        data = ProductUnitController.searchProductUnit(searchOption, searchText);

        updateTable(data);
    }

    public void changeTheme(){
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.styleScrollPanel(scpCenter);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.stylePanel(pnlLowLeft);
        AppsStyle.stylePanel(pnlLowRight);
        AppsStyle.styleComboBox(cmbSearch);
        AppsStyle.styleTextField(txtSearch);
        AppsStyle.styleButton(btnSearch);
        AppsStyle.styleTable(tbeItens);
        AppsStyle.styleButton(btnSelect);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleButton(btnRemove);
        AppsStyle.styleButton(btnEdit);
        AppsStyle.styleButton(btnAdd);;
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
