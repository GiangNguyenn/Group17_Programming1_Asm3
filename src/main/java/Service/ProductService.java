package Service;

import Model.Productions.Product;
import common.BaseConstant;
import common.BaseHelper;
import common.Utils;
import interfaces.ProductInterface;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import static common.BaseConstant.PRODUCT_DATA_PATH;
import static common.Utils.lstProduct;
import static common.Utils.reader;

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

    public void manageProductPrice() throws IOException {
        System.out.println("========================================================");
        System.out.printf("%20s%15s%15s", "   ID   ", "   Product's name   ", "   Product's price   ");
        System.out.println("");
        System.out.println("========================================================");

        for (Product product : lstProduct){
            System.out.printf("%20s%15s%15s", "   " + product.getId() + "   ", "   " + product.getProductName() + "   ", "   " + product.getPrice() + "$");
            System.out.println("");
            System.out.println("========================================================");
        }
        System.out.println("Enter the id of the product you want to change the price of: ");
        Pattern p = Pattern.compile("^[0-9]+$");
        while (true){
            String productId = Utils.reader.readLine();
            Product searchProduct = BaseHelper.getProductByProductId(productId);
            if(p.matcher(productId).find()){
                changeProductPrice(productId);
                break;
            }else {
            System.out.println("The id you entered is not correct! Please re-enter: ");
            }
        }
        writeData();
    }
    public Double changeProductPrice(String input) throws IOException {
        String productId = input;
        Product searchProduct = BaseHelper.getProductByProductId(productId);
        System.out.println("The current Price of the product is: " + searchProduct.getPrice());
        System.out.println("============================");
        System.out.println("Enter your desire product's price: ");
        Pattern p = Pattern.compile("^[0-9]+$");
        while (true){
            Double newPrice = Double.valueOf(Utils.reader.readLine());
            if(p.matcher(productId).find()){
                searchProduct.setPrice(newPrice);
                System.out.println("The new Product details are: " + searchProduct.getPrice());
                return searchProduct.setPrice(newPrice);
            }else {
                System.out.println("The entered the amount in a wrong format! Please re-enter: ");
            }
        }
    }
    @Override
    public void addProduct() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean productExists = false;

        while (!productExists) {
            System.out.println("Enter product name: ");
            String productName = scanner.nextLine();
            System.out.println("Enter price: ");
            String price = scanner.nextLine();
            System.out.println("Enter category: ");
            String category = scanner.nextLine();
            System.out.println("Enter supplier: ");
            String supplier = scanner.nextLine();

            if (BaseHelper.checkExistProduct(productName, supplier)) {
                System.out.println(productName + " of supplier " + supplier + " has been added! Please add another product");
                productExists = true;
            } else {
                String id = BaseHelper.generateIdForProduct();
                lstProduct.add(new Product(id, productName, Double.parseDouble(price), category, supplier));
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
