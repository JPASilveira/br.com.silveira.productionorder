package view;

import com.fasterxml.jackson.databind.JsonNode;
import controller.RegistrationAddressController;
import util.ResolutionCapture;
import util.ViaCep;
import view.styles.AppsStyle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class RegistrationAddressView extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlTop;
    private JPanel pnlCenter;
    private JPanel pnlLow;
    private JTextField txtStreet;
    private JTextField txtNeighborhood;
    private JTextField txtCity;
    private JTextField txtNumber;
    private JComboBox cmbState;
    private JPanel pnlCenterOne;
    private JPanel pnlCenterTwo;
    private JPanel pnlCenterFour;
    private JPanel pnlCenterFive;
    private JButton btnSave;
    private JButton btnReturn;
    private JLabel lblState;
    private JLabel lblCity;
    private JLabel lblNeighborhood;
    private JLabel lblStreet;
    private JLabel lblNumber;
    private JLabel lblId;
    private JTextField txtCep;
    private JButton btnCep;
    private JLabel lblCep;
    private JPanel pnlCenterThree;
    private JPanel pnlCenterSix;

    public RegistrationAddressView(Boolean isUpdate, String idAddress, String cepAddress, String streetAddress, String numberAddress, String neighborhoodAddress, String cityAddress, String stateAddress) {
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setTitle("Endereço");
        setContentPane(pnlMain);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(resolutionCapture.getWidth() / 2, resolutionCapture.getHeight() / 2);
        setLocationRelativeTo(null);
        setResizable(false);
        changeTheme();


        lblId.setText("ID: " + idAddress);
        txtCep.setText(cepAddress);
        txtStreet.setText(streetAddress);
        txtNumber.setText(numberAddress);
        txtNeighborhood.setText(neighborhoodAddress);
        txtCity.setText(cityAddress);
        cmbState.setSelectedItem(stateAddress);

        if (isUpdate) {
            btnSave.setText("(F6) Atualizar");
        }

        changeTheme();

        //Ação do botão de buscar cep
        btnCep.addActionListener(e -> {
            String cepText = txtCep.getText().trim();

            if (!cepText.matches("\\d+")) {
                AppsStyle.showErrorDialog("O CEP deve ser formado apenas de números", "CEP inválido");
            } else {
                String originalText = btnCep.getText();
                btnCep.setText("Aguarde ...");
                btnCep.setEnabled(false);

                new Thread(() -> {
                    try {
                        updateAddressByCep(cepText, txtStreet, txtNeighborhood, txtCity, cmbState);
                    } catch (Exception ex) {
                        AppsStyle.showErrorDialog(ex.getMessage(), "Erro de CEP");
                    } finally {
                        SwingUtilities.invokeLater(() -> {
                            btnCep.setText(originalText);
                            btnCep.setEnabled(true);
                            cmbState.updateUI();
                            txtNumber.requestFocus();
                        });
                    }
                }).start();
            }
        });

        //Atalho para buscar CEP (F1)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F1"), "cep");
        pnlMain.getActionMap().put("cep", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCep.doClick();
            }
        });

        //Ação do botão de salvar
        btnSave.addActionListener(e -> {
            String cep = txtCep.getText().trim();
            String street = txtStreet.getText();
            String number = txtNumber.getText();
            String neighborhood = txtNeighborhood.getText();
            String city = txtCity.getText();
            String state = cmbState.getSelectedItem().toString();

            if (isUpdate) {
                try {
                    RegistrationAddressController.updateRegistrationAddress(idAddress, cep, street, number, neighborhood, city, state);
                } catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao atualizar endereço");
                }
            } else {
                try {
                    RegistrationAddressController.addRegistrationAddress(cep, street, number, neighborhood, city, state);
                    dispose();
                } catch (Exception ex) {
                    AppsStyle.showErrorDialog(ex.getMessage(), "Erro ao adicionar endereço");
                }
            }
        });

        // Atalho para salvar (F6)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F6"), "save");
        pnlMain.getActionMap().put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSave.doClick();
            }
        });

        //Ação do botão de retornar
        btnReturn.addActionListener(e -> {
            dispose();
        });

        // Atalho para retornar (ESC)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "return");
        pnlMain.getActionMap().put("return", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnReturn.doClick();
            }
        });


        configureEnterNavigation(txtCep, txtStreet);
        configureEnterNavigation(txtStreet, txtNumber);
        configureEnterNavigation(txtNumber, txtNeighborhood);
        configureEnterNavigation(txtNeighborhood, txtCity);
        configureEnterNavigation(txtCity, txtCep);
        setVisible(true);
    }

    public void changeTheme() {
        AppsStyle.stylePanel(pnlTop);
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlCenter);
        AppsStyle.stylePanel(pnlCenterOne);
        AppsStyle.stylePanel(pnlCenterTwo);
        AppsStyle.stylePanel(pnlCenterThree);
        AppsStyle.stylePanel(pnlCenterFour);
        AppsStyle.stylePanel(pnlCenterFive);
        AppsStyle.stylePanel(pnlCenterSix);
        AppsStyle.stylePanel(pnlLow);
        AppsStyle.styleLabel(lblCep);
        AppsStyle.styleLabel(lblStreet);
        AppsStyle.styleLabel(lblNumber);
        AppsStyle.styleLabel(lblNeighborhood);
        AppsStyle.styleLabel(lblCity);
        AppsStyle.styleLabel(lblState);
        AppsStyle.styleTextField(txtCep);
        AppsStyle.styleTextField(txtStreet);
        AppsStyle.styleTextField(txtNumber);
        AppsStyle.styleTextField(txtNeighborhood);
        AppsStyle.styleTextField(txtCity);
        AppsStyle.styleComboBox(cmbState);
        AppsStyle.styleButton(btnCep);
        AppsStyle.styleButton(btnSave);
        AppsStyle.styleButton(btnReturn);
    }

    private void updateAddressByCep(String cep, JTextField txtStreet, JTextField txtNeighborhood, JTextField txtCity, JComboBox cmbState) throws IOException {
        try {
            JsonNode cepData = ViaCep.getAddress(cep);
            txtStreet.setText(cepData.get("logradouro").asText());
            txtNeighborhood.setText(cepData.get("bairro").asText());
            txtCity.setText(cepData.get("localidade").asText());
            cmbState.setSelectedItem(cepData.get("estado").asText().toUpperCase());
        } catch (Exception e) {
            throw new IOException("Erro ao obter endereço");
        }

    }

    private void configureEnterNavigation(JTextField textField, JTextField nextField) {
        textField.addActionListener(e -> nextField.requestFocusInWindow());
    }
}
