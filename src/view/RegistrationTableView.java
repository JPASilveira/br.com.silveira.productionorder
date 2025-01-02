package view;

import controller.RegistrationController;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegistrationTableView extends JFrame {
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

    String[] columnNames = {"ID", "TIPO", "NOME", "NOME FANTASIA", "DOCUMENTO", "IE", "CONTATO", "EMAIL", "ID ENDEREÇO", "ENDEREÇO"};
    Object[][] data;

    public RegistrationTableView() {
        commonConstructorSetup();
    }

    public RegistrationTableView(JTextField txtSearch) {
        this.txtSearch = txtSearch;
        commonConstructorSetup();

        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setTitle("Registros");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(pnlMain);
        setSize(resolutionCapture.getMinWidth(), resolutionCapture.getMinHeight());
        setLocationRelativeTo(null);

        JButton btnSelect = new JButton("(F6)Selecionar");
        JButton btnReturn = new JButton("(ESC)Retornar");
        AppsStyle.styleButton(btnSelect);
        AppsStyle.styleButton(btnReturn);

        pnlLowRight.add(btnReturn);
        pnlLowRight.add(btnSelect);

        pnlLowRight.remove(btnAdd);
        pnlLowRight.remove(btnEdit);
        pnlLowRight.remove(btnRemove);

        pnlLowLeft.add(btnAdd);
        pnlLowLeft.add(btnEdit);
        pnlLowLeft.add(btnRemove);

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

        btnSelect.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();
            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                txtSearch.setText(id);
                dispose();
            }
        });

        btnReturn.addActionListener(e -> dispose());
        setVisible(true);
    }

    private void commonConstructorSetup() {
        setTitle("Registros");
        setContentPane(pnlMain);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setSize(resolutionCapture.getMinWidth(), resolutionCapture.getMinHeight());

        changeTheme();
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tbeItens.setModel(model);
        searchData();
        setupInputMaps();
        setupButtonActions();
    }

    private void setupButtonActions() {
        btnSearch.addActionListener(e -> {
            searchData();
            tbeItens.requestFocusInWindow();
        });

        btnAdd.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {

                RegistrationView registrationView = new RegistrationView(false, "", "", "", "", "", "", "", "", "");
                registrationView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        searchData();
                        tbeItens.requestFocusInWindow();
                    }
                });
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de registro");
                }
            });
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();
            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                String type = tbeItens.getValueAt(selectedRow, 1).toString();
                String name = tbeItens.getValueAt(selectedRow, 2).toString();
                String fantasyName = tbeItens.getValueAt(selectedRow, 3).toString();
                String document = tbeItens.getValueAt(selectedRow, 4).toString();
                String ie = tbeItens.getValueAt(selectedRow, 5).toString();
                String contact = tbeItens.getValueAt(selectedRow, 6).toString();
                String email = tbeItens.getValueAt(selectedRow, 7).toString();
                String addressId = tbeItens.getValueAt(selectedRow, 8).toString();
                String street = tbeItens.getValueAt(selectedRow, 9).toString();

                SwingUtilities.invokeLater(() -> {
                    try {
                        RegistrationView registrationView = new RegistrationView(true, id, type, name, fantasyName, document, ie, contact, email, addressId);
                        registrationView.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                searchData();
                                tbeItens.requestFocusInWindow();
                            }
                        });
                    }catch (Exception ex){
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de registro");
                    }
                });
            } else {
                AppsStyle.showErrorDialog("Nenhum registro selecionado", "Erro de busca");
            }
        });

        btnRemove.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();
            if (selectedRow != -1) {
                String id = tbeItens.getValueAt(selectedRow, 0).toString();
                try {
                    RegistrationController.deleteRegistration(id);
                    searchData();
                    tbeItens.requestFocusInWindow();
                }catch (Exception ex){
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro de registro");
                }
            } else {
                AppsStyle.showErrorDialog("Nenhum registro selecionado", "Erro de busca");
            }
        });
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

    private void mapAction(InputMap im, ActionMap am, String keyStroke, String actionName, Runnable action) {
        im.put(KeyStroke.getKeyStroke(keyStroke), actionName);
        am.put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
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
        data = RegistrationController.searchRegistration(searchOption, searchText);

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
}
