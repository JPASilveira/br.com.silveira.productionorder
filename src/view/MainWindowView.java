package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.*;
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
        JPanel pnlEmpty = new JPanel();

        setTitle("Ordem de Produção");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(resolutionCapture.getWidth(), resolutionCapture.getHeight());
        setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        pnlCenter.setLayout(cardLayout);

        AppsStyle.stylePanel(pnlLeft);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlEmpty);
        AppsStyle.styleButton(btnProduct);
        AppsStyle.styleButton(btnRegister);
        AppsStyle.styleButton(btnProduction);
        AppsStyle.styleButton(btnStart);
        AppsStyle.styleButton(btnContact);

        pnlCenter.add(pnlEmpty, "Empty");
        pnlCenter.add(new ProductTableView().getPnlMain(), "Product");
        pnlCenter.add(new RegistrationTableView().getPnlMain(), "Registration");
        pnlCenter.add(new ProductionTableView().getPnlMain(), "Production");

        cardLayout.show(pnlCenter, "Empty");

        setVisible(true);

        btnProduct.addActionListener(e -> cardLayout.show(pnlCenter, "Product"));
        btnRegister.addActionListener(e -> cardLayout.show(pnlCenter, "Registration"));
        btnProduction.addActionListener(e -> cardLayout.show(pnlCenter, "Production"));
        btnStart.addActionListener(e -> cardLayout.show(pnlCenter, "Empty"));
    }

    private void openProductTableView(){
        ProductTableView productTableView = new ProductTableView();
        pnlCenter.add(productTableView.getPnlMain(), "Center");
        pnlCenter.revalidate();
    }

    private void openRegistrationTableView(){
        RegistrationTableView registrationTableView = new RegistrationTableView();
        pnlCenter.add(registrationTableView.getPnlMain(), "Center");
        pnlCenter.revalidate();
    }

    private void openProductionTableView(){
        ProductionTableView productionTableView = new ProductionTableView();
        pnlCenter.add(productionTableView.getPnlMain(), "Center");
        pnlCenter.revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindowView::new);
    }
}
