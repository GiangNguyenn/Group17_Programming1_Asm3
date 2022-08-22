package interfaces;

import java.io.IOException;

public interface OrderInterface extends mainInterface {

    void showAllOders();

    void viewCustomerOrder() throws IOException;

    void viewOrderByCustomerId() throws IOException;

    void manageOrderStatus() throws IOException;
}