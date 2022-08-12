package order.management.system;

/**
 * This class responsible for product information and manipulation
 * Can be added to cart
 */

public class Product {
    // private cannot be accessed from outside the product class  extends Category
    private int id;
    private String name;
    private String image;
    private String description;

    private String product_type;
    private double price;

    public Product(int id, String name, String image, String description, String type_id, double price) {
        // 'id' from the Product class is equal to the 'id' from the argument
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.product_type = type_id;
        this.price = price;
    }

    public int getProductId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public double getProductPrice() {
        return this.price;
    }

    public void updatePrice(double price) {
        this.price = price;
    }
}
