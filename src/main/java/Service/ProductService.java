package Service;

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
import static common.BaseHelper.productTableGenerator;
import static common.Utils.lstProduct;

public class ProductService implements ProductInterface {

    //ProductService singleton instance
    private static ProductService INSTANCE;

    public static void start() {
        INSTANCE = new ProductService();
    }

    public static ProductService getInstant() {
        return INSTANCE;
    }

    /**
     * Load Product data into the lstProduct ArrayList
     */
    @Override
    public void loadData() {
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

    /**
     * Write all Product objects in the lstProduct into the Product.csv file
     *
     * @throws FileNotFoundException
     */
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

    //Print out the list of product in table format
    @Override
    public void showAllProduct() {
        BaseHelper.simpleTable(BaseHelper.productTableGenerator(lstProduct));
    }

    //get user input and find products in the lstProduct that matched that category
    //then add them to a new ArrayList searchedProducts and then print them out
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
            BaseHelper.simpleTable(BaseHelper.productTableGenerator(searchedProducts));
        } else {
            System.out.println(ANSI_RED + "Wrong category input, please try again!");
        }
    }

    //print a list of all categories by mapping through lstProduct to find distinct categories exist
    private static void printListOfCategories() {
        List<String> uniqueCategories = lstProduct.stream().map(Product::getCategory).distinct().toList();
        for (String uniqueCategory : uniqueCategories) {
            System.out.println(uniqueCategory);
        }
    }

    //Manage the product price
    @Override
    public void manageProductPrice() throws IOException {
        //Display all the product in the table
        BaseHelper.simpleTable(productTableGenerator(lstProduct));
        //Ask the user to enter their desire product by enter the product's id
        System.out.println("Enter the id of the product you want to change the price of: ");
        while (true) {
            System.out.println("Type B to go back!");
            //Receive the product's id
            String productId = Utils.reader.readLine();
            //The user can choose "B" to go back
            if (productId.equalsIgnoreCase("b")) {
                return;
            }
            //Get a product by their id
            Product searchProduct = BaseHelper.getProductByProductId(productId);
            if (!BaseHelper.isNullOrEmpty(searchProduct)) {
                //If the id is valid, call the change product price method to change the price
                changeProductPrice(searchProduct);
                break;
            } else {
                //IF the is invalid, print out string below
                System.out.println(ANSI_RED + "The id you entered is not correct! Please re-enter: " + ANSI_RESET);
            }
        }
        writeData();
    }

    //Change a product's price
    private void changeProductPrice(Product searchedProduct) throws IOException {
        //Display the product's current price
        System.out.println("The current Price of the product is: " + searchedProduct.getPrice());
        System.out.println("============================");
        System.out.println("Enter your desire product's price: ");
        //Validate product's price input
        Pattern p = Pattern.compile("^[1-9][0-9]{3,}$");
        boolean notMatchedRegex = true;
        while (notMatchedRegex) {
            //If the input is validated, the product's will be change
            String newPrice = String.valueOf(Utils.reader.readLine());
            if (p.matcher(newPrice).find()) {
                searchedProduct.setPrice(Double.parseDouble(newPrice));
                System.out.println(GREEN_BOLD + "Product price changed successfully!" + ANSI_RESET);
                System.out.println("The new Product price is: " + searchedProduct.getPrice() + " VND");
                notMatchedRegex = false;
            } else {
                //If the price is invalid, the string will be printed
                System.out.println(ANSI_RED + "The your input must be larger than 1000(VND) and in the correct format! Please re-enter: " + ANSI_RESET);
            }
        }
    }

    //Filter product by price, high to low or low to high
    public void sortProductByPrice(String sortFunction) throws IOException {
        if (sortFunction.equals("asc")) {
            //If a user choose ascending option, the product will be display from low to high on a table
            List<Product> ascProductList = lstProduct.stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .collect(Collectors.toList());
            BaseHelper.simpleTable(productTableGenerator(ascProductList));
        } else if (sortFunction.equals("desc")) {
            //If a user choose descending option, the product will be display from high to low on a table
            List<Product> descProductList = lstProduct.stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .collect(Collectors.toList());
            BaseHelper.simpleTable(productTableGenerator(descProductList));
        }
    }

    //Create a new product
    @Override
    public void addProduct() throws IOException {
        boolean productExists = false;
        BufferedReader reader = Utils.reader;
        String productName;
        String price;
        String category;
        String supplier;

//      Supposing the product has not existed in the database, allow user to enter new product
        while (!productExists) {
            System.out.print("Enter product name: ");
            productName = reader.readLine();
            System.out.print("Enter price: ");
            price = reader.readLine();
            System.out.print("Enter category: ");
            category = reader.readLine();
            System.out.print("Enter supplier: ");
            supplier = reader.readLine();
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

    //Delete a single product from the database
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
