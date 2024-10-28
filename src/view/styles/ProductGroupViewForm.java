package view.styles;

import javax.swing.*;
import java.awt.*;

public abstract class ProductGroupViewForm extends JFrame {
    protected JPanel pnlTitle;
    protected JPanel pnlContent;
    protected JPanel pnlButtons;

    //product group id and title
    protected JLabel lblLabelId;
    protected JLabel lblId;
    protected JLabel lblTitle;

    //product group and name
    protected JLabel lblName;
    protected JTextField txtName;

    //save, delete and return
    protected JButton btnSave;
    protected JButton btnDelete;
    protected JButton btnReturn;

    public ProductGroupViewForm(){
        initializer();
    }

    protected JPanel getPnlTitle(){
        if(pnlTitle == null){
            pnlTitle = new JPanel();
            pnlTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
            pnlTitle.setBackground(AppsStyle.backgroundColor);

            lblLabelId = AppsStyle.defaultTitle("Group ID: ");
            lblId = AppsStyle.defaultTitle("01");

            pnlTitle.add(lblLabelId);
            pnlTitle.add(lblId);
        }
        return pnlTitle;
    }

    protected JPanel getPnlContent(){
        if(pnlContent == null){
            pnlContent = new JPanel();
            pnlContent.setLayout(new FlowLayout(FlowLayout.LEFT));
            pnlContent.setBackground(AppsStyle.backgroundColor);

            lblName = AppsStyle.defaultLabel("Name: ");
            txtName = AppsStyle.defaultTextField();


            pnlContent.add(lblName);
            pnlContent.add(txtName);
        }
        return pnlContent;
    }

    protected JPanel getPnlButtons(){
        if(pnlButtons == null){
            pnlButtons = new JPanel();
            pnlButtons.setBackground(AppsStyle.backgroundColor);

            pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
            btnSave = AppsStyle.defaultButton("Salvar");
            btnDelete = AppsStyle.defaultButton("Excluir");
            btnReturn = AppsStyle.defaultButton("Voltar");

            pnlButtons.add(btnSave);
            pnlButtons.add(btnDelete);
            pnlButtons.add(btnReturn);
        }
        return pnlButtons;

    }

    protected void initializer(){
        this.setTitle("Cadastro de Grupo");
        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        this.add(getPnlTitle(), BorderLayout.NORTH);
        this.add(getPnlContent(), BorderLayout.CENTER);
        this.add(getPnlButtons(), BorderLayout.SOUTH);

    }
}
