package Model.Shopping;

import Model.Products;
import Model.Shopping.Order;
import Model.User.Member;

import java.util.*;

import static common.BaseHelper.generateUniqueId;


public class Cart {

    private final String id;
    private final Member member;
    private ArrayList<Products> products;

    // Constructor
    public Cart(String id, Member member, ArrayList<Products> products) {
        this.id = id;
        this.member = member;
        this.products = products;
    }

    // Getters
    public String getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }


    // Program methods
    // Inventory manipulators commands
    public void addProductIntoCart(Products product) {
        // Adding a product to the array
        products.add(product);
    }

    public void removeProductFromCart(Products targetProduct) {
        // Iterating through the list to find Product
        for (int i = this.getProducts().size() - 1; i >= 0; i--) {
            if (this.getProducts().get(i).equals(targetProduct)) {
                this.getProducts().remove(i);
            }
        }
    }


    // Program constructor

    /**
     * This constructor only get 1 parameter in order to create a Cart
     * Other values are automatically created and are better being systematic
     * Since these values act as unique feature for each Cart/main.java.Model.Shopping.Order
     *
     * @param member: What customer do this cart belongs?
     * @return Cart
     */
    public static Cart createCart(Member member) {
        return new Cart(generateUniqueId(), member, new ArrayList<Products>());
    }

    public static Order finishCart(Cart cart) {          // Innit when done shopping
        return Order.createOrder(cart);
    }


    // Display methods


    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", member=" + member +
                ", products=" + products +
                '}';
    }

    public static void main(String[] args) {
        //For testing purposes
    }
}
