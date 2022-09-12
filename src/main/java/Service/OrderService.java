package Service;

import Model.Productions.Order;
import Model.Productions.Product;
import Model.User.Member;
import common.BaseConstant;
import common.BaseHelper;
import common.Utils;
import interfaces.OrderInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static common.Utils.*;

public class OrderService implements OrderInterface {

    private static OrderService INSTANT;

    public static void start() {
        INSTANT = new OrderService();
    }

    public static OrderService getInstant() {
        return INSTANT;
    }


    /**
     * ORDER LOAD FIRST TO CALCULATE USER TOTAL SPENDING
     */
    @Override
    public void loadData() {
        try {
            BufferedReader orderData = Utils.fileReader(BaseConstant.ORDER_DATA_PATH);
            String dataRow;
            lstOrder.clear();
            String idString;
            String memberString;
            String productsString;
            String dateString;
            boolean statusString;
            String priceString;
            while ((dataRow = orderData.readLine()) != null) {
                String[] detailed = dataRow.split(",");
                idString = detailed[0];
                memberString = detailed[1];
                productsString = detailed[2];
                dateString = detailed[3];
                statusString = Boolean.parseBoolean(detailed[4]);
                priceString = detailed[5];
                lstOrder.add(new Order(idString, memberString, convertToProductIdList(productsString), LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME), statusString, Double.valueOf(priceString)));
            }
            orderData.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeData() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(BaseConstant.ORDER_DATA_PATH);
        if (!BaseHelper.isNullOrEmpty(lstOrder)) {
            for (Order order : lstOrder) {
                out.write(order.getId() + "," + order.getMemberID() + "," +            //Stores userID for customer
                        convertToString(order.getProductsID()) + "," +        //Stores ID for product
                        convertToString(order.getCreated_at()) + "," +     //String of ISO dateformat
                        order.getPaid() + "," + order.getTotalPrice() + "\n");
            }
        }
        out.close();
    }

    @Override
    public void showAllOders() {
        for (Order order : lstOrder) {
            System.out.println(order.toString());
        }
    }

