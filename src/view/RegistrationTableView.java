package view;

import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

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
    private JButton btnDelete;
    private JButton btnSearch;

    String[] columnNames = {"ID", "TIPO", "NOME", "DOCUMENTO"};
    Object[][] data = {
            {1, "COCA COLA", 10.00, "78766897687"},
            {2, "PEPSI", 8.00, "78766897688"}
    };

    public RegistrationTableView() {
        setTitle("PRODUTOS");
        setContentPane(pnlMain);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();



        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tbeItens.setModel(model);
    }

    public void changeTheme(){
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.styleScrollPanel(scpCenter);
        AppsStyle.styleTable(tbeItens);
        AppsStyle.styleComboBox(cmbFilter);
        AppsStyle.styleTextField(txtSearch);
        AppsStyle.styleButton(btnSearch);
        AppsStyle.styleButton(btnDelete);
        AppsStyle.styleButton(btnEdit);
        AppsStyle.styleButton(btnAdd);
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
}
