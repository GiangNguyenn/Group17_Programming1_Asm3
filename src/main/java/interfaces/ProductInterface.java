package interfaces;

import java.io.IOException;

public interface ProductInterface extends mainInterface {

    public void showAllProduct();

    void addProduct() throws IOException;

    void manageProductPrice() throws IOException;

    void showProductsByCategory() throws IOException;

    void sortProductByPrice(String sortFunction) throws IOException;
}