package Model.Productions;

import common.BaseHelper;

public class Product {
    String id;
    String productName;
    Double price;
    String category;
    String supplier;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                ", supplier='" + supplier + '\'' +
                '}';
    }

    public Product(String id, String productName, Double price, String category, String supplier) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.supplier = supplier;
    }


    public Product(String productName, Double price, String category, String supplier) {
        this.id = BaseHelper.generateIdForProduction();
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.supplier = supplier;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public Double setPrice(Double price) {
        this.price = price;
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }


}
