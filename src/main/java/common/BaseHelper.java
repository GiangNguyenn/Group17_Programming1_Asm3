package common;

import Model.Productions.Order;
import Model.Productions.Product;
import Model.User.Admin;
import Model.User.Member;
import Model.User.User;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static common.BaseConstant.*;
import static common.Utils.*;

public class BaseHelper {

    public static void printWelcomePage() {
        System.out.println("********* Here is welcome page *********");
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println(BLACK_BOLD + "STORE ORDER MANAGEMENT SYSTEM " + ANSI_RESET);
        System.out.println(BLUE_BOLD + "Instructor: Mr. Minh Vu " + ANSI_RESET);
        System.out.println(BLACK_BOLD + "Group: Group 17" + ANSI_RESET);
        System.out.println("Nguyen Thi Quynh Giang - S3866617");
        System.out.println("Vo Khai Minh - S3879953");
        System.out.println("Nguyen Huu Minh Khang - s3927067");
        System.out.println("Nguyen Chau Loan - s3880115");
        System.out.println("****************************************");
        System.out.println(" ");
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

    public static Boolean validateUserInput(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input).find();
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
        return lstMember.stream().anyMatch(user -> Objects.equals(user.getPassword(), password) && Objects.equals(user.getUsername(), username));
    }

    public static Boolean checkingAdminLoginInfo(String username, String password) {
        return lstAdmin.stream().anyMatch(admin -> Objects.equals(admin.getPassword(), password) && Objects.equals(admin.getUsername(), username));
    }

