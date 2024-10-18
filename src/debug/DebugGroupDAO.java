package debug;

import model.ProductGroup;
import repository.ProductGroupDAO;

public class DebugGroupDAO {
    public static void testGroup(String operation) {
        ProductGroup productGroup = new ProductGroup();
        productGroup.setGroupId(1);
        productGroup.setGroupName("test2");
        switch (operation) {
            case "add":
                ProductGroupDAO.addGroup(productGroup);
                break;

            case "update":
                ProductGroupDAO.updateGroup(productGroup);
                break;

            case "delete":
                ProductGroupDAO.deleteGroup(productGroup);
                break;
        }
    }

    public static void main(String[] args) {
        testGroup("delete");
    }
}
