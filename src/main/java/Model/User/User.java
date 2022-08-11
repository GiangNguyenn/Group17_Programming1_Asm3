package Model.User;

public abstract class User {
    private String id;
    private String userName;
    private String password;
    private Boolean isAdmin;

    public Boolean checkingUserLoginInfo(String userName, String password) {
        /*
         * TODO checking infor of an Member.
         * return true if username and password are correct
         */

        return true;
    }

    public User(String id, String userName, String password, Boolean isAdmin) {

    }

    public User(String userName, String password, Boolean isAdmin) {

    }

    public User() {

    }

}
