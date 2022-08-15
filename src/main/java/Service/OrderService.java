package Service;

import common.BaseConstant;
import common.Utils;
import interfaces.OrderInterface;

import java.io.BufferedReader;
import java.io.IOException;

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
    public void writeData() {
        // TODO Auto-generated method stub

    }

    //TODO: Giang + Khang
    @Override
    public void showAllOder() {
        // TODO Auto-generated method stub

    }

}
