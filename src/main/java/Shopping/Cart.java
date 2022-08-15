package Shopping;


import main.java.Model.User.Order;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    public String getId() { return id;}

    public Member getMember() {
        return member;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", member=" + member +
                ", products=" + products +
                '}';
    }

    // Inventory manipulators
    public void addProductIntoCart(Products product) {
        // Adding a product to an array
        products.add(product);
    }
    public void removeProductFromCart(Products targetProduct){
        /*
         * Remove the product object from the cart
         */
        for ( int i = this.getProducts().size() - 1; i >= 0; i--){
            if (this.getProducts().get(i).equals(targetProduct)){
                this.getProducts().remove(i);
            }
        }
    }

    // Program methods

    /**
     * This method purpose is for creating a unique ID each time it is called
     * @return String id
     */
    public static String idUnique(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyMMddHHmmssnn");
        return myDateObj.format(myFormatObj);
    }

    // Program constructor
    /** This constructor only get 1 parameter in order to create a Cart
     * Other values are automatically created and are better being systematic
     * Since these values act as unique feature for each Cart/main.java.Model.User.Order
     * @param member: What customer do this cart belongs?
     * @return Cart
     */
    public static Cart createCart(Member member) {
        return new Cart(idUnique(),member,new ArrayList<Products>());
    }

    public static Order finishCart(Cart cart){          // Innit when done shopping
        return Order.createOrder(cart);
    }


    public static void main(String[] args) {            //For testing purposes


    }
}