    @SuppressWarnings("rawtypes")
    public static String generateUniqueId(Class objClass) {

        if (Member.class.equals(objClass) || Admin.class.equals(objClass) || User.class.equals(objClass)) {
            return generateIdForUser();
        } else if (Product.class.equals(objClass)) {
            return generateIdForProduct();
        } else if (Order.class.equals(objClass)) {
            return generateIdForOrder();
        } else {
            System.out.println("Could not generate id for object " + objClass.getName());
        }
        return "";
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

    public static void loadData() {
        Utils.userService.loadData();
        Utils.productService.loadData();
        Utils.orderService.loadData();
    }

    public static void writeData() throws FileNotFoundException {
        Utils.userService.writeData();
        Utils.productService.writeData();
        Utils.orderService.writeData();
    }

    @SuppressWarnings("rawtypes")
    public static void simpleTable(String[][] table) {
        boolean leftJustifiedRows = true;
        int maxWidth = 30;
        List<String[]> tableList = new ArrayList<>(Arrays.asList(table));
        List<String[]> finalTableList = new ArrayList<>();
        for (String[] row : tableList) {
            boolean needExtraRow;
            int splitRow = 0;
            do {
                needExtraRow = false;
                String[] newRow = new String[row.length];
                for (int i = 0; i < row.length; i++) {
                    if (row[i].length() < maxWidth) {
                        newRow[i] = splitRow == 0 ? row[i] : "";
                    } else if ((row[i].length() > (splitRow * maxWidth))) {
                        int end = Math.min(row[i].length(), ((splitRow * maxWidth) + maxWidth));
                        newRow[i] = row[i].substring((splitRow * maxWidth), end);
                        needExtraRow = true;
                    } else {
                        newRow[i] = "";
                    }
                }
                finalTableList.add(newRow);
                if (needExtraRow) {
                    splitRow++;
                }
            } while (needExtraRow);
        }
        String[][] finalTable = new String[finalTableList.size()][finalTableList.get(0).length];
        for (int i = 0; i < finalTable.length; i++) {
            finalTable[i] = finalTableList.get(i);
        }
        Map<Integer, Integer> columnLengths = new HashMap<>();
        Arrays.stream(finalTable).forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
            columnLengths.putIfAbsent(i, 0);
            if (columnLengths.get(i) < a[i].length()) {
                columnLengths.put(i, a[i].length());
            }
        }));
        final StringBuilder formatString = new StringBuilder("");
        String flag = leftJustifiedRows ? "-" : "";
        columnLengths.forEach((key, value) -> formatString.append("| %").append(flag).append(value).append("s "));
        formatString.append("|\n");
        String line = columnLengths.entrySet().stream().reduce("", (ln, b) -> {
            String templn = "+-";
            templn = templn + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (ln1, b1) -> ln1 + "-", (a1, b1) -> a1 + b1);
            templn = templn + "-";
            return ln + templn;
        }, (a, b) -> a + b);
        line = line + "+\n";
        System.out.print(line);
        Arrays.stream(finalTable).limit(1).forEach(a -> System.out.printf(formatString.toString(), a));
        System.out.print(line);

        Stream.iterate(1, (i -> i < finalTable.length), (i -> ++i)).forEach(a -> System.out.printf(formatString.toString(), finalTable[a]));
        System.out.print(line);
    }

    public static List<Order> addSingleOrderToOrderList(Order input) {
        List<Order> singleOrder = new ArrayList<>();
        singleOrder.add(input);
        return singleOrder;
    }

    public static List<Product> addSingleOrderToOrderList(Product input) {
        List<Product> singleProduct = new ArrayList<>();
        singleProduct.add(input);
        return singleProduct;
    }

    public static List<Admin> addSingleOrderToOrderList(Admin input) {
        List<Admin> singleAdmin = new ArrayList<>();
        singleAdmin.add(input);
        return singleAdmin;
    }

    public static List<Member> addSingleOrderToOrderList(Member input) {
        List<Member> singleMember = new ArrayList<>();
        singleMember.add(input);
        return singleMember;
    }

    public static String[][] memberTableGenerator(List<Member> input) {
        if (isNullOrEmpty(input)) {
            return new String[][]{};
        }
        String[][] table = new String[input.size() + 1][4];
        table[0] = new String[]{"Member's ID", "Member's Name", "Member's Phone", "Type of member", "total Spending"};
        int index = 1;
        for (Member member : input) {
            table[index] = new String[]{member.getId(), member.getName(), member.getPhoneNumber(), member.converMemberTypeToString(), String.valueOf(member.getTotalSpending())};
            index++;
        }
        return table;
    }

    public static String[][] adminTableGenerator(List<Admin> input) {
        if (isNullOrEmpty(input)) {
            return new String[][]{};
        }
        String[][] table = new String[input.size() + 1][4];
        table[0] = new String[]{"Admin's ID", "Admin's UserName", "Admin's Password"};
        int index = 1;
        for (Admin admin : input) {
            table[index] = new String[]{admin.getId(), admin.getUsername(), admin.getPassword()};
            index++;
        }
        return table;
    }

    public static String[][] orderTableGenerator(List<Order> input) {
        if (isNullOrEmpty(input)) {
            return new String[][]{};
        }
        String[][] table = new String[input.size() + 1][4];
        table[0] = new String[]{"Order's ID", "Member's ID", "Product's IDs", "Created date", "Order's status", "total Spending"};
        int index = 1;
        for (Order order : input) {
            table[index] = new String[]{order.getId(), order.getMemberID(), order.getProductsID().toString(), String.valueOf(order.getCreated_at()), String.valueOf(order.getPaid()), String.valueOf(order.getTotalPrice())};
            index++;
        }
        return table;
    }

    public static String[][] productTableGenerator(List<Product> input) {
        if (isNullOrEmpty(input)) {
            return new String[][]{};
        }
        String[][] table = new String[input.size() + 1][4];
        table[0] = new String[]{"Product's ID", "Product's name", "Product's price", "Product's category", "Supplier"};
        int index = 1;
        for (Product product : input) {
            table[index] = new String[]{product.getId(), product.getProductName(), String.valueOf(product.getPrice()), product.getCategory(), product.getSupplier()};
            index++;
        }
        return table;
    }
}


















