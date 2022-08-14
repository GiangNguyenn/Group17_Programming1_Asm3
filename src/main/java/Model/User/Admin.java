package Model.User;

public class Admin extends User {


    public Admin(String id, String userName, String password) {
        super(id, userName,password, true);
    }

}