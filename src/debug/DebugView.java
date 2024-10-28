package debug;

import view.styles.ProductGroupView;
import view.styles.ProductGroupViewForm;

import javax.swing.*;

public class DebugView {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                ProductGroupViewForm p = new ProductGroupView();
                p.setVisible(true);
        });
    }
}
