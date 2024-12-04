package model;

public class ProductUnit {
    private Integer unitId;
    private String unitName;
    private String unitAcronym;

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
        return unitAcronym;
    }

    public void setUnitAcronym(String unitAcronym) {
        this.unitAcronym =unitAcronym;
    }
}
