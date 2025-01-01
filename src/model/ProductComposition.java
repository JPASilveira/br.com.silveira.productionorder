package model;

public class ProductComposition {
    private Integer productCompositionId;
    private Product productCompositionParentProduct;
    private Product productCompositionChildProduct;
    private Double productCompositionQuantity;

    public ProductComposition() {}

    public Integer getProductCompositionId() {
        return productCompositionId;
    }

    public void setProductCompositionId(Integer productCompositionId) {
        this.productCompositionId = productCompositionId;
    }

    public Product getProductCompositionParentProduct() {
        return productCompositionParentProduct;
    }

    public void setProductCompositionParentProduct(Product productCompositionParentProduct) {
        this.productCompositionParentProduct = productCompositionParentProduct;
    }

    public Product getProductCompositionChildProduct() {
        return productCompositionChildProduct;
    }

    public void setProductCompositionChildProduct(Product productCompositionChildProduct) {
        this.productCompositionChildProduct = productCompositionChildProduct;
    }

    public Double getProductCompositionQuantity() {
        return productCompositionQuantity;
    }

    public void setProductCompositionQuantityUsed(Double productCompositionQuantityUsed) {
        this.productCompositionQuantity = productCompositionQuantityUsed;
    }
}
