package common;

public class BaseConstant {
    public static final String App_Name = "ASM3";

    public enum TypeMember {NORMAL, SILVER, GOLD, PLATINUM}

    public static final Double LITMIT_SILVER = 5000000.0;

    public static final Double LITMIT_GOLD = 10000000.0;

    public static final Double LITMIT_PLATINUM = 25000000.0;


    // CSV files' Path
    public static final String USER_DATA_PATH = "src/main/resources/data/user.csv";
    public static final String PRODUCT_DATA_PATH = "src/main/resources/data/production.csv";
    public static final String ORDER_DATA_PATH = "src/main/resources/data/order.csv";

    //    Implement Color for better user experience
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
}
