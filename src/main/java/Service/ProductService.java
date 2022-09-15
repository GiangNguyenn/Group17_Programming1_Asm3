package Service;

import Model.Productions.Order;
import Model.Productions.Product;
import common.BaseConstant;
import common.BaseHelper;
import common.Utils;
import interfaces.ProductInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static common.BaseConstant.*;
import static common.BaseHelper.orderTableGenerator;
import static common.BaseHelper.productTableGenerator;
import static common.Utils.lstOrder;
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
            System.out.println(ANSI_RED + "The List is empty" + ANSI_RESET);
        } else {
            for (Product product : lstProduct) {
                out.printf("%s,%s,%s,%s,%s\n", product.getId(), product.getProductName(), product.getPrice(),
                        product.getCategory(), product.getSupplier());
            }
        }
        out.close();
    }

    @Override
    public void showAllProduct() {
        BaseHelper.simpleTable(BaseHelper.productTableGenerator(lstProduct));
    }

    public void showProductsByCategory() throws IOException {
        printListOfCategories();
        List<Product> searchedProducts = new ArrayList<>();
        System.out.println(BLUE_BOLD + "Note: Type 'B' to go back." + ANSI_RESET);
        System.out.println("Input categories: ");
        String categoryInput = Utils.reader.readLine();
        if (categoryInput.equalsIgnoreCase("B")) {
            return;
        }
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
    public void viewOrderDetails() throws IOException {
        System.out.println(BLACK_BOLD + "List of order" + ANSI_RESET);
        BaseHelper.simpleTable(orderTableGenerator(lstOrder));
        System.out.println("Enter the id of the order you want to view: ");
        Pattern p = Pattern.compile("^[0-9]+$");
        boolean notMatchedRegex = true;
        while (notMatchedRegex) {
            String orderId = Utils.reader.readLine();
            Order searchOrder = BaseHelper.getOrderByOrderId(orderId);
            if (!BaseHelper.isNullOrEmpty(searchOrder) && p.matcher(orderId).find()) {
                BaseHelper.simpleTable(BaseHelper.orderTableGenerator(BaseHelper.addSingleObjectToList(searchOrder)));
                notMatchedRegex = false;
            } else {
                System.out.println(ANSI_RED + "The your input is in the correct format! Please re-enter: " + ANSI_RESET);
            }
        }
    }

    @Override
    public void manageProductPrice() throws IOException {
        BaseHelper.simpleTable(productTableGenerator(lstProduct));
        System.out.println("Enter the id of the product you want to change the price of: ");

        while (true) {
            System.out.println("Type B to go back!");
            String productId = Utils.reader.readLine();
            if (productId.equalsIgnoreCase("b")) {
                return;
            }
            Product searchProduct = BaseHelper.getProductByProductId(productId);
            if (!BaseHelper.isNullOrEmpty(searchProduct)) {
                changeProductPrice(searchProduct);
                break;
            } else {
                System.out.println(ANSI_RED + "The id you entered is not correct! Please re-enter: " + ANSI_RESET);
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
                System.out.println(GREEN_BOLD + "Product price changed successfully!" + ANSI_RESET);
                System.out.println("The new Product price is: " + searchedProduct.getPrice() + " VND");
                notMatchedRegex = false;
            } else {
                System.out.println(ANSI_RED + "The your input must be larger than 1000(VND) and in the correct format! Please re-enter: " + ANSI_RESET);
            }
        }
    }

    public void sortProductByPrice(String sortFunction) throws IOException {
        if (sortFunction.equals("asc")) {
            List<Product> ascProductList = lstProduct.stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .collect(Collectors.toList());
            BaseHelper.simpleTable(productTableGenerator(ascProductList));
        } else if (sortFunction.equals("desc")) {
            List<Product> descProductList = lstProduct.stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .collect(Collectors.toList());
            BaseHelper.simpleTable(productTableGenerator(descProductList));
        }
    }

    @Override
    public void addProduct() throws IOException {
        boolean productExists = false;
        BufferedReader reader = Utils.reader;
//      Supposing the product has not existed in the database, allow user to enter new product
        while (!productExists) {
            System.out.println("Enter product name: ");
            String productName = reader.readLine();
            System.out.println("Enter price: ");
            String price = reader.readLine();
            System.out.println("Enter category: ");
            String category = reader.readLine();
            System.out.println("Enter supplier: ");
            String supplier = reader.readLine();
//      If the product has EXISTED in the database, display error message in red color
            if (BaseHelper.checkExistProduct(productName, supplier)) {
                System.out.println(ANSI_RED + productName + " of supplier " + supplier + " has already been added! Please add another product" + ANSI_RESET);
                productExists = true;
            } else {
//      If the product has NOT EXISTED in the database, insert the new product into the database
//      Id is auto-generated using generateUniqueId
                String id = BaseHelper.generateUniqueId(Product.class);
                lstProduct.add(new Product(id, productName, Double.parseDouble(price), category, supplier));
                System.out.println(lstProduct);
                break;
            }
        }
    }

    public void deleteProduct() {
        try {
            BaseHelper.simpleTable(BaseHelper.productTableGenerator(lstProduct));
            System.out.println("Please input Product ID you want to delete:");
            String userInput = Utils.reader.readLine();
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
