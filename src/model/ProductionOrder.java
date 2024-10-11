package model;

import model.enums.EnumProductionOrder;

import java.util.List;

public class ProductionOrder {
    private int id;
    private Registration recipient;
    private Product product;
    private double quantity;
    private EnumProductionOrder status;
    private List<Composition> compositions;
    private double orderCost;

    public ProductionOrder() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Registration getRecipient() {
        return recipient;
    }

    public void setRecipient(Registration recipient) {
        this.recipient = recipient;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public EnumProductionOrder getStatus() {
        return status;
    }

    public void setStatus(EnumProductionOrder status) {
        this.status = status;
    }

    public List<Composition> getCompositions() {
        return compositions;
    }

    public void setCompositions(List<Composition> compositions) {
        this.compositions = compositions;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(double orderCost) {
        this.orderCost = orderCost;
    }
}
