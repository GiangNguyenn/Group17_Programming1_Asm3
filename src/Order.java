//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//public class Order {
//    private final String id;
//    private final Member member;
//    private ArrayList<Product> products;
//    private LocalDateTime created_at;
//    private String status;
//
//    public Order(Cart cart) {
//        this.id = cart.getId();
//        this.member = cart.getMember();
//        this.products = cart.getProducts();
//        this.created_at = LocalDateTime.now();
//        this.status = "Proccessing";
//    }
//
//    public Order(String id, Member member, ArrayList<Product> products, LocalDateTime created_at, String status) {
//        this.id = id;
//        this.member = member;
//        this.products = products;
//        this.created_at = created_at;
//        this.status = status;
//    }
//}
//