    // Datatype to String
    private static String convertToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return date.format(formatter);
    }

    private static String convertToString(List<String> productIDList) {
        StringBuilder resString = new StringBuilder();
        for (String product : productIDList) {
            resString.append(product).append("and");
        }
        resString = new StringBuilder(resString.substring(0, resString.length() - 3));        //Deleting the last "and"
        return resString.toString();
    }

    //String to other datatype
    private static ArrayList<String> convertToProductIdList(String inputString) {
        String[] array = inputString.split("and");
        List<String> resultArray = new ArrayList<String>(Arrays.asList(array));
        return (ArrayList<String>) resultArray;
    }

    //Menus

    /**
     * Take user input as orderID to get specific order
     */
    public void viewCustomerOrder() throws IOException {
        int indexOfOrder = 1;
        ArrayList<Order> ordersOfCustomer = new ArrayList<>();          //Get all the orders belong to the current users
        for (Order order : lstOrder) {
            if (Objects.equals(order.getMemberID(), Utils.current_user.getId())) {
                ordersOfCustomer.add(order);
            }
        }

        for (Order order : ordersOfCustomer) {                          //Print out the belonging orders and the indexes of it
            System.out.println(indexOfOrder + ". " + order.toString());
            indexOfOrder += 1;
        }
        System.out.print("Please enter the index of the order: ");
        String targetOrderIndex = Utils.reader.readLine().trim();
        if (!targetOrderIndex.matches("[0-9]+")) {
            System.out.println("Input Invalid!");
            viewCustomerOrder();
        }
        if (Integer.parseInt(targetOrderIndex) > ordersOfCustomer.size()) {
            System.out.println("\nOrder not found");
            System.out.println("Please enter again");
            viewCustomerOrder();
        }
        Order order = ordersOfCustomer.get(Integer.parseInt(targetOrderIndex) - 1);
        System.out.println(order.toStringCustom());
    }

    /**
     * Take user input as customer ID to get associated Orders
     */
    public void viewOrderByCustomerId() throws IOException {
        System.out.print("Please enter the ID of the customer:");
        String customerID = Utils.reader.readLine();
        Member targetCustomer = BaseHelper.getMemberById(customerID);       //Finds customer

        if (targetCustomer == null) {
            System.out.println("Customer not found!");
            viewOrderByCustomerId();
        }

        ArrayList<Order> ordersOfCustomer = new ArrayList<>();
        for (Order order : lstOrder) {
            if (Objects.equals(order.getMemberID(), targetCustomer.getId())) {          // Returns Empty List if no customer is found
                ordersOfCustomer.add(order);
            }
        }
        for (Order order : ordersOfCustomer) {
            System.out.println(order.toStringCustom());
        }
    }

    /**
     * get user's order Id input to find the order object.
     * then continue getting an input to see if user want to change the input
     * call method changeOrderStatus using that input and order's status
     *
     * @throws IOException
     */

    @Override
    public void manageOrderStatus() throws IOException {
        System.out.println("Enter an Order ID: ");
        String orderId = Utils.reader.readLine();
        if (orderId.equalsIgnoreCase("B")) {
            return;
        }
        Order searchedOrder = BaseHelper.getOrderByOrderId(orderId);
        if (!BaseHelper.isNullOrEmpty(searchedOrder) && !searchedOrder.getPaid()) {
            System.out.println(searchedOrder);
            System.out.println("===================");
            System.out.println("Order's status: UNPAID.\n Do you want to change the status to PAID? (Y/N): ");
            String answer = Utils.reader.readLine();
            searchedOrder.setPaid(changeOrderStatus(answer, searchedOrder.getPaid()));
            System.out.println("Order status: Paid");
        } else if (!BaseHelper.isNullOrEmpty(searchedOrder) && searchedOrder.getPaid()) {
            System.out.println(searchedOrder);
            System.out.println("===================");
            System.out.println("Order's status: PAID.\n Do you want to change the status to UNPAID? (Y/N): ");
            String answer = Utils.reader.readLine();
            searchedOrder.setPaid(changeOrderStatus(answer, searchedOrder.getPaid()));
            System.out.println("Order status: Unpaid");
        }
    }

    /**
     * @param input
     * @param orderStatus
     * @return true if user's input is y or yes, and vice versa with n or no
     */
    private Boolean changeOrderStatus(String input, Boolean orderStatus) {
        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
            System.out.println("Order status changed successfully!");
            return !orderStatus;
        } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
            System.out.println("Order status has not been changed!");
            return orderStatus;
        } else {
            System.out.println("Invalid input!");
            return orderStatus;
        }
    }

    // Using product ID to add to Cart
    public void addProductToCart() throws IOException {
        printCart();
        System.out.println("Note: Type 'B' to go back.");
        System.out.print("Please input the product Id you want to add to cart: ");
        String productId = Utils.reader.readLine();

        Product product = BaseHelper.getProductByProductId(productId);
        if (productId.equalsIgnoreCase("B")) {
            return;
        }
        if (!BaseHelper.isNullOrEmpty(product)) {
            Utils.cart.add(productId);
            System.out.println("Product " + product.getProductName() + " added to cart!");
            printCart();
        } else {
            System.out.println("Product Id not found! Please try again!");
            addProductToCart();
        }
    }

    private void printCart() {
        System.out.println("Shopping cart: ");
        System.out.println("-----------------------------------------");
        for (String productId : Utils.cart) {
            if (Utils.cart.size() > 0) {
                System.out.println(BaseHelper.getProductByProductId(productId).getProductName());
            } else {
                System.out.println("Empty cart!");
            }
        }
        System.out.println("-----------------------------------------");
    }

    private Double calculateTotalPrice() {
        List<Product> productObjectList = new ArrayList<>();
        for (String ProductId : Utils.cart) {
            Product productObject = BaseHelper.getProductByProductId(ProductId);
            productObjectList.add(productObject);
        }
        return productObjectList.stream().mapToDouble(Product::getPrice).sum() * ((Member) Utils.current_user).discountAmount();
    }


    public void placeOrder() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        if (!BaseHelper.isNullOrEmpty(Utils.cart)) {
            Order newOrder = new Order(BaseHelper.generateUniqueId(Order.class), Utils.current_user.getId(), new ArrayList<String>(Utils.cart),
                    now,                                    //When clearing Utils.cart, the products in this order are deleted too
                    false,                                  //Have to make separate object by copying the origin Utils.cart
                    this.calculateTotalPrice());
            lstOrder.add(newOrder);
            System.out.println("Order placed successfully!");
            System.out.println(newOrder);
            Utils.cart.clear();
        } else {
            System.out.println("Your shopping cart is empty!");
            ProductService productService = new ProductService();
            productService.showAllProduct();
            addProductToCart();
        }
    }

    private Double calculateRevenueOneDay(LocalDate targetDate){
        return lstOrder.stream().filter(order -> order.getCreated_at().toLocalDate().equals(targetDate)).mapToDouble(Order::getTotalPrice).sum();
    }

    private String dateAdjustmentHelper(String string){
        return String.format("%02d", Integer.parseInt(string));          // Adding zeros before numbers for the LocalDate.parse to work
                                                                        // Helps customers avoiding the Exception because of missing zero
    }
    private LocalDate userInputToDate(String targetYearString, String targetMonthString, String targetDayString){
        if (!(targetDayString.matches("\\d{1,2}") && targetMonthString.matches("\\d{1,2}") && targetYearString.matches("\\d{4}"))) {
            System.out.println("Invalid format! Please write again.");
            System.out.println("");
            revenueSpecificDayMenu();
            return null;
        }
        targetMonthString = dateAdjustmentHelper(targetMonthString);
        targetDayString = dateAdjustmentHelper(targetDayString);
        String targetDateString = targetYearString + "-" + targetMonthString + "-" + targetDayString;
        return LocalDate.parse(targetDateString, DateTimeFormatter.ISO_DATE);
    }

    public void revenueTodayMenu(){
        LocalDate targetDate = LocalDate.now();
        System.out.println("Revenue made in " + targetDate + " : " + calculateRevenueOneDay(targetDate));
    }
    public void revenueSpecificDayMenu() {
        try {
            String targetYearString;
            String targetMonthString;
            String targetDayString;
            System.out.print("Please enter the year yyyy: ");
            targetYearString = Utils.reader.readLine().trim();
            System.out.print("Please enter the month mm: ");
            targetMonthString = Utils.reader.readLine().trim();
            System.out.print("Please enter the day dd: ");
            targetDayString = Utils.reader.readLine().trim();
            LocalDate targetDate = userInputToDate(targetYearString,targetMonthString,targetDayString);
            if (targetDate != null){
                System.out.println("Revenue made in " + targetDate + " : " + calculateRevenueOneDay(targetDate));}
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DateTimeParseException ex) {
            System.out.println("Invalid date! Please try again.");
            System.out.println("");
            orderService.revenueSpecificDayMenu();
        }
    }

}





