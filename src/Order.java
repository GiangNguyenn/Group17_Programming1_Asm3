package Tutorial.Project;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private final String id;
    private final Member member;
    private ArrayList<Product> products;
    private LocalDateTime created_at;
    private String status;

    public Order(String id, Member member) {
        this.id = id;
        this.member = member;
    }

    public Order(String id, Member member, ArrayList<Product> products, LocalDateTime created_at, String status) {
        this.id = id;
        this.member = member;
        this.products = products;
        this.created_at = created_at;
        this.status = status;
    }
}

