package application;

import Model.Productions.Product;
import Service.MenuService;
import Service.UserService;
import common.BaseHelper;

import java.util.Collections;

import static common.Utils.lstProduct;

public class Application {

    public static void main(String[] args) {

        Product product1 = new Product("1", "T-shirt", "200000", "Clothing", "Zara");
        Product product2 = new Product("2", "Denim Shorts", "100000", "Clothing", "Zara");
        Product product3 = new Product("3", "Sun dress", "500000", "Clothing", "Zara");
        Product product4 = new Product("4", "Macbook Pro 2019", "500000", "Laptop", "Dell");

        Collections.addAll(lstProduct, product1, product2, product4, product3);
        MenuService menuService = new MenuService();
        UserService userService = new UserService();

        userService.loadData();
        BaseHelper.printWelcomePage();
        menuService.mainMenu();
    }
}
