package view;

import view.styles.AppsStyle;

import javax.swing.*;

public class initialView extends JFrame {
    private JTextPane tpInstruction;
    private JPanel pnlMain;
    private JPanel pnlRight;

    public initialView() {
        setTitle("Initial");
        setContentPane(pnlMain);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();

        AppsStyle.stylePanel(pnlMain);
        AppsStyle.styleTextPanel(tpInstruction);
    }

    public JPanel getPnlMain() {
        return pnlMain;
    }

}
