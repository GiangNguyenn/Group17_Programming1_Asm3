package Service;

import Model.Productions.Product;
import common.BaseConstant;
import common.BaseHelper;
import interfaces.ProductInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static common.BaseConstant.PRODUCT_DATA_PATH;
import static common.Utils.lstProduct;

public class ProductService implements ProductInterface {
    @Override
    public void loadData() {
        // TODO Auto-generated method stub
        try {
            BufferedReader productData = new BufferedReader(new FileReader(BaseConstant.PRODUCT_DATA_PATH));
            String record;
            while ((record = productData.readLine()) != null) {
                String[] detailed = record.split(",");
                String id = detailed[0];
                String productName = detailed[1];
                Double price = Double.parseDouble(detailed[2]);
                String category = detailed[3];
                String supplier = detailed[4];
                lstProduct.add(new Product(id, productName, price, category, supplier));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeData() throws FileNotFoundException {
        // TODO Auto-generated method stub
        File productionCsvFile = new File(PRODUCT_DATA_PATH);
        File csvFile = new File(productionCsvFile.toURI());
        PrintWriter out = new PrintWriter(csvFile);
//        Product iphone = new Product("123", "iphone12", "122$", "phone", "samsung");
//        lstProduct.add(iphone);
        System.out.println(lstProduct);
        boolean ans = lstProduct.isEmpty();
        if (ans) System.out.println("The List is empty");
        else for (Product product : lstProduct) {
            out.printf("%s,%s,%s,%s,%s\n", product.getId(), product.getProductName(), product.getPrice(), product.getCategory(), product.getSupplier());
        }
        out.close();
    }

    @Override
    public void showAllProduct() {
        for (Product product : lstProduct) {
            System.out.println(product);
        }
    }

    public void showProductsByCategory() {
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

    @Override
    public void addProduct() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean productExists = false;

        while (!productExists) {
            System.out.println("Enter product name: ");
            String productName = scanner.nextLine();
            System.out.println("Enter price: ");
            Double price = scanner.nextDouble();
            System.out.println("Enter category: ");
            String category = scanner.nextLine();
            System.out.println("Enter supplier: ");
            String supplier = scanner.nextLine();

            if (BaseHelper.checkExistProduct(productName, supplier)) {
                System.out.println(productName + " of supplier " + supplier + " has been added! Please add another product");
                productExists = true;
            } else {
                String id = BaseHelper.generateIdForProduct();
                lstProduct.add(new Product(id, productName, price, category, supplier));
                System.out.println(lstProduct);
                break;
            }
        }
    }

    public void deleteProduct() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please input Product ID you want to delete:");
            String userInput = scanner.nextLine();
            for (int i = 0; i < lstProduct.size(); i++) {
                if (lstProduct.get(i).getId().equals(userInput)) {
                    lstProduct.remove(i);
                }
            }
            writeData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
