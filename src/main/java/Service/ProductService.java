package Service;

import Model.Productions.Product;
import common.BaseConstant;
import common.BaseHelper;
import common.Utils;
import interfaces.ProductInterface;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static common.BaseConstant.PRODUCT_DATA_PATH;
import static common.Utils.lstProduct;

public class ProductService implements ProductInterface {

    private static ProductService INSTANT;

    public static void start() {
        INSTANT = new ProductService();
    }

    public static ProductService getInstant() {
        return INSTANT;
    }

    @Override
    public void loadData() {
        // TODO Auto-generated method stub
        try {
            BufferedReader productData = new BufferedReader(new FileReader(BaseConstant.PRODUCT_DATA_PATH));
            String record;
            String[] detailed;
            String id;
            String productName;
            double price;
            String category;
            String supplier;
            while ((record = productData.readLine()) != null) {
                detailed = record.split(",");
                id = detailed[0];
                productName = detailed[1];
                price = Double.parseDouble(detailed[2]);
                category = detailed[3];
                supplier = detailed[4];
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
        boolean ans = lstProduct.isEmpty();
        if (ans) {
            System.out.println("The List is empty");
        } else {
            for (Product product : lstProduct) {
                out.printf("%s,%s,%s,%s,%s\n", product.getId(), product.getProductName(), product.getPrice(), product.getCategory(), product.getSupplier());
            }
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


    private static void printListOfCategories() {
        List<String> uniqueCategories = lstProduct.stream().map(Product::getCategory).distinct().toList();
        for (int i = 0; i < uniqueCategories.size(); i++) {
            System.out.println(i + " " + uniqueCategories.get(i));
        }
    }

    // TODO
    public void viewOrderDetails() {

    }

    public void manageProductPrice() throws IOException {
        System.out.println("========================================================");
        System.out.printf("%20s%15s%15s", "   ID   ", "   Product's name   ", "   Product's price   ");
        System.out.println("");
        System.out.println("========================================================");

        for (Product product : lstProduct) {
            System.out.printf("%20s%15s%15s", "   " + product.getId() + "   ", "   " + product.getProductName() + "   ", "   " + product.getPrice() + "$");
            System.out.println("");
            System.out.println("========================================================");
        }
        System.out.println("Enter the id of the product you want to change the price of: ");

        while (true) {
            String productId = Utils.reader.readLine();
            Product searchProduct = BaseHelper.getProductByProductId(productId);
            if (!BaseHelper.isNullOrEmpty(searchProduct)) {
                changeProductPrice(searchProduct);
                break;
            } else {
                System.out.println("The id you entered is not correct! Please re-enter: ");
            }
        }
        writeData();
    }

        private void changeProductPrice(Product searchedProduct) throws IOException {
        System.out.println("The current Price of the product is: " + searchedProduct.getPrice());
        System.out.println("============================");
        System.out.println("Enter your desire product's price: ");
        Pattern p = Pattern.compile("^[1-9][0-9]{3,}$");
        boolean notMatchedRegex = true;
        while (notMatchedRegex) {
            String newPrice = String.valueOf(Utils.reader.readLine());
            if (p.matcher(newPrice).find()) {
                searchedProduct.setPrice(Double.parseDouble(newPrice));
                System.out.println("The new Product price is: " + searchedProduct.getPrice() + " VND");
                notMatchedRegex = false;
            } else {
                System.out.println("The your input must be larger than 1000(VND) and in the correct format! Please re-enter: ");
            }
        }
    }

    public void sortProductByPrice(String input) throws IOException {
        BaseHelper.productTable(lstProduct);
        List<Product> ascProductList = lstProduct.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
        List<Product> descProductList = lstProduct.stream().sorted(Comparator.comparing(Product::getPrice).reversed()).collect(Collectors.toList());
        if(input == "1"){
        BaseHelper.productTable(ascProductList);
        } else if (input == "2") {
        BaseHelper.productTable(descProductList);
        }
    }

    @Override
    public void addProduct() throws IOException {
        boolean productExists = false;

        BufferedReader reader = Utils.reader;

        while (!productExists) {
            System.out.println("Enter product name: ");
            String productName = reader.readLine();
            System.out.println("Enter price: ");
            String price = reader.readLine();
            System.out.println("Enter category: ");
            String category = reader.readLine();
            System.out.println("Enter supplier: ");
            String supplier = reader.readLine();

            if (BaseHelper.checkExistProduct(productName, supplier)) {
                System.out.println(productName + " of supplier " + supplier + " has already been added! Please add another product");
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
