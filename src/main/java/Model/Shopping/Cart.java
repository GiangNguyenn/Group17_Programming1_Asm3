//package main.java.Model.Shopping;
//
//
//import java.util.*;
//import main.java.Model.User.*;
//public class Cart {
//
//    private final String id;
//    private final Member member;
//    private ArrayList<Products> products;
//
//    // Constructor
//    public Cart(String id, Member member, ArrayList<Products> products) {
//        this.id = id;
//        this.member = member;
//        this.products = products;
//    }
//
//    // Getters
//    public String getId() { return id;}
//
//    public Member getMember() {
//        return member;
//    }
//
//    public ArrayList<Products> getProducts() {
//        return products;
//    }
//
//
//    // Program methods
//    // Inventory manipulators commands
//    public void addProductIntoCart(Products product) {
//        // Adding a product to the array
//        products.add(product);
//    }
//    public void removeProductFromCart(Products targetProduct){
//        // Iterating through the list to find Product
//        for ( int i = this.getProducts().size() - 1; i >= 0; i--){
//            if (this.getProducts().get(i).equals(targetProduct)){
//               this.getProducts().remove(i);
//            }
//        }
//    }
//    /**
//     * This method purpose is for creating a unique ID each time it is called
//     * @return String id
//     */
//    public static String idUnique(){
//        ArrayList<Integer> userArray = Arrays.stream(userDatabase)
//                .map(user -> Integer.valueOf(member.getID())).toList();
//        Integer max = Collections.max(userArray);
//        return String.valueOf((max+1));
//    }
//
//    // Program constructor
//    /** This constructor only get 1 parameter in order to create a Cart
//     * Other values are automatically created and are better being systematic
//     * Since these values act as unique feature for each Cart/main.java.Model.Shopping.Order
//     * @param member: What customer do this cart belongs?
//     * @return Cart
//     */
//    public static Cart createCart(Member member) {
//        return new Cart(idUnique(),member,new ArrayList<Products>());
//    }
//
//    public static Order finishCart(Cart cart){          // Innit when done shopping
//        return Order.createOrder(cart);
//    }
//
//
//
//    // Display methods
//
//
//    @Override
//    public String toString() {
//        return "Cart{" +
//                "id='" + id + '\'' +
//                ", member=" + member +
//                ", products=" + products +
//                '}';
//    }
//
//    public static void main(String[] args) {
//        //For testing purposes
//    }
//}
