package view;

import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;

public class RegistrationView extends JFrame {
    private JPanel pnlMain;
    private JComboBox cmbType;
    private JTextField txtDocument;
    private JTextField txtIe;
    private JTextField txtName;
    private JTextField txtFantasyName;
    private JTextField txtContact;
    private JTextField txtEmail;
    private JTextField txtAddress;
    private JButton btnDocument;
    private JButton btnSave;
    private JButton btnReturn;
    private JButton btnAddress;
    private JPanel pnlTop;
    private JPanel pnlCenter;
    private JPanel pnlCenterOne;
    private JPanel pnlCenterTwo;
    private JPanel pnlCenterThree;
    private JPanel pnlCenterFour;
    private JPanel pnlCenterFive;
    private JPanel pnlCenterSix;
    private JPanel pnlCenterSeven;
    private JPanel pnlCenterEight;
    private JPanel pnlBottom;
    private JLabel lblId;
    private JLabel lblType;
    private JLabel lblDocument;
    private JLabel lblIe;
    private JLabel lblName;
    private JLabel lblFantasyName;
    private JLabel lblContact;
    private JLabel lblEmail;
    private JLabel lblAddress;

    public RegistrationView() {
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setTitle("Registro");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth()/2, resolutionCapture.getHeight()/2);
        setLocationRelativeTo(null);
        setResizable(false);
        changeTheme();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RegistrationView();
            }
        });
    }

    public void changeTheme(){
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlCenterOne);
        AppsStyle.stylePanel(pnlCenterTwo);
        AppsStyle.stylePanel(pnlCenterThree);
        AppsStyle.stylePanel(pnlCenterFour);
        AppsStyle.stylePanel(pnlCenterFive);
        AppsStyle.stylePanel(pnlCenterSix);
        AppsStyle.stylePanel(pnlCenterSeven);
        AppsStyle.stylePanel(pnlCenterEight);
        AppsStyle.stylePanel(pnlBottom);
        AppsStyle.styleComboBox(cmbType);
        AppsStyle.styleLabelBold(lblId);
        AppsStyle.styleLabel(lblType);
        AppsStyle.styleLabel(lblDocument);
        AppsStyle.styleLabel(lblIe);
        AppsStyle.styleLabel(lblName);
        AppsStyle.styleLabel(lblFantasyName);
        AppsStyle.styleLabel(lblContact);
        AppsStyle.styleLabel(lblEmail);
        AppsStyle.styleLabel(lblAddress);
        AppsStyle.styleTextField(txtDocument);
        AppsStyle.styleTextField(txtIe);
        AppsStyle.styleTextField(txtName);
        AppsStyle.styleTextField(txtFantasyName);
        AppsStyle.styleTextField(txtContact);
        AppsStyle.styleTextField(txtEmail);
        AppsStyle.styleTextField(txtAddress);
        AppsStyle.styleButton(btnDocument);
        AppsStyle.styleButton(btnSave);
        AppsStyle.styleButton(btnReturn);
        AppsStyle.styleButton(btnAddress);
    }
}
