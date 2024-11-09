package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        setSize(resolutionCapture.getWidth(), resolutionCapture.getHeight());
        setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        pnlCenter.setLayout(cardLayout);

        productTableView = new ProductTableView();
        registrationTableView = new RegistrationTableView();
        productionTableView = new ProductionTableView();

        changeTheme();

        pnlCenter.add(pnlEmpty, "Empty");
        pnlCenter.add(productTableView.getPnlMain(), "Product");
        pnlCenter.add(registrationTableView.getPnlMain(), "Registration");
        pnlCenter.add(productionTableView.getPnlMain(), "Production");

        cardLayout.show(pnlCenter, "Empty");

        setVisible(true);

        btnProduct.addActionListener(e -> cardLayout.show(pnlCenter, "Product"));
        btnRegister.addActionListener(e -> cardLayout.show(pnlCenter, "Registration"));
        btnProduction.addActionListener(e -> cardLayout.show(pnlCenter, "Production"));
        btnStart.addActionListener(e -> cardLayout.show(pnlCenter, "Empty"));

        btnTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppsStyle.changeTheme();
                changeTheme();
                pnlMain.revalidate();
                pnlMain.repaint();
            }
        });
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
        AppsStyle.styleButton(btnTheme);
        productTableView.changeTheme();
        registrationTableView.changeTheme();
        productionTableView.changeTheme();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindowView::new);
    }
}
