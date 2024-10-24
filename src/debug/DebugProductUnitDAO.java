package debug;

import model.ProductUnit;
import model.enums.EnumUnit;
import repository.ProductUnitDAO;

public class DebugProductUnitDAO {
    public static void testUnit(String operation){
        ProductUnit productUnit = new ProductUnit();
        productUnit.setUnitId(1);
        productUnit.setUnitName("Kilogram2");
        productUnit.setUnitAcronym("KG");

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
        testUnit("delete");
    }
}
