package common;

import Model.Productions.Order;
import Model.Productions.Product;
import Model.User.Admin;
import Model.User.Member;
import Model.User.User;

import java.util.*;
import java.util.stream.Stream;

import static common.Utils.*;

public class BaseHelper {

    public static void printWelcomePage() {
        // Todo prin welcome page in item C
        System.out.println("********* Here is welcome page *********");
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

    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return "".equals(value.toString().trim());
        } else if (value instanceof Collection c) {
            return c.isEmpty();
        } else if (value instanceof Map m) {
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
        List<Integer> idArray = lstProduct.stream().map(order -> Integer.valueOf(order.getId())).toList();
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

    public static void outputTable(String[][] table) {
        boolean leftJustifiedRows = false;
        Map<Integer, Integer> columnLengths = new HashMap<>();
        Arrays.stream(table).forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
            if (columnLengths.get(i) == null) {
                columnLengths.put(i, 0);
            }
            if (columnLengths.get(i) < a[i].length()) {
                columnLengths.put(i, a[i].length());
            }
        }));

        final StringBuilder formatString = new StringBuilder();
        String flag = leftJustifiedRows ? "-" : "";
        columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
        formatString.append("|\n");

        String line = columnLengths.entrySet().stream().reduce("", (ln, b) -> {
            String templn = "+-";
            templn = templn + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (ln1, b1) -> ln1 + "-",
                    (a1, b1) -> a1 + b1);
            templn = templn + "-";
            return ln + templn;
        }, (a, b) -> a + b);
        line = line + "+\n";

        System.out.print(line);
        Arrays.stream(table).limit(1).forEach(a -> System.out.printf(formatString.toString(), a));
        System.out.print(line);

        Stream.iterate(1, (i -> i < table.length), (i -> ++i))
                .forEach(a -> System.out.printf(formatString.toString(), table[a]));
        System.out.print(line);
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String generateIdForProduction() {
        return null;
    }
}