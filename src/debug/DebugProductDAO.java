package debug;

import model.ProductGroup;
import model.Product;
import model.ProductUnit;
import model.enums.EnumUnit;
import repository.ProductDAO;

public class DebugProductDAO {
    public static void testProduct(String operation){
        ProductUnit productUnit = new ProductUnit();
        productUnit.setUnitName("Kilograms");
        productUnit.setUnitAcronym(EnumUnit.KG);
        productUnit.setUnitId(1);

        ProductGroup productGroup = new ProductGroup();
        productGroup.setGroupName("group");
        productGroup.setGroupId(1);

        Product product = new Product();
        product.setProductId(1);
        product.setProductName("test");
        product.setProductReference("test");
        product.setProductUnit(productUnit);
        product.setProductGroup(productGroup);
        product.setProductPrice(100.00);
        product.setProductQuantity(2.0);
        product.setProductIsComposite(false);

    }

    public static void main(String[] args) {

    }
}
