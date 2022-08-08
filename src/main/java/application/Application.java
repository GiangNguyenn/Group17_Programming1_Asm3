package application;

import Service.MenuService;
import Service.UserService;
import common.BaseHelper;

public class Application {

	public static void main(String[] args) {
		MenuService menuService = new MenuService();
		UserService userService = new UserService();

		userService.loadData();
		BaseHelper.printWelcomePage();
		menuService.mainMenu();
	}

}
