package view;

import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.net.URI;

public class ContactView extends JFrame {
    private JTextPane txpContact;
    private JPanel pnlMain;
    private JPanel pnlRight;

    public ContactView() {
        setTitle("Contato");
        setContentPane(pnlMain);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        AppsStyle.stylePanel(pnlMain);
        pack();

        txpContact.setEditable(false);
        txpContact.setContentType("text/html");

        txpContact.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                                desktop.browse(new URI(e.getURL().toString()));
                            } else {
                                openUrlWithXdgOpen(e);
                            }
                        } else {
                            openUrlWithXdgOpen(e);
                        }
                    } catch (Exception ex) {
                        openUrlWithXdgOpen(e);
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }


    public JPanel getPnlMain() {
        return pnlMain;
    }

    private void openUrlWithXdgOpen(HyperlinkEvent e) {
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            try {
                String command = "xdg-open " + e.getURL().toString();
                Runtime.getRuntime().exec(command);
            } catch (Exception ex) {
                AppsStyle.showErrorDialog("Ação de navegação não suportada neste ambiente.","Erro ao abrir navegador");
            }
        } else {
            AppsStyle.showErrorDialog("Ação de navegação não suportada neste ambiente.","Erro ao abrir navegador");
        }
    }
}
