package Model.Productions;

import Model.User.Member;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String id;
    private Member member;
    private List<Product> products;
    private LocalDateTime created_at;
    private Boolean isPaid;
    private Double totalPrice;


    public Order(String id, Member member, List<Product> products, LocalDateTime created_at, Boolean isPaid, Double totalPrice) {
        this.id = id;
        this.member = member;
        this.products = products;
        this.created_at = created_at;
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", member=" + member +
                ", products=" + products +
                ", created_at=" + created_at +
                ", isPaid=" + isPaid +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public String toStringCustom() {
        return "Order{" +
                "\nid='" + id + '\'' +
                ", member=" + member +
                ", \nproducts=" + products.toString() +
                ", \ncreated_at=" + created_at +
                ", totalPrice=" + totalPrice +
                ", isPaid=" + isPaid +
                '}';
    }


    public void calculateTotalPrice() {


    }
}
