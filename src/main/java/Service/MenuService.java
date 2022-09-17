package Service;

import common.BaseHelper;
import common.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import static common.BaseConstant.ANSI_RED;
import static common.BaseConstant.ANSI_RESET;
import static common.BaseHelper.productTableGenerator;
import static common.Utils.lstProduct;
import static common.Utils.orderService;

public class MenuService {

    private static MenuService INSTANCE;

    public static void start() {
        INSTANCE = new MenuService();
    }

    public static MenuService getInstant() {
        return INSTANCE;
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
                case "e", "E" -> exit();
                default -> System.out.println(ANSI_RED + "Invalid choice, please try again!" + ANSI_RESET);
            }
            System.out.println("press Enter to continue...");
            Utils.reader.read();
            startUpMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print out the admin main menu with admin's options
     */
    private static void printAdminMainMenu() {
        System.out.println("Select function: ");
        System.out.println("1. Admin login");
        System.out.println("E. Exit");
        System.out.println("B. Go back");
        System.out.println("Your choice: ");
    }

    /**
     * Call methods based on user input
     */
    public void adminMainMenu() {
        BaseHelper.clearConsole();
        printAdminMainMenu();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> {
                    Utils.userService.login();
                    printMenuByUserRole();
                    break;
                }
                case "b", "B" -> {
                    return;
                }
                case "e", "E" -> exit();
                default -> System.out.println(ANSI_RED + "Invalid choice, please try again!" + ANSI_RESET);
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            adminMainMenu();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Print out the customer main menu with customer's options
     */
    private static void printMemberMainMenu() {
        System.out.println("Select function: ");
        System.out.println("1. Member login");
        System.out.println("2. Register");
        System.out.println("3. Browse products");
        System.out.println("B. Go back");
        System.out.println("E. Exit");
        System.out.println("Your choice: ");
    }

    public void memberMainMenu() throws FileNotFoundException {
        BaseHelper.clearConsole();
        printMemberMainMenu();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> {
                    Utils.userService.login();
                    printMenuByUserRole();
                    break;
                }
                case "2" -> {
                    Utils.userService.register();
                    break;
                }
                case "3" -> {
                    Utils.productService.showAllProduct();
                    break;
                }
                case "b", "B" -> {
                    return;
                }
                case "E", "e" -> exit();
                default -> System.out.println(ANSI_RED + "Invalid choice, please try again!" + ANSI_RESET);
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            memberMainMenu();
        } catch (IOException e) {
            BaseHelper.writeData();
            e.printStackTrace();
        }
    }

    private static void printMemberMenu() {
        System.out.println("Select function: ");
        System.out.println("1. View all products");
        System.out.println("2. Browse products by categories");
        System.out.println("3. Sort Products by price");
        System.out.println("4. View your orders");
        System.out.println("5. View my profile");
        System.out.println("6. Log out");
        System.out.println("E. Exit");
        System.out.print("Your choice: ");
    }

    private void memberMenu() {
        BaseHelper.clearConsole();
        printMemberMenu();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> {
                    Utils.productService.showAllProduct();
                    placeOrderMenu();
                }
                case "2" -> {
                    Utils.productService.showProductsByCategory();
                    break;
                }
                case "3" -> {
                    sortProductByPriceMenu();
                    break;
                }
                case "4" -> Utils.orderService.viewCustomerOrder();
                case "5" -> Utils.userService.printUserProfile(BaseHelper.getCurrentUser());
                case "6" -> {
                    Utils.userService.logout();
                    return;
                }
                case "b", "B" -> {
                    return;
                }
                case "E", "e" -> exit();
                default -> System.out.println(ANSI_RED + "Invalid choice, please try again!" + ANSI_RESET);
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
        System.out.print("Your choice: ");
    }

    private void placeOrderMenu() {
        BaseHelper.clearConsole();
        printPlaceOrderMenu();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> Utils.orderService.addProductToCart();
                case "2" -> {
                    Utils.orderService.placeOrder();
                    return;
                }
                case "b", "B" -> {
                    return;
                }
                case "E", "e" -> exit();
                default -> System.out.println(ANSI_RED + "Invalid choice, please try again!" + ANSI_RESET);
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
        System.out.println("1. Sort product from price low to high");
        System.out.println("2. Sort product from price high to low");
        System.out.println("B. Go back");
        System.out.println("E. Exit");
    }

    public void sortProductByPriceMenu() {
        BaseHelper.clearConsole();
        BaseHelper.simpleTable(productTableGenerator(lstProduct));
        printSortProductByPrice();
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> Utils.productService.sortProductByPrice("asc");
                case "2" -> Utils.productService.sortProductByPrice("desc");
                case "B", "b" -> {
                    return;
                }
                case "E", "e" -> exit();
                default -> System.out.println(ANSI_RED + "Invalid choice, please try again!" + ANSI_RESET);
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
        System.out.println("6. View revenue");
        System.out.println("7. Log out");
        System.out.println("E. exit");
        System.out.print("Your choice: ");
    }

    private void adminMenu() {
        BaseHelper.clearConsole();

        printAdminMenu();
        try {
            String choice = Utils.reader.readLine();

            switch (choice) {
                case "1":
                    Utils.productService.addProduct();
                    break;
                case "2":
                    Utils.productService.manageProductPrice();
                    break;
                case "3":
                    Utils.orderService.viewOrderByCustomerId();
                    break;
                case "4":
                    Utils.orderService.manageOrderStatus();
                    break;
                case "5":
                    Utils.productService.deleteProduct();
                    break;
                case "6":
                    revenueMenu();
                    break;
                case "7":
                    Utils.userService.logout();
                    return;
                case "E", "e":
                    exit();
                default:
                    System.out.println(ANSI_RED + "Invalid choice, please try again!" + ANSI_RESET);
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

    public void revenueMenu() throws IOException {
        LocalDate targetDate = null;

        System.out.println("Please choose actions:");
        System.out.println("1. See revenue today");
        System.out.println("2. See revenue specific day");
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> {
                    orderService.revenueTodayMenu();
                    break;
                }
                case "2" -> orderService.revenueSpecificDayMenu();
                case "b", "B" -> {
                    return;
                }

                default -> {
                    System.out.println(ANSI_RED + "Invalid input! Please try again" + ANSI_RESET);
                    System.out.println();
                    revenueMenu();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void exit() throws FileNotFoundException {
        System.out.println("See you again!");
        BaseHelper.writeData();
        System.exit(-1);
    }

}