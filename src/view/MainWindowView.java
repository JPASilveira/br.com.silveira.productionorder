package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindowView extends JFrame {
    ResolutionCapture resolutionCapture = new ResolutionCapture();
    private final ProductTableView productTableView;
    private final RegistrationTableView registrationTableView;
    private final ProductionTableView productionTableView;
    private final initialView inicialView;
    private final ContactView contactView;
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
        inicialView = new initialView();
        contactView = new ContactView();

        pnlCenter.add(productTableView.getPnlMain(), "Product");
        pnlCenter.add(registrationTableView.getPnlMain(), "Registration");
        pnlCenter.add(productionTableView.getPnlMain(), "Production");
        pnlCenter.add(inicialView.getPnlMain(),"Initial");
        pnlCenter.add(contactView.getPnlMain(), "Contact");

        cardLayout.show(pnlCenter, "Initial");

        btnProduct.addActionListener(e -> {
            productTableView.searchData();
            cardLayout.show(pnlCenter, "Product");
            pnlCenter.revalidate();
            pnlCenter.repaint();
            productTableView.requestFocusOnTable();
        });

        btnRegister.addActionListener(e -> {
            registrationTableView.searchData();
            cardLayout.show(pnlCenter, "Registration");
            pnlCenter.revalidate();
            pnlCenter.repaint();
            registrationTableView.requestFocusOnTable();
        });

        btnProduction.addActionListener(e -> {
            productTableView.searchData();
            cardLayout.show(pnlCenter, "Production");
            pnlCenter.revalidate();
            pnlCenter.repaint();
            productionTableView.requestFocusOnTable();
        });

        btnStart.addActionListener(e -> cardLayout.show(pnlCenter, "Initial"));
        btnContact.addActionListener(e -> cardLayout.show(pnlCenter, "Contact"));

        changeTheme();
        setupGlobalShortcuts();

        SwingUtilities.invokeLater(() -> {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        });

        setVisible(true);
    }

    private void changeTheme(){
        AppsStyle.stylePanel(pnlLeft);
        AppsStyle.stylePanel(pnlCenter);
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

    private void setupGlobalShortcuts() {
        InputMap im = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getRootPane().getActionMap();

        mapAction(im, am, "alt P", "product", () -> btnProduct.doClick());
        mapAction(im, am, "alt R", "register", () -> btnRegister.doClick());
        mapAction(im, am, "alt O", "production", () -> btnProduction.doClick());
        mapAction(im, am, "alt C", "contact", () -> btnContact.doClick());
        mapAction(im, am, "alt I", "start", () -> btnStart.doClick());
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
}

