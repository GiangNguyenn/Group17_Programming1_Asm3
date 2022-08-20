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

    public void sortProductByPrice(){
        System.out.println("========================================================");
        System.out.printf("%20s%15s%15s", "   ID   ", "   Product's name   ", "   Product's price   ");
        System.out.println("");
        System.out.println("========================================================");

        for (Product product : lstProduct) {
            System.out.printf("%20s%15s%15s", "   " + product.getId() + "   ", "   " + product.getProductName() + "   ", "   " + product.getPrice() + "$");
            System.out.println("");
            System.out.println("========================================================");
        }
        System.out.println("1. Sort product from low to high");
        System.out.println("2. Sort product from high to low");
        try {
            String choice = Utils.reader.readLine();
            switch (choice) {
                case "1" -> ascendProductByPrice();
                case "2" -> descendProductByPrice();
                default -> System.out.println("Invalid choice, please try again!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ascendProductByPrice(){
        List<Product> ascendProductList = lstProduct.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
        System.out.println("========================================================");
        System.out.printf("%20s%15s%15s", "   ID   ", "   Product's name   ", "   Product's price   ");
        System.out.println("");
        System.out.println("========================================================");

        for (Product product : ascendProductList) {
            System.out.printf("%20s%15s%15s", "   " + product.getId() + "   ", "   " + product.getProductName() + "   ", "   " + product.getPrice() + "$");
            System.out.println("");
            System.out.println("========================================================");
        }
    }
    private void descendProductByPrice(){
        List<Product> descendProductList = lstProduct.stream().sorted(Comparator.comparing(Product::getPrice).reversed()).collect(Collectors.toList());

        System.out.println("========================================================");
        System.out.printf("%20s%15s%15s", "   ID   ", "   Product's name   ", "   Product's price   ");
        System.out.println("");
        System.out.println("========================================================");

        for (Product product : descendProductList) {
            System.out.printf("%20s%15s%15s", "   " + product.getId() + "   ", "   " + product.getProductName() + "   ", "   " + product.getPrice() + "$");
            System.out.println("");
            System.out.println("========================================================");
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

            Double priceDouble = Double.parseDouble(price);

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