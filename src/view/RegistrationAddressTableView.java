package view;


import controller.RegistrationAddressController;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

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

    public RegistrationAddressTableView(JTextField idReturn) {
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setTitle("Endereços");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getMinWidth(), resolutionCapture.getMinHeight());
        setLocationRelativeTo(null);
        setContentPane(pnlMain);
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
                try {
                    RegistrationAddressView registrationAddressView = new RegistrationAddressView(false, "", "", "", "", "", "", "");
                    registrationAddressView.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            searchData();
                            tbeItens.requestFocus();
                        }
                    });
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de registro");
                }
            });
        });

        //Atalho para adicionar unidade (F1)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F1"), "addressAdd");
        pnlMain.getActionMap().put("addressAdd", new AbstractAction() {
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
                        try {
                            RegistrationAddressView registrationAddressView = new RegistrationAddressView(true, id, cep, street, number, neighborhood, city, state);
                            registrationAddressView.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                    searchData();
                                    tbeItens.requestFocus();
                                }
                            });
                        }catch (Exception ex){
                            AppsStyle.showErrorDialog(ex.getMessage(), "Erro de registro");
                        }
                    }
                } );
            } else {
                AppsStyle.showErrorDialog("Nenhum produto selecionado", "Erro de Busca");
            }
        });

        //Atalho para editar produto (F2)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F2"), "addressEdit");
        pnlMain.getActionMap().put("addressEdit", new AbstractAction() {
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
                    try {
                        RegistrationAddressController.deleteRegistrationAddress(id);
                        searchData();
                        tbeItens.requestFocus();
                    }catch (Exception ex){
                            AppsStyle.showErrorDialog(ex.getMessage(), "Erro de registro");
                        }
                }else {
                    AppsStyle.showErrorDialog("Nenhum endereço selecionado", "Erro ao deletar");
                }
            }
        });

        //Atalho para remover (F3)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F3"), "removeAddress");
        pnlMain.getActionMap().put("removeAddress", new AbstractAction() {
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
                idReturn.setText(id);
            }
            dispose();
        });

        //Atalho para selecionar (F6)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F6"), "selectAddress");
        pnlMain.getActionMap().put("selectAddress", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSelect.doClick();
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


        searchData();
        configureEnterNavigation(cmbSearch,txtSearch);

        setVisible(true);
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

    public void requestFocus() {
        tbeItens.requestFocus();
    }
}
