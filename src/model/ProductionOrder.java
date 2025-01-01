package model;

import java.util.ArrayList;

public class ProductionOrder {
    private Integer productionOrderId;
    private Registration productionOrderRecipient;
    private Product productionOrderProduct;
    private Double productionOrderQuantity;
    private String productionOrderStatus;
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

    public String getProductionOrderStatus() {
        return productionOrderStatus;
    }

    public void setProductionOrderStatus(String productionOrderStatus) {
        this.productionOrderStatus = productionOrderStatus;
    }

    public Double getProductionOrderCost() {
        return productionOrderCost;
    }

    public void setProductionOrderCost(Double productionOrderCost) {
        this.productionOrderCost = productionOrderCost;
    }
}
