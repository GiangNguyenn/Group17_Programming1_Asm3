package Service;


import java.io.*;
import java.util.Scanner;

import Model.User.Admin;
import Model.User.Member;
import common.BaseConstant;
import common.BaseHelper;
import common.Utils;
import interfaces.UserInterface;

import static common.Utils.lstAdmin;
import static common.Utils.lstMember;

public class UserService implements UserInterface {

    /**
     * @throws IOException
     */
    @Override
    public void login() throws IOException {

        if (BaseHelper.isLogin()) {
            System.out.println("This user has been logged.");
            return;
        }

        System.out.println("Process login:");
        System.out.println("Username: ");
        String username = Utils.reader.readLine();
        System.out.println("Password: ");
        String password = Utils.reader.readLine();

        Member member = BaseHelper.getMemberByUserName(username);

        Admin admin = BaseHelper.getAdminByUserName(username);

        if (!BaseHelper.isNullOrEmpty(member) && BaseHelper.checkingMemberLoginInfo(username, password)) {
            Utils.isLogin = true;
            Utils.current_user = member;
            System.out.println("Login success! " + member);
            System.out.println(Utils.current_user);
            return;
        }

        if (!BaseHelper.isNullOrEmpty(admin) && BaseHelper.checkingAdminLoginInfo(username, password)) {
            Utils.isLogin = true;
            Utils.isAdmin = true;
            Utils.current_user = admin;
            System.out.println(Utils.current_user);
            return;
        }

        System.out.println("Login fail, please try again! ");
    }

    @Override
    public void register() throws IOException {
        // TODO: process get input to login
        Scanner scanner = new Scanner(System.in);
        boolean userExists = false;

        while (!userExists) {
            System.out.println("Enter your name: ");
            String name = scanner.nextLine();
            System.out.println("Enter your phone number: ");
            String phoneNumber = scanner.nextLine();
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            if (BaseHelper.checkExistUsername(username)) {
                System.out.println("This username has been used! Please register with another one.");
                userExists = true;
            } else {
                String id = BaseHelper.generateIdForUser();
                lstMember.add(new Member(id, name, phoneNumber, username, password));
                System.out.println(lstMember);
                break;
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
        System.out.println("You have logged out!");
    }

    @Override
    public void loadData() {
        try {
            BufferedReader userData = Utils.fileReader(BaseConstant.USER_DATA_PATH);
            String dataRow;

            while ((dataRow = userData.readLine()) != null) {
                String[] detailed = dataRow.split(",");
                if (detailed.length == 5) {
                    String id = detailed[0];
                    String name = detailed[1];
                    String phone = detailed[2];
                    String userName = detailed[3];
                    String password = detailed[4];
                    lstMember.add(new Member(id, name, phone, userName, password));
                } else {
                    String adminId = detailed[0];
                    String adminUsername = detailed[1];
                    String adminPassword = detailed[2];
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
            System.out.println("The List is empty or null");
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

    public static void printUserProfile(Member currentUser) {
        if (Utils.isLogin) {
            System.out.println("My profile");
            System.out.println("Name: " + currentUser.getName());
            System.out.println("Phone Number: " + currentUser.getPhoneNumber());
            System.out.println("Total Spending: " + (currentUser.getTotalSpending() == null ? "0" : currentUser.getTotalSpending()));
            System.out.println("Member Type: " + (currentUser.getMemberType() == null ? "NORMAL" : currentUser.getMemberType()));
        }
    }
}
