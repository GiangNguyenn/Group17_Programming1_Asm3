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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static common.Utils.lstOrder;

public class OrderService implements OrderInterface {

    /**
     * Take user input as orderID to get specific order
     */

    public void viewCustomerOrder() throws IOException {
        ArrayList<Order> ordersOfCustomer = new ArrayList<>();          //Get all the orders belong to the current users
        for (Order order : lstOrder) {
            if (Objects.equals(order.getMemberID(), Utils.current_user.getId())) {
                ordersOfCustomer.add(order);
            }
        }
        int indexOfOrder = 1;
        for (Order order : ordersOfCustomer) {                          //Print out the belonging orders and the indexes of it
            System.out.println(indexOfOrder + ". " + order.toString());
            indexOfOrder += 1;
        }
        System.out.print("Please enter the ID of the order: ");
        String targetOrderId = Utils.reader.readLine();
        Order order = BaseHelper.getOrderByOrderId(targetOrderId,ordersOfCustomer);
        if (order != null) {
            System.out.println(order.toStringCustom());
        } else {
            System.out.println("Order ID not found!");
        }
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
            System.out.println(order.toString());
        }

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
            while ((dataRow = orderData.readLine()) != null) {
                String[] detailed = dataRow.split(",");

                String idString = detailed[0];
                String memberString = detailed[1];
                String productsString = detailed[2];
                String dateString = detailed[3];
                Boolean statusString = Boolean.valueOf(detailed[4]);
                String priceString = detailed[5];
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
        System.out.println(lstOrder.toString());        //Debugging
        if (!BaseHelper.isNullOrEmpty(lstOrder)) {
            for (Order order : lstOrder) {
                System.out.println(order.getId());                      //Debugging
                System.out.println(order.getProductsID().toString());     //Debugging
                out.write(order.getId()+","+order.getMemberID()+","+            //Stores userID for customer
                        convertToString(order.getProductsID())+","+        //Stores ID for product
                        convertToString(order.getCreated_at())+","+     //String of ISO dateformat
                        order.getPaid()+","+ order.getTotalPrice()+"\n");
            }
        }
        out.close();
    }

    @Override
    public void showAllOder() {
        for (Order order : lstOrder) {
            System.out.print(order.toString());
        }
    }

    // Datatype to String
    public static String convertToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return date.format(formatter);
    }

    public static String convertToString(List<String> productIDList) {
        StringBuilder resString = new StringBuilder();
        for (String product : productIDList) {
            resString.append(product).append("and");
        }
        resString = new StringBuilder(resString.substring(0, resString.length()-3));        //Deleting the last "and"
        return resString.toString();
    }

    //String to other datatype
    static ArrayList<String> convertToProductIdList(String inputString) {
        String[] array = inputString.split("and");
        List<String> resultArray = new ArrayList<String>(Arrays.asList(array));
        return (ArrayList<String>) resultArray;
    }


    /**
     * get user's order Id input to find the order object.
     * then continue getting an input to see if user want to change the input
     * call method changeOrderStatus using that input and order's status
     *
     * @throws IOException
     */
    public void manageOrderStatus() throws IOException {
        System.out.println("Enter an Order ID: ");
        String orderId = Utils.reader.readLine();
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

    public void addProductToCart() throws IOException {
        System.out.println("Please input the product Id you want to add to cart: ");
        String productId = Utils.reader.readLine();

        Product product = BaseHelper.getProductByProductId(productId);
        if (!BaseHelper.isNullOrEmpty(product)) {
            Utils.cart.add(productId);
            System.out.println("Product " + product.getProductName() + " added to cart!");
            System.out.println(Utils.cart);
        } else {
            System.out.println("Product Id not found! Please try again!");
//            addProductToCart();
            // Deleting this because the user will be stuck in the loop if they don't know the ID

        }
    }

    public Double calculateTotalPrice() {
        List<Product> productObjectList = new ArrayList<>();
        for (String ProductId : Utils.cart){
            Product productObject = BaseHelper.getProductByProductId(ProductId);
            productObjectList.add(productObject);
        }
        return productObjectList.stream().mapToDouble(Product::getPrice).sum()
                *((Member) Utils.current_user).discountAmount();
    }


    public void placeOrder() throws IOException {
        LocalDateTime now = LocalDateTime.now();

        if (!BaseHelper.isNullOrEmpty(Utils.cart)) {
            Order newOrder = new Order(BaseHelper.generateIdForOrder(),
                    Utils.current_user.getId(),
                    new ArrayList<String>(Utils.cart),      //Mat em 1 buoi sang
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
}
