package application;

import Service.MenuService;
import Service.ProductService;
import Service.UserService;
import common.BaseHelper;


public class Application {

    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        UserService userService = new UserService();
        ProductService productService = new ProductService();

        userService.loadData();
        productService.loadData();
        BaseHelper.printWelcomePage();
        menuService.mainMenu();
    }
}
