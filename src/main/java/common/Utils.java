package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Model.User.Member;
import Model.User.User;
import common.BaseConstant.TypeMember;

public  class Utils {
    //login infor
    public static Boolean isLogin = false;
    public static User current_user = null;


    // Data in use
    public static HashMap<TypeMember, Float> promotionHash = new HashMap<TypeMember, Float>() {

        private static final long serialVersionUID = 1L;
        {
            put(TypeMember.NOMAL, (float) 1.0);
            put(TypeMember.SILVER, (float) 0.95);
            put(TypeMember.GOLD, (float) 0.9);
            put(TypeMember.PLATINUM, (float) 0.85);
        }
    };

    /* Adding user to lstMember after registering successfully */
    public static List<Member> lstMember = new ArrayList<Member>();


    // Some tools useful
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static Random rand = new Random();


}