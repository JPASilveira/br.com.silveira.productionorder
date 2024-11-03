package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindowView extends JFrame {
    ResolutionCapture resolutionCapture = new ResolutionCapture();

    private JPanel pnlMain;
    private JButton btnRegister;
    private JButton btnProduction;
    private JButton btnProduct;
    private JPanel pnlLeftTop;
    private JPanel pnlLeft;
    private JButton btnContact;
    private JPanel pnlLeftLow;
    private JPanel pnlCenter;
    private JButton btnStart;

    public MainWindowView() {
        setTitle("Ordem de Produção");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(resolutionCapture.getWidth(), resolutionCapture.getHeight());
        setLocationRelativeTo(null);

        //Styles
        pnlLeft.setBackground(AppsStyle.backgroundColor);
        pnlCenter.setBackground(AppsStyle.backgroundColor);

        btnProduct.setBackground(AppsStyle.btnColor);
        btnProduct.setForeground(AppsStyle.textColor);
        btnProduct.setFont(AppsStyle.boldFont);

        btnRegister.setBackground(AppsStyle.btnColor);
        btnRegister.setForeground(AppsStyle.textColor);
        btnRegister.setFont(AppsStyle.boldFont);

        btnProduction.setBackground(AppsStyle.btnColor);
        btnProduction.setForeground(AppsStyle.textColor);
        btnProduct.setFont(AppsStyle.boldFont);

        btnStart.setBackground(AppsStyle.btnColor);
        btnStart.setForeground(AppsStyle.textColor);
        btnStart.setFont(AppsStyle.boldFont);

        btnContact.setBackground(AppsStyle.btnColor);
        btnContact.setForeground(AppsStyle.textColor);
        btnContact.setFont(AppsStyle.boldFont);

        setVisible(true);

        btnProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlCenter.removeAll();
                openProductGroupView();
            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlCenter.removeAll();
                pnlCenter.repaint();
            }
        });
    }

    private void openProductGroupView(){
        ProductTableView productTableView = new ProductTableView();
        pnlCenter.add(productTableView.getPnlMain(), "Center");
        pnlCenter.revalidate();
        pnlCenter.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindowView::new);
    }
}
