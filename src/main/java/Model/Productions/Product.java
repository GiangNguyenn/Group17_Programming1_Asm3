package Model.Productions;

import common.BaseHelper;

public class Product {
    String id;
    String productName;
    String price;
    String category;
    String supplier;

    public Product(String id, String productName, String price, String category, String supplier) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.supplier = supplier;
    }

    public Product(String productName, String price, String category, String supplier) {
        this.id = BaseHelper.generateIdForProduction();
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.supplier = supplier;
    }

    public String getId() {
        return id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
