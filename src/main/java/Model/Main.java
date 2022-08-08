package Model;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private String stage = "1";

    private int handleLimit() {
        if (stage.equals("1")) return 2;
        return 6;
    }
    /**
     * check valid user input
     * @param userInput is an integer
     * @return true if user input is valid and vice versa
     */
    private boolean isCorrectOption(int userInput) {
        if (userInput > 6) {
            System.err.println("You should input a valid choice from 1 to " + handleLimit() + '.');
            return false;
        }
        return true;
    }


    /**
     *
     * @param arrQuestions is a list of menu option that will be printed out on different lines
     */
    private void printMenu(String[] arrQuestions) {
        printSpace(40);
        System.out.println("\nPlease choose the options below:");
        handlePrintArray(arrQuestions);
        printSpace(40);

    }

    private void printSpace(int num) {
        System.out.println("");
        for (int i = 0; i < num; i++) System.out.print('*');

    }

    /**
     *
     * @param arrQuestions is a list of menu option that will be printed out on different lines
     */
    private void handlePrintArray(String[] arrQuestions) {
        for (String arrQuestion : arrQuestions) {
            System.out.println("* " + arrQuestion);
        }
    }


    private int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nYour Input: ");
        int userSelection = 0;
        try {
            userSelection = Integer.parseInt(scanner.nextLine());
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Your input is invalid. Please input a valid number option in displayed menu");
        }
        return userSelection;
    }

    public void consoleHandler() {
        while (true) {
            printOpts();
            int option = getUserInput();
            if (isCorrectOption(option) && option != 0) {
                stage = stage + "." + option;
            }
        }

    }

    public void printOpts() {

        if ("1".equals(stage)) {
            printMenu(new String[]{"1. Admin login", "2. Customer Sign up", "3. Customer Sign in", "4. Exit"});
        }}

    public static void main(String[] args) {
        Main main = new Main();
        main.consoleHandler();

    }

}


