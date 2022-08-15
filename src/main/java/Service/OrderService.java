package Service;

import Shopping.Order;
import common.BaseConstant;
import common.BaseHelper;
import common.Utils;
import interfaces.OrderInterface;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static common.Utils.lstOrder;

public class OrderService implements OrderInterface {

    //TODO: Giang + Khang
    @Override
    public void loadData() {
        // TODO Auto-genethod stub
        try {
            BufferedReader orderData = Utils.fileReader(BaseConstant.ORDER_DATA_PATH);
            String dataRow;

            while ((dataRow = orderData.readLine()) != null) {

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO: Giang + Khang
    @Override
    public void writeData() throws FileNotFoundException {
        // TODO Auto-generated method stub
        PrintWriter out = new PrintWriter(BaseConstant.ORDER_DATA_PATH);
        if (BaseHelper.isNullOrEmpty(lstOrder)){
            for (Order order : lstOrder){
                out.printf("%s,%s,%s,$s,$s",
                        order.getId(),
                        order.getMember().getUsername(),
                        order.getProducts(),
                        toStringTime(order.getCreated_at()),
                        order.getStatus());
            }
        }
    }


    public static String toStringTime(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return date.format(formatter);
    }

    //TODO: Giang + Khang
    @Override
    public void showAllOder() {
        // TODO Auto-generated method stub

    }

}
