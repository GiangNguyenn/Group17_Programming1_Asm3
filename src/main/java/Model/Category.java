package main.java.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class responsible for array of category
 */

public class Category {
    // private cannot be accessed from outside the product class
    private String type_id;
    private String categoryName;

    public Category(String id, String name) {
        this.type_id = id;
        this.categoryName = name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static void addCategory(String id, String name) {
        List<Category> categoryList = new ArrayList<>();
        Category new_category = new Category(id, name);
        categoryList.add(new_category);
    }

    public static void main(String[] args) {
        addCategory("Tech", "Technology");
        addCategory("Tech2", "Technical Accessories");
        addCategory("Fashion", "Clothing");
        addCategory("Fashion5", "Shoes");
    }
}
