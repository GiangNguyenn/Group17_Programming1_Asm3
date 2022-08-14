package common;

import java.util.*;
import java.util.stream.Collectors;

import Model.User.Member;
import Model.User.User;

import static common.Utils.lstMember;
import static java.lang.Integer.parseInt;

public class BaseHelper {

    public static void printWelcomePage() {
        // Todo prin welcome page in item C
        System.out.println("********* Here is welcome page *********");
    }

    public static Boolean isLogin() {
        return Utils.isLogin;
    }

    public static User getCurrentUser() {
        return Utils.current_user;
    }

    public static Member getMemberByUserName(String userName) {
        // TODO get a member from lstMember with userName
        return new Member(null, null, null, null, null);
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object value) {
        if (value == null) {
            return true;
        }

        if (value instanceof String) {
            return "".equals(value.toString().trim());
        } else if (value instanceof Collection) {
            Collection c = (Collection) value;
            return c.isEmpty();
        } else if (value instanceof Map) {
            Map m = (Map) value;
            return m.isEmpty();
        } else if (value.getClass().isArray()) {
            Object[] array = (Object[]) value;
            return (array.length <= 0);
        } else if (value instanceof Class && ((Class) value).isPrimitive()) {
            long temp = (long) value;
            return temp < 0;
        } else {
            return false;
        }
    }

    public static Boolean checkingUserLoginInfo(String username, String password) {
        /*
         * TODO checking infor of an Member.
         * return true if username and password are correct
         */
        Optional<Member> matchedUser = lstMember.stream().filter(user -> Objects.equals(user.getPassword(), password) && Objects.equals(user.getUsername(), username)).findFirst();

        return matchedUser.isPresent();
    }

    public static String generateUniqueId() {
        // TODO creating an id of Member, it must be not exist in lstMember
        List<Integer> idArray = lstMember.stream().map(user -> Integer.valueOf(user.getId())).toList();
        Integer maxId = Collections.max(idArray);
        return String.valueOf(maxId + 1);
    }

    public static boolean checkExistUsername(String username) {
		/*
		TODO check an id of Member
		return true if exist it in lstMember
		*/
        return lstMember.stream().map(User::getUsername).anyMatch(username::equals);
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
