package Service;

import common.BaseHelper;
import common.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

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

    private static void printStartUpMenu() {
        System.out.println("1. Enter admin mode");
        System.out.println("2. Enter customer mode");
        System.out.println("E. Exit");
        System.out.println("Your choice: ");
    }

    public void startUpMenu() {
        printStartUpMenu();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> {
                    adminMainMenu();
                    break;
                }
                case "2" -> {
                    memberMainMenu();
                    break;
                }
                case "E" -> exit();
                default -> System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            startUpMenu();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void printAdminMainMenu() {
        System.out.println("Select function: ");
        System.out.println("1. Admin login");
        System.out.println("E. Exit");
        System.out.println("Your choice: ");
    }

    /**
     * Print out the main menu with customer options
     */
    public void adminMainMenu() {
        BaseHelper.clearConsole();
        printAdminMainMenu();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> {
                    userService.login();
                    printMenuByUserRole();
                    break;
                }
                case "E" -> exit();
                default -> System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            adminMainMenu();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void printMemberMainMenu() {
        System.out.println("Select function: ");
        System.out.println("1. Member login");
        System.out.println("2. Register");
        System.out.println("3. Browse products");
        System.out.println("E. Exit");
        System.out.println("Your choice: ");
    }

    public void memberMainMenu() {
        BaseHelper.clearConsole();
        printMemberMainMenu();
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
                case "E" -> exit();
                default -> System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            memberMainMenu();
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
        System.out.println("3. View your orders");   //done
        System.out.println("4. View my profile");
        System.out.println("5. Log out");
        System.out.println("E. Exit");
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
                case "3" -> orderService.viewCustomerOrder();
                case "4" -> userService.printUserProfile(Utils.current_user);
                case "5" -> {
                    userService.logout();
                    return;
                }
                case "E" -> exit();
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
        System.out.println("B. Go back");
        System.out.println("E. Exit");
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
                case "B" -> {
                    return;
                }
                case "E" -> exit();
                default -> System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            placeOrderMenu();
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
        System.out.println("6. Log out");
        System.out.println("E. exit");
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
                    return;
//                    break;
                case "3":
                    orderService.viewOrderByCustomerId();
                    break;
                case "4":
                    orderService.manageOrderStatus();
                    break;
                case "5":
                    productService.deleteProduct();
                    break;
                case "6":
                    userService.logout();
                    return;
                case "E":
                    exit();
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
