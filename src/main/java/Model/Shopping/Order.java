package Model.Shopping;

import Model.Products;
import Model.User.Member;

import java.time.*;
import java.util.ArrayList;
;

public class Order {
    private final String id;
    private final Member member ;
    private final ArrayList<Products> products;
    private final LocalDateTime created_at;
    private String status;

    // Constructor
    public Order(String id, Member member, ArrayList<Products> products, LocalDateTime created_at, String status) {
        this.id = id;
        this.member = member;
        this.products = products;
        this.created_at = created_at;
        this.status = status;
    }

    // Getters
    public String getId() { return id; }

    public Member getMember() { return member; }

    public ArrayList<Products> getProducts() { return products; }

    public LocalDateTime getCreated_at() { return created_at; }

    public String getStatus() { return status; }

    // Setter
    public void setStatus(String status) {
        this.status = status;
    }

    // Program methods
    // Program Constructor
    /**
     * This Constructor only needs Cart Object to be created
     * main.java.Model.Shopping.Order object should only be created through cart when done shopping
     * @param cart: This main.java.Model.Shopping.Order is created when user are done shopping on that cart
     * @return main.java.Model.Shopping.Order
     */
    public static Order createOrder(Cart cart){
        return new Order (cart.getId(), cart.getMember(), cart.getProducts(),
                LocalDateTime.now(),"Processing");
    }

    // Finder Methods
    /** Finds a specific order using ID
     * @param targetOrderID: ID of the order that you want to find
     * @param orderDatabase: Where all the information of past Orders are stored
     * @return order
     */
    public static Order findOrder(String targetOrderID, ArrayList<Order> orderDatabase){
        for (Order order : orderDatabase){
            if (order.getId().equals(targetOrderID)){
                return order;
            }
        }
        System.out.println("Order not found");
        return null;
    }

    /** Get orders that uniquely belong to a customer
     * @param targetUser: Whose Orders do you want to find?
     * @param orderDatabase: Where the information of Orders are stored
     * @return resultArrayList
     */
    public static ArrayList<Order> findOrdersOfCustomer (Member targetUser, ArrayList<Order> orderDatabase){
        ArrayList<Order> resultArrayList = new ArrayList<>();
        for (Order order : orderDatabase){
            if (order.getMember().equals(targetUser)){
                resultArrayList.add(order);
            }
        }
        if (resultArrayList.size() > 0) {           // Empty list means there is no alike user
            return resultArrayList;
        } else {
            System.out.println("User not found");
            return null;
        }
    }

    /** Status Manipulation methods
     * Static and Non-Static
     * @param status: Status you want to change to
     * @param order: Specifiy which order are you changing
     */
    public static void changeOrderStatus (Order order, String status){   // For Admin only
        order.changeOrderStatus(status);
    }
    public static String checkOrderStatus (Order order) {
        return order.getStatus();
    }
    // Non-static for direct use
    public void changeOrderStatus (String status) {
        setStatus(status);
    }

    // Display methods

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", member=" + member +
                ", products=" + products +
                ", created_at=" + created_at +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     * Display the information to the console
     * @param user : What user object to display
     * @param orderDatabase : where data is taken from
     */
//    public static void displayOrdersOfCustomer(Member member, ArrayList<Order> orderDatabase){
//        ArrayList<Order> ordersOfCustomer =  findOrdersOfCustomer(member, );
//        if (ordersOfCustomer == null){
//            System.out.println("Orders not found!");
//            return;
//        }
//        for (Order order : ordersOfCustomer){
//            System.out.println(order.toString());
//            System.out.println(" ");
//        }
//    }
    // Static version of toString
    public static void displayOrder(Order order){
        System.out.println(order.toString());
        System.out.println(" ");
    }

    // Data Storing


    public static void main(String[] args) {
        //For testing purposes
    }

}

