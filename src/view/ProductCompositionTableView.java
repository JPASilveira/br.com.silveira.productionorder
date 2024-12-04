package view;

import controller.ProductCompositionController;
import controller.ProductController;
import controller.ProductGroupController;
import model.Product;
import model.ProductComposition;
import util.ResolutionCapture;
import view.styles.AppsStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProductCompositionTableView extends JFrame {
    private JPanel pnlMain;
    private JTable tbeItens;
    private JScrollPane scpCenter;
    private JButton btnRemove;
    private JButton btnEdit;
    private JButton btnAdd;
    private JPanel pnlButton;
    private JButton btnReturn;
    private JPanel pnlButtonLeft;
    private JPanel pnlButtonRight;
    String[] columnNames = {"ID", "PRODUTO", "SUBPRODUTO", "QUANTIDADE"};
    Object[][] data;

    public ProductCompositionTableView(String parentProductId) {
        setTitle("Subprodutos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        setSize(resolutionCapture.getWidth() / 2, resolutionCapture.getHeight() / 2);
        setLocationRelativeTo(null);
        setContentPane(pnlMain);
        setResizable(false);
        changeTheme();;

        //Ação do botão adicionar
        btnAdd.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ProductCompositionView productCompositionView = new ProductCompositionView(false, "", parentProductId, "", "");
                    productCompositionView.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            searchData();
                            tbeItens.requestFocus();
                        }
                    });
                }
            });
        });

        // Atalho para Adcionar (F1)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F1"), "add");
        pnlMain.getActionMap().put("add", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAdd.doClick();
            }
        });

        //Ação do botão editar
        btnEdit.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();

            if (selectedRow != -1) {
                String compositionId = tbeItens.getModel().getValueAt(selectedRow, 0).toString();
                Product parentProduct = (Product) tbeItens.getModel().getValueAt(selectedRow,1);
                Product childProduct = (Product) tbeItens.getModel().getValueAt(selectedRow,2);
                String quantity = tbeItens.getModel().getValueAt(selectedRow,3).toString();

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ProductCompositionView productCompositionView = new ProductCompositionView(true, compositionId, parentProduct.getProductId().toString(), childProduct.getProductId().toString(), quantity);
                        productCompositionView.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                searchData();
                                tbeItens.requestFocus();
                            }
                        });
                    }
                });
            }
        });

        // Atalho para Editar (F2)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F2"), "edit");
        pnlMain.getActionMap().put("edit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEdit.doClick();
            }
        });

        //Ação do botão remover
        btnRemove.addActionListener(e -> {
            int selectedRow = tbeItens.getSelectedRow();
            if (selectedRow != -1) {
                String compositionId = tbeItens.getModel().getValueAt(selectedRow, 0).toString();

                ProductCompositionController.deleteProductComposition(compositionId);
                searchData();
                tbeItens.requestFocus();
            }
        });

        // Atalho para remover (F3)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F3"), "remove");
        pnlMain.getActionMap().put("remove", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRemove.doClick();
            }
        });

        //Ação do botão retornar
        btnReturn.addActionListener(e -> {
           dispose();
        });

        // Atalho para Retornar (Esc)
        pnlMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "return");
        pnlMain.getActionMap().put("return", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnReturn.doClick();
            }
        });

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tbeItens.setModel(model);

        searchData();
        tbeItens.requestFocus();
        setVisible(true);
    }


    private void updateTable(Object[][] tableData) {
        if (tbeItens == null) {
            return;
        }

        DefaultTableModel model = new DefaultTableModel(tableData, columnNames);

        tbeItens.setModel(model);
        tbeItens.setDefaultEditor(Object.class, null);
        tbeItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void searchData(){
        data = ProductCompositionController.searchProductComposition();

        updateTable(data);
    }

    public void changeTheme(){
        AppsStyle.stylePanel(pnlMain);
        AppsStyle.stylePanel(pnlButton);
        AppsStyle.stylePanel(pnlButtonLeft);
        AppsStyle.stylePanel(pnlButtonRight);
        AppsStyle.styleScrollPanel(scpCenter);
        AppsStyle.styleTable(tbeItens);
        AppsStyle.styleButton(btnAdd);
        AppsStyle.styleButton(btnEdit);
        AppsStyle.styleButton(btnRemove);
        AppsStyle.styleButton(btnReturn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProductCompositionTableView("");
            }
        });
    }
}
