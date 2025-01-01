package model;

import java.util.ArrayList;

public class ProductionProductSeparation {
    private final ArrayList<ProductComposition> basicProducts;
    private final ArrayList<ProductComposition> compositeProducts;

    public ProductionProductSeparation() {
        this.basicProducts = new ArrayList<>();
        this.compositeProducts = new ArrayList<>();
    }

    public ArrayList<ProductComposition> getBasicProducts() {
        return basicProducts;
    }

    public ArrayList<ProductComposition> getCompositeProducts() {
        return compositeProducts;
    }
}
