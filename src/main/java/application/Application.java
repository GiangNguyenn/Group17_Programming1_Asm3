package application;

import Service.MenuService;
import Service.OrderService;
import Service.ProductService;
import Service.UserService;
import common.BaseHelper;


public class Application {

    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        UserService userService = new UserService();
        OrderService orderService = new OrderService();
        ProductService productService = new ProductService();

        userService.loadData();
        productService.loadData();
        orderService.loadData();
        BaseHelper.printWelcomePage();
        menuService.mainMenu();
    }
}
