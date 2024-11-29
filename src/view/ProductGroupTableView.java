package view;

import controller.ProductGroupController;
import util.ResolutionCapture;
import view.styles.AppsStyle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    private ProductView parentView;
    private DefaultTableModel model;
    Object[][] data;

    public ProductGroupTableView(ProductView parentView) {
        this.parentView = parentView;
        setTitle("Grupo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setSize(resolutionCapture.getWidth() / 2, resolutionCapture.getHeight() / 2);
        setLocationRelativeTo(null);
        setContentPane(pnlMain);
        setResizable(false);
        changeTheme();

        DefaultTableModel model = new DefaultTableModel(data, new String[] {"ID", "NOME"});
        tbeItens.setModel(model);

        btnSearch.addActionListener(e -> {
            try {
                searchData();
            } catch (Exception ex) {
                AppsStyle.showErrorDialog(ex.getMessage(), "Erro de Busca");
            }
        });

        btnAdd.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                  new ProductGroupView(false, "", "");
                }
            });
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();

            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                String name = tbeItens.getValueAt(selectedRow, 1).toString();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new ProductGroupView(true, id, name);
                    }
                } );
            } else {
                AppsStyle.showErrorDialog("Nenhum grupo selecionado", "Erro de Busca");
            }
        });


        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbeItens.getSelectedRow();

                if (selectedRow != -1) {
                    String id = tbeItens.getValueAt(selectedRow, 0).toString();
                    ProductGroupController.deleteProductGroup(id);
                    searchData();
                }else {
                    AppsStyle.showErrorDialog("Nenhum grupo selecionado", "Erro ao deletar");
                }
            }
        });

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

        btnReturn.addActionListener(e -> {
            dispose();
        });

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                searchData();
            }
        });
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
    }
}

