package Shopping;


import Model.Productions.Product;
import Model.User.*;
import java.time.*;
import java.util.ArrayList;

public class Order {
    private final String id;
    private final Member member;
    private final ArrayList<Product> products;
    private final LocalDateTime created_at;
    private String status;

    // Constructor
    public Order(String id, Member member, ArrayList<Product> products, LocalDateTime created_at, String status) {
        this.id = id;
        this.member = member;
        this.products = products;
        this.created_at = created_at;
        this.status = status;
    }

    // Getters
    public String getId() { return id; }

    public Member getMember() { return member; }

    public ArrayList<Product> getProducts() { return products; }

    public LocalDateTime getCreated_at() { return created_at; }

    public String getStatus() { return status; }

    // Setter
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "main.java.Shopping.Order{" +
                "id='" + id + '\'' +
                ", member=" + member +
                ", products=" + products +
                ", created_at=" + created_at +
                ", status='" + status + '\'' +
                '}';
    }

    // Program Constructor
    /**
     * This Constructor only needs Cart Object to be created
     * main.java.Shopping.Order object should only be created through cart when done shopping
     * @param cart: This main.java.Shopping.Order is created when user are done shopping on that cart
     * @return main.java.Shopping.Order
     */
//    public static Order createOrder(Cart cart){
//        return new Order (cart.getId(), cart.getMember(), cart.getProducts(),
//                LocalDateTime.now(),"Processing");
//    }

    // Finder Methods
    /** Finds a specific order using ID
     * @param targetOrderID: ID of the order that you want to find
     * @param orderDatabase: Where all the information of past Orders are stored
     * @return order
     */
    public static Order findOrder(String targetOrderID, Order[] orderDatabase){
        for (Order order : orderDatabase){
            if (order.getId().equals(targetOrderID)){
                return order;
            }
        }
        return null;
    }
    public static Order findOrder(String targetOrderID, ArrayList<Order> orderDatabase){
        for (Order order : orderDatabase){
            if (order.getId().equals(targetOrderID)){
                return order;
            }
        }
        return null;
    }

    /** Get orders that uniquely belong to a customer
     * @param targetMember: Whose Orders do you want to find?
     * @param orderDatabase: Where the information of Orders are stored
     * @return resultArrayList
     */

    public static ArrayList<Order> findOrdersOfCustomer (Member targetMember, ArrayList<Order> orderDatabase){
        ArrayList<Order> resultArrayList = new ArrayList<>();
        for (Order order : orderDatabase){
            if (order.getMember().equals(targetMember)){
                resultArrayList.add(order);
            }
        }
        return resultArrayList;
    }

    /** Status Manipulation methods
     * Static and Non-Static
     * @param status: Status you want to change to
     * @param order: Specifiy which order are you changing
     */
    public static void changeOrderStatus (String status, Order order){
        order.changeOrderStatus(status);
    }
    public void changeOrderStatus (String status) {
        setStatus(status);
    }
    public String checkOrderStatus (Order order) {
        return order.getStatus();
    }

}

