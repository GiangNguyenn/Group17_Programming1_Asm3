package interfaces;

import java.io.IOException;

/**
 * this interface inherit the main interface
 * define method signatures associated with Product's features
 * implemented by the ProductService class
 */

public interface ProductInterface extends mainInterface {

    void showAllProduct();

    void addProduct() throws IOException;

    void manageProductPrice() throws IOException;

    void showProductsByCategory() throws IOException;

    void sortProductByPrice(String sortFunction) throws IOException;
}