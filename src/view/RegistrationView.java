package view;

import com.fasterxml.jackson.databind.JsonNode;
import controller.RegistrationAddressController;
import util.BrasilAPI;
import util.CpfCnpjValidator;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.io.IOException;

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
        disableComponents();

        btnDocument.addActionListener(e -> {
            String document = txtDocument.getText().trim();
            if(!CpfCnpjValidator.isValidCpfOrCnpj(document) || !document.matches("\\d+")) {
                AppsStyle.showErrorDialog("CPF ou CNPJ inválido, o documento deve conter apenas números", "Documento inválido");
            }else {
                if(CpfCnpjValidator.isValidCnpj(document)) {
                    enableCnpjComponents();
                    String originalText = btnDocument.getText();
                    btnDocument.setText("Aguarde ...");
                    btnDocument.setEnabled(false);

                    new Thread(() -> {
                        try {
                            updateCnpjData(document);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            AppsStyle.showErrorDialog(ex.getMessage(), "Erro de CNPJ");
                        } finally {
                            SwingUtilities.invokeLater(() -> {
                                btnDocument.setText(originalText);
                                btnDocument.setEnabled(true);
                            });
                        }
                    }).start();
                }
                if(CpfCnpjValidator.isValidCpf(document)) {
                    enableCpfComponents();
                }
            }
        });

        btnAddress.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new RegistrationAddressTableView(txtAddress);
                }
            });
        });



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

    public void disableComponents(){
        txtIe.setEnabled(false);
        txtName.setEnabled(false);
        txtFantasyName.setEnabled(false);
        txtContact.setEnabled(false);
        txtEmail.setEnabled(false);
        txtAddress.setEnabled(false);
        btnAddress.setEnabled(false);
    }

    private void enableCpfComponents(){
        txtName.setEnabled(true);
        txtContact.setEnabled(true);
        txtEmail.setEnabled(true);
        txtAddress.setEnabled(true);
        btnAddress.setEnabled(true);
    }

    private void enableCnpjComponents(){
        txtIe.setEnabled(true);
        txtName.setEnabled(true);
        txtFantasyName.setEnabled(true);
        txtContact.setEnabled(true);
        txtEmail.setEnabled(true);
        txtAddress.setEnabled(true);
        btnAddress.setEnabled(true);
    }

    private void updateCnpjData(String cnpj) throws Exception {
        try {
            JsonNode cnpjData = BrasilAPI.getCnpjData(cnpj);
            txtName.setText(cnpjData.get("razao_social").asText());
            txtFantasyName.setText(cnpjData.get("nome_fantasia").asText());
            txtContact.setText(cnpjData.get("ddd_telefone_1").asText());
            txtEmail.setText(cnpjData.get("email").asText());

            if(ConfirmationView.getViewResult("Cadastro endereço", "Deseja cadastrar o endereço?")){
                String cep = cnpjData.get("cep").asText();
                String street = cnpjData.get("logradouro").asText();
                String number = cnpjData.get("numero").asText() + ", " + cnpjData.get("complemento").asText();
                String neighborhood = cnpjData.get("bairro").asText();
                String city = cnpjData.get("municipio").asText();
                String state = BrasilAPI.getStateName(cnpjData.get("uf").asText());

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new RegistrationAddressView(false, "", cep, street, number, neighborhood, city, state);
                    }
                });

                txtAddress.setText(RegistrationAddressController.returnLastIdRegister());
            }
        }catch (Exception e){
            throw new Exception("Erro ao buscar CNPJ");
        }
    }
}
