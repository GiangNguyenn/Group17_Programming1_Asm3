package Service;

import java.io.IOException;

import common.BaseHelper;
import common.Utils;

public class MenuService {

    public static void printMainMenu() {
		// Todo: Print main menu
        System.out.println("Select function: ");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Your choice: ");
	}

    public void mainMenu() {
        BaseHelper.clearConsole();
        UserService userService = new UserService();
        printMainMenu();
        try {
            String choice = Utils.reader.readLine();
            
            switch(choice) {
                case "1":
                    userService.login();
                    break;
                case "2":
                    userService.register();
                    break;
                case "3":
                    exit();
                default:
                    System.out.println("Invalid choice, please try again!");
            }
            System.out.println("press enter to continue...");
            Utils.reader.read();
            mainMenu();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void exit() {
        UserService userService = new UserService();
        userService.writeData();
    }

}
