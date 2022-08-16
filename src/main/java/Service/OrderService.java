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
import java.util.List;
import java.util.Objects;

import static common.Utils.lstOrder;

public class OrderService implements OrderInterface {

    /**
     * Take user input as orderID to get specific order
     */
    public void viewOrderByIdMenu() throws IOException {
        System.out.print("Please enter the ID of the order: ");
        String targetOrderId = Utils.reader.readLine();
        Order order = BaseHelper.getOrderByOrderId(targetOrderId);
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
        }

        ArrayList<Order> ordersOfCustomer = new ArrayList<>();
        for (Order order : lstOrder) {
            if (Objects.equals(order.getMember(), targetCustomer)) {          // Returns Empty List if no customer is found
                ordersOfCustomer.add(order);
            }
        }

        for (Order order : ordersOfCustomer) {
            System.out.println(order.toString());
        }

    }

    /**
     * REQUIRES PRODUCT, MEMBER OBJECT LIST TO LOAD FIRST
     * IN ORDER TO USE THE GET***BY*** FUNCTIONS
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
                lstOrder.add(new Order(idString, BaseHelper.getMemberById(memberString), convertToProductList(productsString), LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME), statusString, Double.valueOf(priceString)));
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
                out.printf("%s,%s,%s,%s,%s,%s\n", order.getId(), order.getMember().getId(),            //Stores username for customer
                        convertToString(order.getProducts()),       //Stores ID for product
                        convertToString(order.getCreated_at()),     //String of ISO dateformat
                        order.getPaid(), order.getTotalPrice());
            }
        }
        out.close();
    }

    @Override
    public void showAllOder() {
        // TODO Auto-generated method stub
        for (Order order : lstOrder) {
            System.out.print(order.toString());
        }
    }

    // Datatype to String
    public static String convertToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return date.format(formatter);
    }

    public static String convertToString(List<Product> productList) {
        StringBuilder resString = new StringBuilder();
        for (Product product : productList) {
            resString.append(product.getId()).append("and");
        }
        return resString.toString();
    }


    //String to other datatype
    static ArrayList<Product> convertToProductList(String inputString) {
        ArrayList<Product> resultArray = new ArrayList<>();
        String[] productStringList = inputString.split("and");
        for (String productIdString : productStringList) {
            resultArray.add(BaseHelper.getProductByProductId(productIdString));
        }
        return resultArray;
    }


    public void manageOrderStatus() throws IOException {
        System.out.println("Enter an Order ID: ");
        String orderId = Utils.reader.readLine();
        Order searchedOrder = BaseHelper.getOrderByOrderId(orderId);
        if (!BaseHelper.isNullOrEmpty(searchedOrder) && !searchedOrder.getPaid()) {
            System.out.println(searchedOrder);
            System.out.println("===================");
            System.out.println("Order's status: UNPAID. \n Do you want to change the status to PAID? (Y/N): ");
            String answer = Utils.reader.readLine();
            searchedOrder.setPaid(changeOrderStatus(answer, searchedOrder.getPaid()));
            System.out.println(searchedOrder);
        } else if (!BaseHelper.isNullOrEmpty(searchedOrder) && searchedOrder.getPaid()) {
            System.out.println(searchedOrder);
            System.out.println("===================");
            System.out.println("Order's status: UNPAID. \n Do you want to change the status to PAID? (Y/N): ");
            String answer = Utils.reader.readLine();
            searchedOrder.setPaid(changeOrderStatus(answer, searchedOrder.getPaid()));
            System.out.println(searchedOrder);
        }
    }

    private Boolean changeOrderStatus(String input, Boolean orderStatus) {
        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
            return !orderStatus;
        } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
            return orderStatus;
        } else {
            System.out.println("Invalid input!");
            return orderStatus;
        }
    }
}
