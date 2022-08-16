package common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Model.Productions.Order;
import Model.Productions.Product;
import Model.User.Admin;
import Model.User.Member;
import Model.User.User;
import common.BaseConstant.TypeMember;

public class Utils {
    //login infor
    public static Boolean isLogin = false;
    public static User current_user = null;
    public static boolean isAdmin = false;


    // Data in use
    public static HashMap<TypeMember, Float> promotionHash = new HashMap<TypeMember, Float>() {
        private static final long serialVersionUID = 1L;
        {
            put(TypeMember.NORMAL, (float) 1.0);
            put(TypeMember.SILVER, (float) 0.95);
            put(TypeMember.GOLD, (float) 0.9);
            put(TypeMember.PLATINUM, (float) 0.85);
        }
    };

    /* Adding user to lstMember after registering successfully */
    public static List<Admin> lstAdmin = new ArrayList<Admin>();

    /* Adding user to lstMember after registering successfully */
    public static List<Member> lstMember = new ArrayList<Member>();

    /* Adding products to lstProduct */
    public static List<Product> lstProduct = new ArrayList<Product>();

    /* Adding products to lstMember after registering successfully */
    public static List<Order> lstOrder = new ArrayList<Order>();

    // Some tools useful
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static BufferedReader fileReader(String filePath) throws FileNotFoundException {
        return new BufferedReader(new FileReader(filePath));
    }

    public static Random rand = new Random();
}