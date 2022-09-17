package interfaces;

import java.io.IOException;

/**
 * this interface inherits the main interface
 * define method signatures associated with Order's feature
 * implemented by the OrderService class
 */

public interface OrderInterface extends mainInterface {

    void showAllOrders();

    void viewCustomerOrder() throws IOException;

    void viewOrderByCustomerId() throws IOException;

    void manageOrderStatus() throws IOException;
}