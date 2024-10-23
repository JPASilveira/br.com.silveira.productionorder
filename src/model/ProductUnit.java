package model;

import model.enums.EnumUnit;

public class ProductUnit {
    private Integer unitId;
    private String unitName;
    private EnumUnit unitAcronym;

    public ProductUnit() {}

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitAcronym() {
        if (unitAcronym == null) {
            return null;
        }
        return unitAcronym.name();
    }

    public void setUnitAcronym(String unitAcronym) {
        this.unitAcronym = EnumUnit.valueOf(unitAcronym);
    }
}
