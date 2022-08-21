package common;

import Model.Productions.Order;
import Model.Productions.Product;
import Model.User.Admin;
import Model.User.Member;
import Model.User.User;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

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
        System.out.printf(BaseConstant.PRODUCT_TABLE_FORMAT, "   ID   ", "   Product's name   ", "   Product's price   ");
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
    public static void simpleTable(List<Object> objects, Class objClass) {

        Field[] fields = objClass.getDeclaredFields();

        int columnSize = fields.length;
        int rowSize = objects.size() + 1;

        /*
         * leftJustifiedRows - If true, it will add "-" as a flag to format string to
         * make it left justified. Otherwise right justified.
         */
        boolean leftJustifiedRows = true;

        /*
         * Maximum allowed width. Line will be wrapped beyond this width.
         */
        int maxWidth = 30;

        /*
         * Table to print in console in 2-dimensional array. Each sub-array is a row.
         */
        String[][] table = new String[][]{{"id", "First Name", "Last Name", "Age", "Profile"},
                {"1", "John", "Johnson", "45", "My name is John Johnson. My id is 1. My age is 45."},
                {"2", "Tom", "", "35", "My name is Tom. My id is 2. My age is 35."},
                {"3", "Rose", "Johnson Johnson Johnson Johnson Johnson Johnson Johnson Johnson Johnson Johnson", "22",
                        "My name is Rose Johnson. My id is 3. My age is 22."},
                {"4", "Jimmy", "Kimmel", "", "My name is Jimmy Kimmel. My id is 4. My age is not specified. "
                        + "I am the host of the late night show. I am not fan of Matt Damon. "}};

        String[][] tables = new String[rowSize][columnSize];
        int index = 0;
        for (Field field : fields) {
            table[0][index] = field.getName();
            index++;
        }

        /*
         * Create new table array with wrapped rows
         */
        List<String[]> tableList = new ArrayList<>(Arrays.asList(table));
        List<String[]> finalTableList = new ArrayList<>();
        for (String[] row : tableList) {
            // If any cell data is more than max width, then it will need extra row.
            boolean needExtraRow = false;
            // Count of extra split row.
            int splitRow = 0;
            do {
                needExtraRow = false;
                String[] newRow = new String[row.length];
                for (int i = 0; i < row.length; i++) {
                    // If data is less than max width, use that as it is.
                    if (row[i].length() < maxWidth) {
                        newRow[i] = splitRow == 0 ? row[i] : "";
                    } else if ((row[i].length() > (splitRow * maxWidth))) {
                        // If data is more than max width, then crop data at maxwidth.
                        // Remaining cropped data will be part of next row.
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

        /*
         * Calculate appropriate Length of each column by looking at width of data in
         * each column.
         *
         * Map columnLengths is <column_number, column_length>
         */
        Map<Integer, Integer> columnLengths = new HashMap<>();
        Arrays.stream(finalTable).forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
            columnLengths.putIfAbsent(i, 0);
            if (columnLengths.get(i) < a[i].length()) {
                columnLengths.put(i, a[i].length());
            }
        }));
        System.out.println("columnLengths = " + columnLengths);

        /*
         * Prepare format String
         */
        final StringBuilder formatString = new StringBuilder("");
        String flag = leftJustifiedRows ? "-" : "";
        columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
        formatString.append("|\n");
        System.out.println("formatString = " + formatString.toString());

        /*
         * Prepare line for top, bottom & below header row.
         */
        String line = columnLengths.entrySet().stream().reduce("", (ln, b) -> {
            String templn = "+-";
            templn = templn + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (ln1, b1) -> ln1 + "-",
                    (a1, b1) -> a1 + b1);
            templn = templn + "-";
            return ln + templn;
        }, (a, b) -> a + b);
        line = line + "+\n";
        System.out.println("Line = " + line);

        /*
         * Print table
         */
        System.out.print(line);
        Arrays.stream(finalTable).limit(1).forEach(a -> System.out.printf(formatString.toString(), a));
        System.out.print(line);

        Stream.iterate(1, (i -> i < finalTable.length), (i -> ++i))
                .forEach(a -> System.out.printf(formatString.toString(), finalTable[a]));
        System.out.print(line);
    }

}