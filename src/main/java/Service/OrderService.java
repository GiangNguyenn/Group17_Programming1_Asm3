package Service;

import Model.Productions.*;
import Model.User.*;
import common.BaseConstant;
import common.BaseHelper;
import common.Utils;
import interfaces.OrderInterface;

import java.io.*;
import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static common.Utils.lstOrder;

public class OrderService implements OrderInterface {

    //TODO: Giang + Khang
    @Override
    public void loadData() {
        // TODO Auto-genethod stub
        //REQUIRES PRODUCT, MEMBER OBJECT LIST TO LOAD FIRST
        //IN ORDER TO USE THE GET***BY*** FUNCTIONS
        try {
            BufferedReader orderData = Utils.fileReader(BaseConstant.ORDER_DATA_PATH);
            String dataRow;
            while ((dataRow = orderData.readLine()) != null) {
                String[] detailed = dataRow.split(",");

                String idString = detailed[0];
                String memberString = detailed[1];
                String productsString = detailed[2];
                String dateString = detailed[3];
                String statusString = detailed[4];

                lstOrder.add(new Order(
                        idString,
                        BaseHelper.getMemberByUserName(memberString),
                        convertToProductList(productsString),
                        LocalDateTime.parse(dateString,DateTimeFormatter.ISO_DATE_TIME),
                        statusString
                ));
            }
            orderData.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //TODO: Giang + Khang
    @Override
    public void  writeData() throws FileNotFoundException {
        // TODO Auto-generated method stub
        PrintWriter out = new PrintWriter(BaseConstant.ORDER_DATA_PATH);
        if (!BaseHelper.isNullOrEmpty(lstOrder)){
            for (Order order : lstOrder){
                out.printf("%s,%s,%s,%s,%s\n",
                        order.getId(),
                        order.getMember().getUsername(),            //Stores username for customer
                        convertToString(order.getProducts()),       //Stores ID for product
                        convertToString(order.getCreated_at()),
                        order.getStatus());
            }
        }
        out.close();
    }

    //TODO: Giang + Khang
    @Override
    public void showAllOder() {
        // TODO Auto-generated method stub
        for (Order order : lstOrder){
            System.out.print(order.toString());
        }
    }

    public void showAllOrderOfCustomer(Member member){
        ArrayList<Order> customerOrders = new ArrayList<>();
        for (Order order : lstOrder){
            if (order.getMember().equals(member)){
                customerOrders.add(order);
            }
        }
        for (Order order : customerOrders){
            System.out.println(order.toString());
        }
    }

    public static String convertToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return date.format(formatter);
    }

    public static String convertToString(List<Product> productList){
        String resString = new String("[");
        for (Product product : productList){
            resString = resString+product.getId()+"and";
        }
        resString = resString+"]";
        return resString;
    }

    static ArrayList<Product> convertToProductList(String inputString){
        ArrayList<Product> resultArray = new ArrayList<>();
        inputString = inputString.replace("[","");
        inputString = inputString.replace("]","");
        String[] productStringList = inputString.split("and");
        for (String productIdString : productStringList){
            resultArray.add(BaseHelper.getProductByProductId(productIdString));
        }
        return resultArray;
    }



}
