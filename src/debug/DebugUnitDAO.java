package debug;

import model.ProductUnit;
import model.enums.EnumUnit;
import repository.ProductUnitDAO;

public class DebugUnitDAO {
    public static void testUnit(String operation){
        ProductUnit productUnit = new ProductUnit();
        productUnit.setUnitId(1);
        productUnit.setUnitName("Kilogram");
        productUnit.setUnitAcronym(EnumUnit.KG);

    }
    }
