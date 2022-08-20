package Service;

import Model.Productions.Product;
import Model.User.Member;
import common.BaseHelper;
import common.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import static common.Utils.lstProduct;

public class MenuService {
    UserService userService = new UserService();
    ProductService productService = new ProductService();
    OrderService orderService = new OrderService();

    private static MenuService INSTANT;

    public static void start() {
        INSTANT = new MenuService();
    }

    public static MenuService getInstant() {
        return INSTANT;
    }

    private static void printUtilMenu() {
        System.out.println("B. Go Back");
        System.out.println("E. Exit");
    }

    public void utilMenu(String choice) {
        try {
            switch (choice) {
                case "B" -> {
                    System.out.println("going ");
                    return;
                }
                case "E" -> exit();
                default -> {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void printMainMenu() {
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
        printMainMenu();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> {
                    userService.login();
                    printMenuByUserRole();
                    break;
                }
                case "2" -> {
                    userService.register();
                    break;
                }
                case "3" -> {
                    productService.showAllProduct();
                    break;
                }
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

    private static void printMemberMenu() {
        // Todo: Print main menu
        System.out.println("Select function: ");
        System.out.println("1. View all products");
        System.out.println("2. Browse products by categories");
        System.out.println("3. View order by OrderID"); // done
        System.out.println("4. View my profile");
        System.out.println("5. Log out");
        System.out.println("Your choice: ");
    }

    private void memberMenu() {
        BaseHelper.clearConsole();
        printMemberMenu();

        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> {
                    productService.showAllProduct();
                    placeOrderMenu();
                }
                case "2" -> productService.showProductsByCategory();
                case "3" -> orderService.viewOrderByIdMenu();
                case "4" -> userService.printUserProfile((Member) Utils.current_user);
                case "5" -> {
                    userService.logout();
                    return;
                }
                default -> System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            memberMenu();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void printPlaceOrderMenu() {
        System.out.println("Select action: ");
        System.out.println("1. Add product to cart");
        System.out.println("2. Checkout");
        System.out.println("Your choice:");
    }

    private void placeOrderMenu() {
        BaseHelper.clearConsole();
        printPlaceOrderMenu();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> orderService.addProductToCart();
                case "2" -> {
                    orderService.placeOrder();
                    return;
                }
                default -> System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            placeOrderMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printSortProductByPrice() {
        System.out.println("Select action: ");
        System.out.println("1. Sort product from low to high");
        System.out.println("2. Sort product from high to low");
    }

    public void productTable() {
        System.out.println("========================================================");
        System.out.printf("%20s%15s%15s", "   ID   ", "   Product's name   ", "   Product's price   ");
        System.out.println("");
        System.out.println("========================================================");

        for (Product product : lstProduct) {
            System.out.printf("%20s%15s%15s", "   " + product.getId() + "   ", "   " + product.getProductName() + "   ",
                    "   " + product.getPrice() + "$");
            System.out.println("");
            System.out.println("========================================================");
        }
    }

    public void sortProductByPriceMenu() {
        BaseHelper.clearConsole();
        productTable();
        printSortProductByPrice();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> productService.ascendProductByPrice();
                case "2" -> productService.descendProductByPrice();
                default -> System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            // sortProductByPriceMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printAdminMenu() {
        System.out.println("Select function: ");
        System.out.println("1. Add a new product");
        System.out.println("2. Update a product's price");
        System.out.println("3. View all orders of a Customer ID");
        System.out.println("4. Manage order status");
        System.out.println("5. Delete a product");
        System.out.println("Your choice: ");
    }

    private void adminMenu() {
        BaseHelper.clearConsole();

        printAdminMenu();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1":
                    productService.addProduct();
                case "2":
                    productService.manageProductPrice();
                    break;
                case "3":
                    orderService.viewOrderByCustomerId();
                    break;
                case "exit":
                    exit();
                case "4":
                    orderService.manageOrderStatus();
                    break;
                case "5":
                    productService.deleteProduct();
                    break;
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
            return;
        }
    }


    private static void exit() throws FileNotFoundException {
        UserService userService = new UserService();
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();

        userService.writeData();
        productService.writeData();
        orderService.writeData();
        System.exit(-1);
    }

}