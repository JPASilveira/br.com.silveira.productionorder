package model;

import model.enums.EnumUnit;

public class Unit {
    private Integer id;
    private String name;
    private EnumUnit unit;

    public Unit() {}

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return this.unit.name();
    }

    public void setUnit(EnumUnit unit) {
        this.unit = unit;
    }
}
