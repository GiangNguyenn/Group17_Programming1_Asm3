package Model.User;

import static common.Utils.lstMember;

public abstract class User {
    private String id;
    private String username;
    private String password;
    private Boolean isAdmin;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
