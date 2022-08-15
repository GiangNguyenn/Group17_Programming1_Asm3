package Service;

import Model.Productions.Product;
import common.BaseHelper;
import interfaces.ProductInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static common.Utils.lstProduct;

public class ProductService implements ProductInterface {


    //    TODO: Loan
    @Override
    public void loadData() {

    }

    //    TODO: Loan
    @Override
    public void writeData() {

    }

    // TODO: Loan
    @Override
    public void showAllProduct() {
        for (Product product : lstProduct) {
            System.out.println(product);
        }
    }

    public static void showProductsByCategory() {
        printListOfCategories();
        List<Product> searchedProducts = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input categories: ");
        String categoryInput = scanner.nextLine();
        for (Product product : lstProduct) {
            if (Objects.equals(product.getCategory(), categoryInput)) {
                searchedProducts.add(product);
            }
        }
        if (!BaseHelper.isNullOrEmpty(searchedProducts)) {
            for (Product product : searchedProducts) {
                System.out.println(product);
            }
        }
    }

    public static void printListOfCategories() {
        List<String> uniqueCategories = lstProduct.stream().map(Product::getCategory).distinct().toList();
        for (int i = 0; i < uniqueCategories.size(); i++) {
            System.out.println(i + " " + uniqueCategories.get(i));
        }
    }

    public void viewOrderDetails() {
    }
}
