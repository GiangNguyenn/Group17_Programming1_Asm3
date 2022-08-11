package Model.User;

import static common.Utils.lstMember;

public abstract class User {
    private String id;
    private String username;
    private String password;
    private Boolean isAdmin;



    public Boolean checkingUserLoginInfo(String username, String password) {
        /*
         * TODO checking infor of an Member.
         * return true if username and password are correct
         */

        boolean matchedUsername = lstMember.stream().map(User::getUsername).anyMatch(username::equals);
        boolean matchedPassword = lstMember.stream().map(User::getPassword).anyMatch(password::equals);
        return matchedUsername && matchedPassword;
    }

    public User(String username) {
        this.username = username;
    }

    public User(String id, String userName, String password, Boolean isAdmin) {

    }

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getId() {
        return this.id;
    }

}
