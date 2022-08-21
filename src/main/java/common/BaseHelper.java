package common;

import Model.Productions.Order;
import Model.Productions.Product;
import Model.User.Admin;
import Model.User.Member;
import Model.User.User;

import java.util.*;

import static common.Utils.*;

public class BaseHelper {

    public static void printWelcomePage() {
        System.out.println("********* Here is welcome page *********");
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("STORE ORDER MANAGEMENT SYSTEM ");
        System.out.println("Instructor: Mr. Minh Vu ");
        System.out.println("Group: Group Name ");
        System.out.println("Nguyen Thi Quynh Giang - S3866617");
        System.out.println("Vo Khai Minh - S3879953");
        System.out.println("Nguyen Huu Minh Khang - s3927067");
        System.out.println("Nguyen Chau Loan - s3880115");
        System.out.println("****************************************");
        System.out.println(" ");
    }
    public static void productTable(List<Product> input) {
        System.out.println("========================================================");
        System.out.printf("%20s%15s%15s", "   ID   ", "   Product's name   ", "   Product's price   ");
        System.out.println("");
        System.out.println("========================================================");

        for (Product product : input) {
            System.out.printf("%20s%15s%15s", "   " + product.getId() + "   ", "   " + product.getProductName() + "   ",
                    "   " + product.getPrice() + "$");
            System.out.println("");
            System.out.println("========================================================");
        }
    }

    public static Boolean isLogin() {
        return Utils.isLogin;
    }

    public static User getCurrentUser() {
        return Utils.current_user;
    }

    //Customer
    public static Member getMemberByUserName(String userName) {
        Optional<Member> op = lstMember.stream().filter(user -> user.getUsername().equalsIgnoreCase(userName)).findFirst();
        return op.isPresent() ? op.get() : null;
    }

    public static Member getMemberById(String id) {
        Optional<Member> op = lstMember.stream().filter(user -> user.getId().equalsIgnoreCase(id)).findFirst();
        return op.isPresent() ? op.get() : null;
    }

    public static Admin getAdminByUserName(String userName) {
        Optional<Admin> op = lstAdmin.stream().filter(admin -> admin.getUsername().equalsIgnoreCase(userName)).findFirst();
        return op.isPresent() ? op.get() : null;
    }

    //Product
    public static Product getProductByProductId(String id) {
        Optional<Product> op = lstProduct.stream().filter(product -> product.getId().equalsIgnoreCase(id)).findFirst();
        return op.isPresent() ? op.get() : null;
    }

    //Order
    public static Order getOrderByOrderId(String id) {
        Optional<Order> op = lstOrder.stream().filter(order -> order.getId().equalsIgnoreCase(id)).findFirst();
        return op.isPresent() ? op.get() : null;
    }

    public static Order getOrderByOrderId(String id, List<Order> orderList) {
        Optional<Order> op = orderList.stream().filter(order -> order.getId().equalsIgnoreCase(id)).findFirst();
        return op.isPresent() ? op.get() : null;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return "".equals(value.toString().trim());
        } else if (value instanceof Collection) {
            Collection c = (Collection) value;
            return c.isEmpty();
        } else if (value instanceof Map) {
            Map m = (Map) value;
            return m.isEmpty();
        } else if (value.getClass().isArray()) {
            Object[] array = (Object[]) value;
            return (array.length <= 0);
        } else if (value instanceof Class && ((Class) value).isPrimitive()) {
            long temp = (long) value;
            return temp < 0;
        } else {
            return false;
        }
    }

    public static Boolean checkingMemberLoginInfo(String username, String password) {
        return lstMember.stream()
                .anyMatch(user -> Objects.equals(user.getPassword(), password)
                        && Objects.equals(user.getUsername(), username));
    }

    public static Boolean checkingAdminLoginInfo(String username, String password) {
        return lstAdmin.stream()
                .anyMatch(admin -> Objects.equals(admin.getPassword(), password)
                        && Objects.equals(admin.getUsername(), username));
    }

    public static String generateIdForUser() {
        List<Integer> idArray = lstMember.stream().map(user -> Integer.valueOf(user.getId())).toList();
        Integer maxId = Collections.max(idArray);
        return String.valueOf(maxId + 1);
    }

    public static String generateIdForProduct() {
        List<Integer> idArray = lstProduct.stream().map(product -> Integer.valueOf(product.getId())).toList();
        Integer maxId = Collections.max(idArray);
        return String.valueOf(maxId + 1);
    }

    public static String generateIdForOrder() {
        List<Integer> idArray = lstOrder.stream().map(order -> Integer.valueOf(order.getId())).toList();
        Integer maxId = Collections.max(idArray);
        return String.valueOf(maxId + 1);
    }

    public static boolean checkExistUsername(String username) {
        return lstMember.stream().map(User::getUsername).anyMatch(username::equals);
    }

    public static boolean checkExistProduct(String productName, String supplier) {
        if (lstProduct.stream().map(Product::getProductName).anyMatch(productName::equals)) {
            return lstProduct.stream().map(Product::getSupplier).anyMatch(supplier::equals);
        } else {
            return false;
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String generateIdForProduction() {
        return null;
    }
}