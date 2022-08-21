package main;

import common.BaseHelper;
import common.Utils;


public class Main {

    public static void main(String[] args) {

        BaseHelper.loadData();
        // BaseHelper.printWelcomePage();
        // Utils.menuService.mainMenu();
        String [][] table = BaseHelper.tableGenerator();
        BaseHelper.simpleTable(table);
    }
}