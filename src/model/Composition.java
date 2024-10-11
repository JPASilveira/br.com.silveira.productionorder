package model;

public class Composition {
    private int id;
    private Product parentProduct;
    private Product compositionProduct;
    private double quantityUsed;

    public Composition() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(double quantityUsed) {
        this.quantityUsed = quantityUsed;
    }
}
