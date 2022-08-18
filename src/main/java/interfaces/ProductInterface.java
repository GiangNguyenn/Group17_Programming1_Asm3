package interfaces;

import java.io.IOException;

public interface ProductInterface extends mainInterface {

    public void showAllProduct();

    void addProduct() throws IOException;
}