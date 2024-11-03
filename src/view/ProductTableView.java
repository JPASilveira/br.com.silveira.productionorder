package view;

import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

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
    private JButton btnDelete;
    private JButton btnSearch;

    JTableHeader header = tbeItens.getTableHeader();
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    String[] columnNames = {"ID", "NOME", "PREÇO", "REFERÊNCIA"};
    Object[][] data = {
            {1, "COCA COLA", 10.00, "78766897687"},
            {2, "PEPSI", 8.00, "78766897688"}
    };

    public ProductTableView() {
        setTitle("PRODUTOS");
        setContentPane(pnlMain);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();

        //Styles
        pnlTop.setBackground(AppsStyle.backgroundColor);
        pnlLow.setBackground(AppsStyle.backgroundColor);
        scpCenter.setBackground(AppsStyle.backgroundColor);


        header.setBackground(AppsStyle.backgroundColor);
        header.setForeground(AppsStyle.textColor);
        header.setFont(AppsStyle.boldFont);

        tbeItens.setBackground(AppsStyle.backgroundColor);
        tbeItens.setForeground(AppsStyle.textColor);
        tbeItens.setFont(AppsStyle.regularFont);
        tbeItens.setFillsViewportHeight(true);
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tbeItens.setDefaultRenderer(Object.class, centerRenderer);

        cmbFilter.setBackground(AppsStyle.backgroundColor);
        cmbFilter.setForeground(AppsStyle.textColor);
        cmbFilter.setFont(AppsStyle.regularFont);

        txtSearch.setBackground(AppsStyle.btnColor);
        txtSearch.setForeground(AppsStyle.textColor);
        txtSearch.setFont(AppsStyle.regularFont);

        btnSearch.setBackground(AppsStyle.btnColor);
        btnSearch.setForeground(AppsStyle.textColor);
        btnSearch.setFont(AppsStyle.boldFont);

        btnDelete.setBackground(AppsStyle.btnColor);
        btnDelete.setForeground(AppsStyle.textColor);
        btnDelete.setFont(AppsStyle.boldFont);

        btnEdit.setBackground(AppsStyle.btnColor);
        btnEdit.setForeground(AppsStyle.textColor);
        btnEdit.setFont(AppsStyle.boldFont);

        btnAdd.setBackground(AppsStyle.btnColor);
        btnAdd.setForeground(AppsStyle.textColor);
        btnAdd.setFont(AppsStyle.boldFont);

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tbeItens.setModel(model);
    }

    private void createUIComponents() {
        tbeItens = new JTable(new DefaultTableModel(data, columnNames));
        scpCenter = new JScrollPane(tbeItens);
    }

    public JPanel getPnlMain() {
        return pnlMain;
    }
}
