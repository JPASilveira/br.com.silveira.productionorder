package debug;

import model.Product;
import repository.ProductDAO;

import java.util.ArrayList;

public class DebugProductDAO {
    public static void main(String[] args) {
        ArrayList<Product> products = ProductDAO.getAllProducts().get();
        for (Product product : products) {
            System.out.println(product.getProductName());
        }
    }
}
