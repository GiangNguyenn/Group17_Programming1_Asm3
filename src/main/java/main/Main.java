package main;

import Service.ProductService;
import common.BaseHelper;
import common.Utils;

import java.io.IOException;

import static common.BaseHelper.*;
import static common.Utils.*;


public class Main {

    public static void main(String[] args) throws IOException {

        BaseHelper.loadData();
        BaseHelper.printWelcomePage();
//        Utils.menuService.mainMenu();
        ProductService productService = new ProductService();
//        productService.viewOrderDetails();
//        BaseHelper.productTable(lstProduct);
//        BaseHelper.simpleTable(memberTableGenerator(lstMember));
//        BaseHelper.simpleTable(adminTableGenerator(lstAdmin));
//        BaseHelper.simpleTable(productTableGenerator(lstProduct));
//        BaseHelper.simpleTable(orderTableGenerator(lstOrder));
        menuService.sortProductByPriceMenu();

    }
}