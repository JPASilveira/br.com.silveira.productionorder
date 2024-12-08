package view;

import controller.ProductController;
import controller.ProductGroupController;
import controller.ProductUnitController;
import controller.RegistrationAddressController;
import util.BooleanString;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegistrationAddressTableView extends JFrame {
    private JPanel pnlMain;
    private JComboBox cmbSearch;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTable tbeItens;
    private JButton btnSelect;
    private JButton btnReturn;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnRemove;
    private JPanel pnlTop;
    private JScrollPane scpCenter;
    private JPanel pnlLow;
    private JPanel pnlLowLeft;
    private JPanel pnlLowRight;
    private JLabel lblSearch;
    private DefaultTableModel model;
    private Object[][] data;

    public RegistrationAddressTableView() {
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setTitle("Endereços");
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
            SwingUtilities.invokeLater(() -> {
                RegistrationAddressView registrationAddressView = new RegistrationAddressView(false,"","","","","","","");
                registrationAddressView.addWindowListener(new WindowAdapter() {
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
                String cep = tbeItens.getValueAt(selectedRow, 1).toString();
                String street = tbeItens.getValueAt(selectedRow, 2).toString();
                String number = tbeItens.getValueAt(selectedRow, 3).toString();
                String neighborhood = tbeItens.getValueAt(selectedRow, 4).toString();
                String city = tbeItens.getValueAt(selectedRow, 5).toString();
                String state = tbeItens.getValueAt(selectedRow, 6).toString();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        RegistrationAddressView registrationAddressView = new RegistrationAddressView(true, id, cep, street, number, neighborhood, city, state);
                        registrationAddressView.addWindowListener(new WindowAdapter() {
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
                    RegistrationAddressController.deleteRegistrationAddress(id);
                    searchData();
                    tbeItens.requestFocus();
                }else {
                    AppsStyle.showErrorDialog("Nenhum endereço selecionado", "Erro ao deletar");
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

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                searchData();
                tbeItens.requestFocus();
            }
        });
    }


    private String[] getTableColumns(){
        return new String[] {"ID", "CEP", "RUA", "NUMERO", "BAIRRO", "CIDADE", "ESTADO"};
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
        data = RegistrationAddressController.searchAddress(searchOption, searchText);

        updateTable(data);
    }

    public void changeTheme(){
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.styleScrollPanel(scpCenter);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.stylePanel(pnlLowRight);
        AppsStyle.stylePanel(pnlLowLeft);
        AppsStyle.styleLabelBold(lblSearch);
        AppsStyle.styleComboBox(cmbSearch);
        AppsStyle.styleTextField(txtSearch);
        AppsStyle.styleButton(btnSearch);
        AppsStyle.styleButton(btnAdd);
        AppsStyle.styleButton(btnEdit);
        AppsStyle.styleButton(btnRemove);
        AppsStyle.styleButton(btnSelect);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleTable(tbeItens);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RegistrationAddressTableView().setVisible(true);
            }
        });
    }
}
