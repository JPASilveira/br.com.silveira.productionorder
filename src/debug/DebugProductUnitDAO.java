package debug;

import controller.ProductUnitController;
import model.ProductUnit;
import repository.ProductUnitDAO;

public class DebugProductUnitDAO {
    public static void testUnit(String operation){
        ProductUnit productUnit = new ProductUnit();
        productUnit.setUnitId(2);
        productUnit.setUnitName("Kt");
        productUnit.setUnitAcronym("kt");

        switch(operation){
            case "add":
                ProductUnitDAO.addUnit(productUnit);
                break;

            case "update":
                ProductUnitDAO.updateUnit(productUnit);
                break;

            case "delete":
                ProductUnitDAO.deleteUnit(productUnit);
                break;
        }
    }

    public static void main(String[] args) {
        ProductUnitController.updateProductUnit("2", "ll", "ll");

    }
}
