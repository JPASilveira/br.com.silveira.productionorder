package debug;

import model.Unit;
import model.enums.EnumUnit;
import repository.UnitDAO;

public class DebugUnitDAO {
    public static void main(String[] args) {
        Unit unit = new Unit();
        unit.setName("KILO");
        unit.setUnit(EnumUnit.KG);
        UnitDAO.addUnit(unit);
    }
}
