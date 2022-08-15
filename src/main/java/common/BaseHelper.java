package common;

import java.util.*;

import Model.Productions.Product;
import Model.User.Admin;
import Model.User.Member;
import Model.User.User;

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

    public static Member getMemberByUserName(String userName) {
        Optional<Member> op = lstMember.stream().filter(user -> user.getUsername().equalsIgnoreCase(userName)).findFirst();
        return op.isPresent() ? op.get() : null;
    }

    public static Admin getAdminByUserName(String userName) {
        Optional<Admin> op = lstAdmin.stream().filter(admin -> admin.getUsername().equalsIgnoreCase(userName)).findFirst();
        return op.isPresent() ? op.get() : null;
    }

    public static Product getProductByProductId(String id) {
        Optional<Product> op = lstProduct.stream().filter(admin -> admin.getId().equalsIgnoreCase(id)).findFirst();
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

    public static String generateIdForOrder() {
        List<Integer> idArray = lstOrder.stream().map(order -> Integer.valueOf(order.getId())).toList();
        Integer maxId = Collections.max(idArray);
        return String.valueOf(maxId + 1);
    }

    public static boolean checkExistUsername(String username) {
        return lstMember.stream().map(User::getUsername).anyMatch(username::equals);
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String generateIdForProduction() {
        return null;
    }
}