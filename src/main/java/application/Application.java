package application;

import Service.MenuService;
import Service.UserService;
import common.BaseHelper;
import common.Utils;

public class Application {

    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        UserService userService = new UserService();

        userService.loadData();
        System.out.println(Utils.lstMember);
        BaseHelper.printWelcomePage();
        menuService.mainMenu();
    }

}
