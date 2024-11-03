package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;

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

    public ProductUnitTableView() {
        setTitle("Busca de Unidade");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);

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

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductUnitTableView::new);
    }

}
