package model;

import model.enums.EnumProductionOrder;

import java.util.List;

public class ProductionOrder {
    private Integer productionOrderId;
    private Registration productionOrderRecipient;
    private Product productionOrderProduct;
    private Double productionOrderQuantity;
    private EnumProductionOrder productionOrderStatus;
    private List<ProductComposition> productionOrderCompositions;
    private Double productionOrderCost;

    public ProductionOrder() {}

    public Integer getProductionOrderId() {
        return productionOrderId;
    }

    public void setProductionOrderId(Integer productionOrderId) {
        this.productionOrderId = productionOrderId;
    }

    public Registration getProductionOrderRecipient() {
        return productionOrderRecipient;
    }

    public void setProductionOrderRecipient(Registration productionOrderRecipient) {
        this.productionOrderRecipient = productionOrderRecipient;
    }

    public Product getProductionOrderProduct() {
        return productionOrderProduct;
    }

    public void setProductionOrderProduct(Product productionOrderProduct) {
        this.productionOrderProduct = productionOrderProduct;
    }

    public Double getProductionOrderQuantity() {
        return productionOrderQuantity;
    }

    public void setProductionOrderQuantity(Double productionOrderQuantity) {
        this.productionOrderQuantity = productionOrderQuantity;
    }

    public EnumProductionOrder getProductionOrderStatus() {
        return productionOrderStatus;
    }

    public void setProductionOrderStatus(EnumProductionOrder productionOrderStatus) {
        this.productionOrderStatus = productionOrderStatus;
    }

    public List<ProductComposition> getProductionOrderCompositions() {
        return productionOrderCompositions;
    }

    public void setProductionOrderCompositions(List<ProductComposition> productionOrderCompositions) {
        this.productionOrderCompositions = productionOrderCompositions;
    }

    public Double getProductionOrderCost() {
        return productionOrderCost;
    }

    public void setProductionOrderCost(Double productionOrderCost) {
        this.productionOrderCost = productionOrderCost;
    }
}
