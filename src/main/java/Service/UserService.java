package Service;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Model.User.Member;
import common.BaseConstant;
import common.BaseHelper;
import common.Utils;
import interfaces.UserInterface;

import static common.Utils.lstMember;

public class UserService implements UserInterface {

    @Override
    public void login() throws IOException {

        if (BaseHelper.isLogin()) {
            System.out.println("This user has been logged.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // TODO: process get input to login
        System.out.println("Process login:");
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();

        // TODO: declare new User

        Member member = new Member(username);


//		After successful login, set the flag in Utils to re-use.

//		ex:
        if (!BaseHelper.isNullOrEmpty(member) && member.checkingUserLoginInfo(username, password)) {

            for (Member validatedMember : lstMember) {
                if (member.getUsername().equals(username)) {
                    member = validatedMember;
                }
            }
            Utils.isLogin = true;
            Utils.current_user = member;
            System.out.println("Login success! " + member.toString());
            System.out.println(Utils.current_user);
            return;
        }

//		can be modified if you want.Ò
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

            if (BaseHelper.checkExistId(username)) {
                System.out.println("This username has been used! Please register with another one.");
                userExists = true;
            } else {
                String id = BaseHelper.generateIdForUser();
                lstMember.add(new Member(id, name, phoneNumber, username, password));
                System.out.println(lstMember);
            }
        }
        /*
         * TODO: Checking exitÒs username
         * creating new Member if this username is not exits
         * after that, switch to login page
         * */


    }

    @Override
    public void logout() {
        Utils.isLogin = false;
        Utils.current_user = null;
    }

    @Override
    public void loadData() {
        try {
            BufferedReader userData = new BufferedReader(new FileReader(BaseConstant.USER_DATA_PATH));
            String dataRow;

            while ((dataRow = userData.readLine()) != null) {
                String[] detailed = dataRow.split(",");
                String id = detailed[0];
                String name = detailed[1];
                String phone = detailed[2];
                String userName = detailed[3];
                String password = detailed[4];

                lstMember.add(new Member(id, name, phone, userName, password));
                System.out.println(lstMember);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeData() {

    }

}
