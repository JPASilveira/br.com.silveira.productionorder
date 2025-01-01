package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConfirmationView extends JFrame {
    private JPanel pnlMain;
    private JButton btnTrue;
    private JButton btnFalse;
    private JPanel pnlCenter;
    private JPanel pnlBottom;
    private JLabel lblMessage;

    private boolean viewResult;

    private ConfirmationView(String title, String message) {
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setTitle(title);
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth() / 5, resolutionCapture.getHeight() / 5);
        setLocationRelativeTo(null);
        setResizable(false);
        changeTheme(message);

        //Ação do botão sim
        btnTrue.addActionListener(e -> {
            viewResult = true;
            dispose();
        });

        //Atalho para sim (F6)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F6"), "resultTrue");
        pnlMain.getActionMap().put("resultTrue", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnTrue.doClick();
            }
        });

        //Ação do botão não
        btnFalse.addActionListener(e -> {
            viewResult = false;
            dispose();
        });

        //Atalho para não (ESC)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "returnFalse");
        pnlMain.getActionMap().put("returnFalse", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnFalse.doClick();
            }
        });
    }

    public void changeTheme(String message) {
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlBottom);
        lblMessage.setText(message);
        AppsStyle.styleLabelBold(lblMessage);
        AppsStyle.styleButton(btnTrue);
        AppsStyle.styleButton(btnFalse);
    }

    public static boolean getViewResult(String title, String message) {
        final boolean[] result = new boolean[1];
        final Object lock = new Object();

        SwingUtilities.invokeLater(() -> {
            ConfirmationView confirmationView = new ConfirmationView(title, message);
            confirmationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    synchronized (lock) {
                        result[0] = confirmationView.viewResult;
                        lock.notify();
                    }
                }
            });
            confirmationView.setVisible(true);
        });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return result[0];
    }
}
