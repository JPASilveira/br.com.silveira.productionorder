package model;

public class Composition {
    private Integer id;
    private Product parentProduct;
    private Product compositionProduct;
    private Double quantityUsed;

    public Composition() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getParentProduct() {
        return parentProduct;
    }

    public void setParentProduct(Product parentProduct) {
        this.parentProduct = parentProduct;
    }

    public Product getCompositionProduct() {
        return compositionProduct;
    }

    public void setCompositionProduct(Product compositionProduct) {
        this.compositionProduct = compositionProduct;
    }

    public Double getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Double quantityUsed) {
        this.quantityUsed = quantityUsed;
    }
}
