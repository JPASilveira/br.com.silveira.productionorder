package model;

public class ProductComposition {
    private Integer id;
    private Product parentProduct;
    private Product compositionProduct;
    private Double quantityUsed;

    public ProductComposition() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getParentProduct() {
        return parentProduct;
    }

    public void setParentProduct(Product parentProductID) {
        this.parentProduct = parentProductID;
    }

    public Product getCompositionProduct() {
        return compositionProduct;
    }

    public void setCompositionProduct(Product compositionProductID) {
        this.compositionProduct = compositionProductID;
    }

    public Double getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Double quantityUsed) {
        this.quantityUsed = quantityUsed;
    }
}
