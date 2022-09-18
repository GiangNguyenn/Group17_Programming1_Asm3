package Service;

import Model.User.Admin;
import Model.User.Member;
import common.BaseConstant;
import common.BaseHelper;
import common.Utils;
import interfaces.UserInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import static common.BaseConstant.*;
import static common.Utils.lstAdmin;
import static common.Utils.lstMember;

public class UserService implements UserInterface {

    private static UserService INSTANCE;

    public static void start() {
        INSTANCE = new UserService();
    }

    public static UserService getInstant() {
        return INSTANCE;
    }

    /**
     * @throws IOException
     */
    @Override
    public void login() throws IOException {
        //Strings initialization
        String username;
        String password;

        if (BaseHelper.isLogin()) {
            System.out.println(ANSI_RED + "You are currently login!" + ANSI_RESET);
            return;
        }
        System.out.println(BLUE_BOLD + "Note: Type 'B' in any input to go back." + ANSI_RESET);

        //prompt user to input login credentials
        System.out.println(BLACK_BOLD + "Process login:" + ANSI_RESET);
        System.out.println("Username: ");
        username = Utils.reader.readLine();
        if (username.equalsIgnoreCase("B")) {
            return;
        }
        System.out.println("Password: ");
        password = Utils.reader.readLine();
        if (password.equalsIgnoreCase("B")) {
            return;
        }

        //Get either admin or member using the provided username
        Member member = BaseHelper.getMemberByUserName(username);
        Admin admin = BaseHelper.getAdminByUserName(username);

        //if there is a member associated with that username -> check if password matched -> login or print login fail error
        if (!BaseHelper.isNullOrEmpty(member) && BaseHelper.checkingMemberLoginInfo(username, password)) {
            Utils.isLogin = true;
            BaseHelper.setCurrentUser(member);
            member.updateMemberInfo();
            System.out.println(YELLOW_BOLD + "Login success! Hello " + member.getName() + ANSI_RESET);
            BaseHelper.clearConsole();
            return;
        }

        if (!BaseHelper.isNullOrEmpty(admin) && BaseHelper.checkingAdminLoginInfo(username, password)) {
            Utils.isLogin = true;
            Utils.isAdmin = true;
            return;
        }

        System.out.println(ANSI_RED + "Login fail, please try again! " + ANSI_RESET);
    }

    @Override
    public void register() throws IOException {
        // TODO: process get input to login
        boolean userExists = false;
        String name;
        String phoneNumber;
        String username;
        String password;

        while (!userExists) {
            System.out.println(YELLOW_BOLD + "Welcome! Please register your account to start purchasing." + ANSI_RESET);
            System.out.println(BLUE_BOLD + "Note: Type 'B' in any input to go back." + ANSI_RESET);
            boolean notMatchedRegex = true;
            while (notMatchedRegex) {
                System.out.print("Enter your name: ");
                name = Utils.reader.readLine();
                if (name.equalsIgnoreCase("B")) {
                    return;
                }

                System.out.print("Enter your phone number: ");
                phoneNumber = Utils.reader.readLine();
                if (phoneNumber.equalsIgnoreCase("B")) {
                    return;
                }
                System.out.print("Enter your username: ");
                username = Utils.reader.readLine().toLowerCase();
                if (username.equalsIgnoreCase("B")) {
                    return;
                }
                System.out.print("Enter your password: ");
                password = Utils.reader.readLine();
                if (password.equalsIgnoreCase("B")) {
                    return;
                }

                /* creating new Member if this username is not exits
                 * after that, switch to login page
                 * */
                if (BaseHelper.checkExistUsername(username)) {
                    System.out.println(ANSI_RED + "This username has been used! Please register with another one." + ANSI_RESET);
                    userExists = true;
                } else {
                    String id = BaseHelper.generateUniqueId(Member.class);
                    lstMember.add(new Member(id, name, phoneNumber, username, password));
                    System.out.println(new Member(id, name, phoneNumber, username, password));
                    return;
                }
                notMatchedRegex = false;
            }
        }

    }

    //logout by setting all authentication flags to false, set currentUser back to null
    @Override
    public void logout() {
        Utils.isLogin = false;
        Utils.current_user = null;
        Utils.isAdmin = false;
        System.out.println(BLUE_BOLD + "You have logged out!" + ANSI_RESET);
    }

    @Override
    public void loadData() {
        try {
            BufferedReader userData = Utils.fileReader(BaseConstant.USER_DATA_PATH);
            String dataRow;
            String id;
            String name;
            String phone;
            String userName;
            String password;
            String adminId;
            String adminUsername;
            String adminPassword;
            while ((dataRow = userData.readLine()) != null) {
                String[] detailed = dataRow.split(",");
                if (detailed.length == 5) {
                    id = detailed[0];
                    name = detailed[1];
                    phone = detailed[2];
                    userName = detailed[3];
                    password = detailed[4];
                    lstMember.add(new Member(id, name, phone, userName, password));
                } else {
                    adminId = detailed[0];
                    adminUsername = detailed[1];
                    adminPassword = detailed[2];
                    lstAdmin.add(new Admin(adminId, adminUsername, adminPassword));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeData() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(BaseConstant.USER_DATA_PATH);
        if (BaseHelper.isNullOrEmpty(lstMember) || BaseHelper.isNullOrEmpty(lstAdmin)) {
            System.out.println(ANSI_RED + "The List is empty or null" + ANSI_RESET);
        } else {
            for (Member member : lstMember) {
                out.printf("%s,%s,%s,%s,%s\n", member.getId(), member.getName(), member.getPhoneNumber(), member.getUsername(), member.getPassword());
            }
            for (Admin admin : lstAdmin) {
                out.printf("%s,%s,%s\n", admin.getId(), admin.getUsername(), admin.getPassword());
            }
        }
        out.close();
    }

    //display the user's details of the current user
    @Override
    public void printUserProfile(Member currentUser) {
        if (Utils.isLogin) {
            System.out.println("My profile");
            System.out.println("Name: " + currentUser.getName());
            System.out.println("Phone Number: " + currentUser.getPhoneNumber());
            System.out.println("Total Spending: " + (currentUser.getTotalSpending() == null ? "0" : currentUser.getTotalSpending()));
            System.out.println("Member Type: " + (currentUser.getMemberType() == null ? "NORMAL" : currentUser.getMemberType()));
        }
    }
}