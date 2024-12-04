package model;

public class Product {
    private Integer productId;
    private String productReference;
    private String productName;
    private Double productPrice;
    private Double productQuantity;
    private Boolean productIsComposite;
    private ProductUnit productUnit;
    private ProductGroup productGroup;

    public Product() {}

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductReference() {
        if(productReference == null) {
            return "No reference";
        }
        return productReference;
    }

    public void setProductReference(String productReference) {
        this.productReference = productReference;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Double productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Boolean getProductIsComposite() {
        return productIsComposite;
    }

    public void setProductIsComposite(Boolean productIsComposite) {
        this.productIsComposite = productIsComposite;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public ProductUnit getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(ProductUnit productUnit) {
        this.productUnit = productUnit;
    }

    @Override
    public String toString() {
        return String.format("%s", productName);
    }
}
