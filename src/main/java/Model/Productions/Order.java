package Model.Productions;

import common.BaseHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private String memberID;
    private List<String> productsID;
    private LocalDateTime created_at;
    private Boolean isPaid;
    private Double totalPrice;


    public Order(String id, String memberID, List<String> productsID, LocalDateTime created_at, Boolean isPaid, Double totalPrice) {
        this.id = id;
        this.memberID = memberID;
        this.productsID = productsID;
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

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public List<String> getProductsID() {
        return productsID;
    }

    public void setProductsID(List<String> productsID) {
        this.productsID = productsID;
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
                ", memberID=" + memberID +
                ", productsID=" + productsID +
                ", created_at=" + created_at +
                ", isPaid=" + isPaid +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public String toStringCustom() {
        StringBuilder productInfo = new StringBuilder();
        List<Product> productObjectsOfCustomer = new ArrayList<>();
        for (String productID : productsID) {
            Product productObject = BaseHelper.getProductByProductId(productID);
            assert productObject != null;
            productInfo.append("\n").append(productObject);
        }

        return "\nOrder{" +
                "id='" + id + '\'' +
                ", member=" + BaseHelper.getMemberById(memberID).getName() +
                ", \nproducts=" + productInfo +
                " \ncreated_at=" + created_at +
                ", \ntotalPrice=" + totalPrice +
                ", isPaid=" + isPaid +
                '}';
    }
}



