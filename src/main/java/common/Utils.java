package common;

import Model.Productions.Order;
import Model.Productions.Product;
import Model.User.Admin;
import Model.User.Member;
import Service.MenuService;
import Service.OrderService;
import Service.ProductService;
import Service.UserService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Utils {
    //login info
    public static Boolean isLogin = false;
    public static Member current_user = null;
    public static Boolean isAdmin = false;

    // Instant services
    static {
        MenuService.start();
        ProductService.start();
        OrderService.start();
        UserService.start();
    }

    public static MenuService menuService = MenuService.getInstant();
    public static OrderService orderService = OrderService.getInstant();
    public static ProductService productService = ProductService.getInstant();
    public static UserService userService = UserService.getInstant();


    /* Adding user to lstMember after registering successfully */
    public static List<Admin> lstAdmin = new ArrayList<>();

    /* Adding user to lstMember after registering successfully */
    public static List<Member> lstMember = new ArrayList<>();

    /* Adding products to lstMember after registering successfully */
    public static List<Product> lstProduct = new ArrayList<>();

    /* Adding products to lstMember after registering successfully */
    public static List<Order> lstOrder = new ArrayList<>();

    public static List<String> cart = new ArrayList<>();

    // Some tools useful
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static BufferedReader fileReader(String filePath) throws FileNotFoundException {
        return new BufferedReader(new FileReader(filePath));
    }

}