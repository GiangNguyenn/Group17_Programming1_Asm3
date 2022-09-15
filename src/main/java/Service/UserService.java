package Service;


import Model.User.Admin;
import Model.User.Member;
import common.BaseConstant;
import common.BaseHelper;
import common.RegexConstants;
import common.Utils;
import interfaces.UserInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import static common.Utils.lstAdmin;
import static common.Utils.lstMember;

public class UserService implements UserInterface {

    private static UserService INSTANT;

    public static void start() {
        INSTANT = new UserService();
    }

    public static UserService getInstant() {
        return INSTANT;
    }

    /**
     * @throws IOException
     */
    @Override
    public void login() throws IOException {

        if (BaseHelper.isLogin()) {
            System.out.println("This user has been logged.");
            return;
        }
        System.out.println(BaseHelper.BLUE_BOLD + "Note: Type 'B' in any input to go back." + BaseHelper.ANSI_RESET);

        System.out.println(BaseHelper.BLACK_BOLD + "Process login:" + BaseHelper.ANSI_RESET);
        System.out.println("Username: ");
        String username = Utils.reader.readLine();
        if (username.equals("B")) {
            return;
        }
        System.out.println("Password: ");
        String password = Utils.reader.readLine();
        if (password.equals("B")) {
            return;
        }


        Member member = BaseHelper.getMemberByUserName(username);

        Admin admin = BaseHelper.getAdminByUserName(username);

        if (!BaseHelper.isNullOrEmpty(member) && BaseHelper.checkingMemberLoginInfo(username, password)) {
            Utils.isLogin = true;
            Utils.current_user = member;
            member.updateMemberInfo();
            System.out.println(BaseHelper.GREEN_BOLD + "Login success! " + member + BaseHelper.ANSI_RESET);
            BaseHelper.clearConsole();
            System.out.println(BaseHelper.YELLOW_BOLD + "Login success! Hello " + member.getName() + BaseHelper.ANSI_RESET);
            return;
        }

        if (!BaseHelper.isNullOrEmpty(admin) && BaseHelper.checkingAdminLoginInfo(username, password)) {
            Utils.isLogin = true;
            Utils.isAdmin = true;
            return;
        }

        System.out.println("Login fail, please try again! ");
    }

    @Override
    public void register() throws IOException {
        // TODO: process get input to login
        boolean userExists = false;

        while (!userExists) {
            System.out.println(BaseHelper.YELLOW_BOLD + "Welcome! Please register your account to start purchasing." + BaseHelper.ANSI_RESET);
            System.out.println(BaseHelper.BLUE_BOLD + "Note: Type 'B' in any input to go back." + BaseHelper.ANSI_RESET);
            boolean notMatchedRegex = true;
            while (notMatchedRegex) {
                System.out.println("Enter your name: ");
                String name = Utils.reader.readLine();
                if (name.equalsIgnoreCase("B")) {
                    return;
                }

                System.out.println("Enter your phone number: ");
                String phoneNumber = Utils.reader.readLine();
                if (phoneNumber.equalsIgnoreCase("B")) {
                    return;
                }
                System.out.println("Enter your username: ");
                String username = Utils.reader.readLine();
                if (username.equalsIgnoreCase("B")) {
                    return;
                }
                System.out.println("Enter your password: ");
                String password = Utils.reader.readLine();
                if (password.equalsIgnoreCase("B")) {
                    return;
                }

                if (!BaseHelper.validateUserInput(name, RegexConstants.NAME_REGEX)) {
                    System.out.println(BaseHelper.ANSI_RED + "Name must be longer than 2 character and contains only letters!" + BaseHelper.ANSI_RESET);
                    System.out.println(BaseHelper.ANSI_RED + "Try register again." + BaseHelper.ANSI_RESET);
                    register();
                } else if (BaseHelper.validateUserInput(username, RegexConstants.USERNAME_REGEX)) {
                    System.out.println(BaseHelper.ANSI_RED + "Username contains minimum of 2 letters!" + BaseHelper.ANSI_RESET);
                    System.out.println(BaseHelper.ANSI_RED + "Try register again." + BaseHelper.ANSI_RESET);
                    register();
                } else if (BaseHelper.validateUserInput(password, RegexConstants.PASSWORD_REGEX)) {
                    System.out.println(BaseHelper.ANSI_RED + "Password contains minimum of 2 letters!" + BaseHelper.ANSI_RESET);
                    System.out.println(BaseHelper.ANSI_RED + "Try register again." + BaseHelper.ANSI_RESET);
                    register();
                } else if (BaseHelper.validateUserInput(phoneNumber, RegexConstants.PHONE_REGEX)) {
                    System.out.println(BaseHelper.ANSI_RED + "Password contains minimum of 2 letters!" + BaseHelper.ANSI_RESET);
                    System.out.println(BaseHelper.ANSI_RED + "Try register again." + BaseHelper.ANSI_RESET);
                    register();
                } else {
                    if (BaseHelper.checkExistUsername(username)) {
                        System.out.println(BaseHelper.ANSI_RED + "This username has been used! Please register with another one." + BaseHelper.ANSI_RESET);
                        userExists = true;
                    } else {
                        String id = BaseHelper.generateUniqueId(Member.class);
                        lstMember.add(new Member(id, name, phoneNumber, username, password));
                        System.out.println(lstMember);
                        break;
                    }
                    notMatchedRegex = false;
                }
            }


        }
        /*
         * TODO: Checking exits username
         * creating new Member if this username is not exits
         * after that, switch to login page
         * */
    }

    @Override
    public void logout() {
        Utils.isLogin = false;
        Utils.current_user = null;
        System.out.println(BaseHelper.BLUE_BOLD + "You have logged out!" + BaseHelper.ANSI_RESET);
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
            System.out.println(BaseHelper.ANSI_RED + "The List is empty or null" + BaseHelper.ANSI_RESET);
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
