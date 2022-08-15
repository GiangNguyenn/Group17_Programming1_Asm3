package Service;

import java.io.FileNotFoundException;
import java.io.IOException;

import Model.User.Member;
import common.BaseHelper;
import common.Utils;

public class MenuService {

    public static void printMainMenu() {
        System.out.println("Select function: ");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Show all production");
        System.out.println("4. Show all order");
        System.out.println("5. add order");
        System.out.println("6. add production");

        System.out.println("Your choice: ");
    }

    /**
     * Print out the main menu with customer options
     */
    public void mainMenu() {
        BaseHelper.clearConsole();
        UserService userService = new UserService();
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();

        printMainMenu();
        try {
            String choice = Utils.reader.readLine();

            switch (choice) {
                case "1" -> {
                    userService.login();
                    printMenuByUserRole();
                }
                case "2" -> userService.register();
                case "3" -> productService.showAllProduct();
                case "4" -> exit();
                default -> System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            mainMenu();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void printMemberMenu() {
        // Todo: Print main menu
        System.out.println("Select function: ");
        System.out.println("1. View all products");
        System.out.println("2. Browse products by categories");
        System.out.println("3. View order by OrderID");
        System.out.println("4. View my profile");
        System.out.println("Your choice: ");
    }

    public void memberMenu() {
        BaseHelper.clearConsole();
        UserService userService = new UserService();
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();

        printMemberMenu();
        try {
            String choice = Utils.reader.readLine();

            switch (choice) {
                case "1":
                    productService.showAllProduct();
                    break;
                case "2":
                    ProductService.showProductsByCategory();
                    break;
                case "3":
//                    productService.viewOrderDetails();
                    break;
                case "4":
                    UserService.printUserProfile((Member) Utils.current_user);
                    break;
                case "5":
                    userService.logout();
                    return;
                default:
                    System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            memberMenu();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void printAdminMenu() {
        System.out.println("Select function: ");
        System.out.println("1. Add a new product");
        System.out.println("2. Update a product's price");
        System.out.println("3. View all orders of a Customer ID");
        System.out.println("4. Manage order status");
        System.out.println("Your choice: ");
    }

    public void adminMenu() {
        BaseHelper.clearConsole();
        UserService userService = new UserService();
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();

        printAdminMenu();
        try {
            String choice = Utils.reader.readLine();

            switch (choice) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            adminMenu();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void printMenuByUserRole() {
        BaseHelper.clearConsole();
        if (Utils.isLogin && Utils.isAdmin) {
            adminMenu();
            return;
        }
        if (Utils.isLogin) {
            memberMenu();
        }
    }


    public void exit() throws FileNotFoundException {
        UserService userService = new UserService();
        userService.writeData();
        System.exit(-1);
    }

}
