package model;

import model.enums.EnumUnit;

public class Unit {
    private int id;
    private String name;
    private EnumUnit unit;

    public Unit() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumUnit getUnit() {
        return unit;
    }

    public void setUnit(EnumUnit unit) {
        this.unit = unit;
    }
}
