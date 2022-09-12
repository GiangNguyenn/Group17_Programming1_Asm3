package main;

import common.BaseHelper;
import common.Utils;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        BaseHelper.loadData();
        BaseHelper.printWelcomePage();
        Utils.menuService.startUpMenu();
    }
}