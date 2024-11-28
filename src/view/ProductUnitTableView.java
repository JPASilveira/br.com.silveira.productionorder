package view;

import controller.ProductUnitController;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProductUnitTableView extends JFrame{
    ResolutionCapture resolutionCapture = new ResolutionCapture();
    private JPanel pnlMain;
    private JPanel pnlTop;
    private JComboBox cmbFilter;
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


        btnSearch.addActionListener(e -> {
            try {
                searchData();
            }catch (Exception ex) {
                AppsStyle.showErrorDialog(ex.getMessage(), "Erro de Busca");
            }
        });

        btnAdd.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable()  {
                @Override
                public void run() {
                    new ProductUnitView(false, "", "", "");
                }
            });
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();

            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                String name = tbeItens.getValueAt(selectedRow, 1).toString();
                String acronym = tbeItens.getValueAt(selectedRow, 2).toString();

                SwingUtilities.invokeLater(new Runnable()  {
                    @Override
                    public void run() {
                        new ProductUnitView(true , id, name, acronym);
                    }
                });
            }else {
                AppsStyle.showErrorDialog("Nenhuma unidade selecionada", "Erro ao Editar");
            }
        });

        btnRemove.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();

            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                ProductUnitController.deleteProductUnit(id);
                searchData();
            }else {
                AppsStyle.showErrorDialog("Nenhum grupo selecionado", "Erro ao Deletar");
            }
        });

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
        String search = txtSearch.getText();
        String searchOption = cmbFilter.getSelectedItem().toString();
        data = ProductUnitController.searchProductUnit(search, searchOption);

        updateTable(data);
    }

    public void changeTheme(){
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.styleScrollPanel(scpCenter);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.stylePanel(pnlLowLeft);
        AppsStyle.stylePanel(pnlLowRight);
        AppsStyle.styleComboBox(cmbFilter);
        AppsStyle.styleTextField(txtSearch);
        AppsStyle.styleButton(btnSearch);
        AppsStyle.styleTable(tbeItens);
        AppsStyle.styleButton(btnSelect);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleButton(btnRemove);
        AppsStyle.styleButton(btnEdit);
        AppsStyle.styleButton(btnAdd);
    }

}
