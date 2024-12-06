package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.*;

public class MainWindowView extends JFrame {
    ResolutionCapture resolutionCapture = new ResolutionCapture();
    private final JPanel pnlEmpty = new JPanel();
    private final ProductTableView productTableView;
    private final RegistrationTableView registrationTableView;
    private final ProductionTableView productionTableView;
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
    private JButton btnTheme;

    public MainWindowView() {
        setTitle("Ordem de Produção");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(resolutionCapture.getWidth() - 1, resolutionCapture.getHeight() - 1);
        setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        pnlCenter.setLayout(cardLayout);

        productTableView = new ProductTableView();
        registrationTableView = new RegistrationTableView();
        productionTableView = new ProductionTableView();

        pnlCenter.add(pnlEmpty, "Empty");
        pnlCenter.add(productTableView.getPnlMain(), "Product");
        pnlCenter.add(registrationTableView.getPnlMain(), "Registration");
        pnlCenter.add(productionTableView.getPnlMain(), "Production");
        cardLayout.show(pnlCenter, "Empty");

        btnProduct.addActionListener(e -> {
            productTableView.searchData();
            cardLayout.show(pnlCenter, "Product");
            pnlCenter.revalidate();
            pnlCenter.repaint();
            productTableView.requestFocusOnTable();
        });

        btnRegister.addActionListener(e -> cardLayout.show(pnlCenter, "Registration"));
        btnProduction.addActionListener(e -> cardLayout.show(pnlCenter, "Production"));
        btnStart.addActionListener(e -> cardLayout.show(pnlCenter, "Empty"));

        changeTheme();
        setVisible(true);
    }

    private void changeTheme(){
        AppsStyle.stylePanel(pnlLeft);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlEmpty);
        AppsStyle.styleButton(btnProduct);
        AppsStyle.styleButton(btnRegister);
        AppsStyle.styleButton(btnProduction);
        AppsStyle.styleButton(btnStart);
        AppsStyle.styleButton(btnContact);
    }

    public void reloadProductComponents(){
        productTableView.searchData();
        pnlCenter.revalidate();
        pnlCenter.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindowView::new);
    }
}
