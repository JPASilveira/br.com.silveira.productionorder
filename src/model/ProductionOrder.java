package model;

import model.enums.EnumProductionOrder;

import java.util.List;

public class ProductionOrder {
    private Integer id;
    private Registration recipient;
    private Product product;
    private Double quantity;
    private EnumProductionOrder status;
    private List<Composition> compositions;
    private Double orderCost;

    public ProductionOrder() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
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

    public Double getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(Double orderCost) {
        this.orderCost = orderCost;
    }
}
