package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;

public class ProductGroupTableView extends JFrame{
    private ResolutionCapture resolutionCapture = new ResolutionCapture();
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

    public ProductGroupTableView() {
        setTitle("Grupo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setContentPane(pnlMain);
        setResizable(false);
        changeTheme();
        setVisible(true);
    }
    
    public void changeTheme(){
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductGroupTableView::new);
    }
}
