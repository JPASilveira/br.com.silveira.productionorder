package view;

import com.fasterxml.jackson.databind.JsonNode;
import controller.RegistrationAddressController;
import controller.RegistrationController;
import util.BrasilAPI;
import util.CpfCnpjValidator;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
    boolean isUpdate;
    String registrationId;

    public RegistrationView(boolean isUpdate, String registrationId, String registrationType, String registrationName, String registrationFantasyName, String registrationDocument, String registrationIE, String registrationContact, String registrationEmail, String registrationAddress) {
        this.isUpdate = isUpdate;
        this.registrationId = registrationId;
        commonConstructorSetup();

        if (isUpdate) {
            btnSave.setText("(F6) Atualizar");
            lblId.setText("ID: " + registrationId);
            cmbType.setSelectedItem(registrationType);
            txtName.setText(registrationName);
            txtFantasyName.setText(registrationFantasyName);
            txtDocument.setText(registrationDocument);
            txtIe.setText(registrationIE);
            txtContact.setText(registrationContact);
            txtEmail.setText(registrationEmail);
            txtAddress.setText(registrationAddress);
        }

        setVisible(true);
    }

    private void commonConstructorSetup() {
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setTitle("Registro");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth() / 2, resolutionCapture.getHeight() / 2);
        setLocationRelativeTo(null);
        setResizable(false);

        changeTheme();
        disableComponents();
        setupInputMaps();
        configureEnterNavigation(cmbType, txtDocument);

        btnDocument.addActionListener(e -> {
            String document = txtDocument.getText().trim();
            if (!CpfCnpjValidator.isValidCpfOrCnpj(document) || !document.matches("\\d+")) {
                AppsStyle.showErrorDialog("CPF ou CNPJ inválido, o documento deve conter apenas números", "Documento inválido");
            } else {
                if (CpfCnpjValidator.isValidCnpj(document)) {
                    enableCnpjComponents();
                    configureNavigationCnpj();
                    txtIe.setText("0");
                    txtIe.requestFocus();
                    txtIe.selectAll();

                    String originalText = btnDocument.getText();
                    btnDocument.setText("Aguarde ...");
                    btnDocument.setEnabled(false);

                    new Thread(() -> {
                        try {
                            if (ConfirmationView.getViewResult("Buscar dados", "Deseja buscar dados do CNPJ")) {
                                updateCnpjData(document);
                            }
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
                if (CpfCnpjValidator.isValidCpf(document)) {
                    enableCpfComponents();
                    configureNavigationCpf();
                    txtName.requestFocus();
                }
            }
        });

        btnAddress.addActionListener(e -> {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        RegistrationAddressTableView registrationAddressTableView = new RegistrationAddressTableView(txtAddress);
                        registrationAddressTableView.requestFocus();
                    }
                });
            }catch (Exception ex){
                AppsStyle.showErrorDialog(ex.getMessage(), "Erro de registro");
            }
        });

        btnSave.addActionListener(e -> {
            String type = cmbType.getSelectedItem().toString();
            String name = txtName.getText();
            String fantasyName = txtFantasyName.getText();
            String contact = txtContact.getText().trim();
            String document = txtDocument.getText().trim();
            String ie = txtIe.getText().trim();
            String email = txtEmail.getText().trim();
            String address = txtAddress.getText().trim();

            if (isUpdate) {
                try {
                    RegistrationController.updateRegistration(registrationId, type, name, fantasyName, document, ie, contact, email, address);
                    dispose();
                } catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao editar");
                }
            } else {
                try {
                    RegistrationController.addRegistration(type, name, fantasyName, document, ie, contact, email, address);
                    dispose();
                } catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao adicionar");
                }
            }
        });

        btnReturn.addActionListener(e -> dispose());
    }

    private void setupInputMaps() {
        InputMap im = pnlMain.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = pnlMain.getActionMap();

        im.put(KeyStroke.getKeyStroke("F1"), "verifyDocument");
        am.put("verifyDocument", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDocument.doClick();
            }
        });

        im.put(KeyStroke.getKeyStroke("F2"), "searchAddress");
        am.put("searchAddress", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddress.doClick();
            }
        });

        im.put(KeyStroke.getKeyStroke("F6"), "save");
        am.put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSave.doClick();
            }
        });

        im.put(KeyStroke.getKeyStroke("ESCAPE"), "return");
        am.put("return", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnReturn.doClick();
            }
        });
    }

    public void configureNavigationCnpj(){
        configureEnterNavigation(cmbType, txtDocument);
        configureEnterNavigation(txtDocument, txtIe);
        configureEnterNavigation(txtIe, txtName);
        configureEnterNavigation(txtName, txtFantasyName);
        configureEnterNavigation(txtFantasyName, txtContact);
        configureEnterNavigation(txtContact, txtEmail);
        configureEnterNavigation(txtEmail, txtAddress);
        configureEnterNavigation(txtAddress, cmbType);
    }

    public void configureNavigationCpf() {
        configureEnterNavigation(cmbType, txtDocument);
        configureEnterNavigation(txtDocument, txtName);
        configureEnterNavigation(txtName, txtContact);
        configureEnterNavigation(txtContact, txtEmail);
        configureEnterNavigation(txtEmail, txtAddress);
        configureEnterNavigation(txtAddress, cmbType);
    }

    public static void configureEnterNavigation(JComponent textField, JComponent nextField) {
        InputMap im = textField.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = textField.getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        am.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextField.requestFocusInWindow();
            }
        });
    }

    public static void configureEnterNavigation(JComboBox<?> comboBox, JComponent nextField) {
        InputMap im = comboBox.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = comboBox.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        am.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextField.requestFocusInWindow();
            }
        });
    }

    public static void configureEnterNavigation(JTextField textField, JComponent nextField) {
        textField.addActionListener(e -> nextField.requestFocusInWindow());
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
        JsonNode cnpjData;
        String cep, street, number, neighborhood, city, state;
        try {
            cnpjData = BrasilAPI.getCnpjData(cnpj);
        }catch (Exception e) {
            throw new Exception("Erro ao obter dados do CNPJ");
        }
        try {
            txtName.setText(cnpjData.get("razao_social").asText());
        }catch (Exception e) {
            throw new Exception("Erro ao obter nome do CNPJ");
        }
        try {
            txtFantasyName.setText(cnpjData.get("nome_fantasia").asText());
        } catch (Exception e) {
            throw new Exception("Erro ao obter nome fantasia do CNPJ");
        }
        try {
            txtContact.setText(cnpjData.get("ddd_telefone_1").asText());
        }catch (Exception e) {
            throw new Exception("Erro ao obter telefone do CNPJ");
        }
        try {
            txtEmail.setText(cnpjData.get("email").asText());
        }catch (Exception e) {
            throw new Exception("Erro ao obter email do CNPJ");
        }
        if(!isUpdate){
            if(ConfirmationView.getViewResult("Cadastro endereço", "Deseja cadastrar o endereço?")){
                try {
                    cep = cnpjData.get("cep").asText();
                }catch (Exception e) {
                    throw new Exception("Erro ao obter CEP do CNPJ");
                }
                try {
                    street = cnpjData.get("logradouro").asText();
                } catch (Exception e) {
                    throw new Exception("Erro ao obter rua do CNPJ");
                }
                try {
                    number = cnpjData.get("numero").asText();
                    if(!cnpjData.get("complemento").asText().isEmpty()){
                       number += ", " + cnpjData.get("complemento").asText();
                    }
                }catch (Exception e) {
                    throw new Exception("Erro ao obter numero do CNPJ");
                }
                try {
                    neighborhood = cnpjData.get("bairro").asText();
                }catch (Exception e) {
                    throw new Exception("Erro ao obter bairro do CNPJ");
                }
                try {
                    city = cnpjData.get("municipio").asText();
                }catch (Exception e) {
                    throw new Exception("Erro ao obter municipio do CNPJ");
                }
                try {
                    state = BrasilAPI.getStateName(cnpjData.get("uf").asText());
                }catch (Exception e) {
                    throw new Exception("Erro ao obter UF do CNPJ");
                }
                String finalNumber = number;

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        RegistrationAddressView registrationView = new RegistrationAddressView(false, "", cep, street, finalNumber, neighborhood, city, state);
                        registrationView.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent e) {
                                txtAddress.setText(RegistrationAddressController.returnLastIdRegister());
                            }
                        });
                    }
                });
                }
            }
    }
}
