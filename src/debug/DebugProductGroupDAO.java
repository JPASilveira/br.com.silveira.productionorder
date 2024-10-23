package debug;

import model.ProductGroup;
import repository.ProductGroupDAO;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DebugProductGroupDAO {
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
        //testGroup("add");
        try {
            ArrayList<ProductGroup> productGroups = ProductGroupDAO.getAllProductGroups().get();
            ArrayList<ProductGroup> productGroups2 = ProductGroupDAO.getProductGroupByName("test").get();
            ArrayList<ProductGroup> productGroups3 = ProductGroupDAO.getProductGroupById(1).get();

            if(!productGroups.isEmpty()){
                for(ProductGroup productGroup : productGroups2) {
                    System.out.println(productGroup.getGroupName());
                }
            }

            for(ProductGroup productGroup : productGroups2) {
                System.out.println(productGroup.getGroupName());
            }
            for(ProductGroup productGroup : productGroups3) {
                System.out.println(productGroup.getGroupName());
            }
        }catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }
}
