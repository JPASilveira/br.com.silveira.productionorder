package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductGroupView extends JFrame {
    private JPanel pnlProductGroup;

    public JPanel getPnlProductGroup() {
        return pnlProductGroup;
    }

    private JPanel pnlTitle;
    private JLabel lblId;
    private JPanel pnlBaseboard;
    private JButton btnSave;
    private JButton btnReturn;
    private JPanel pnlContent;
    private JPanel pnlLeft;
    private JPanel pnlLeftOne;
    private JLabel lblName;
    private JTextField txtName;
    private JPanel pnlLeftTwo;
    private JPanel pnlLeftThree;
    private JPanel pnlLeftFour;
    private JPanel pnlLeftFive;
    private JPanel pnlRight;
    private JPanel pnlRightOne;
    private JPanel pnlRightTwo;
    private JPanel pnlRightThree;
    private JPanel pnlRightFour;
    private JPanel pnlRightFive;

    public ProductGroupView() {
        // Configura e exibe a janela principal da interface
        JFrame frame = new JFrame("Product Group View");
        frame.setContentPane(pnlProductGroup);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("aaa");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductGroupView::new);
    }
}
