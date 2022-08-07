package Tutorial.Project;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Cart {

    private final String id;
    private final Member member;
    private ArrayList<Product> products;

    public static String idUnique(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyMMddHHmmssnn");
        return myDateObj.format(myFormatObj);
    }

    public Cart(String id,Member member) {
        this.id = id;
        this.member = member;
        this.products =  new ArrayList<>();
    }

    public void addProductIntoCart(Product product) {
        // Adding a product to an array
        products.add(product);
    }

    public String getId() { return id;
    }

    public Member getMember() {
        return member;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + getId() + '\'' +
                ", member=" + getMember() +
                ", products=" + getProducts() +
                '}';
    }

    public static void main(String[] args) {
        Member m1 = new Member();
        Product p1 = new Product();
        Cart cart = new Cart("0001",m1);
        cart.addProductIntoCart(p1);
        cart.addProductIntoCart(new Product());
        System.out.println(cart.toString());
    }
}
