package Model.User;

import common.BaseConstant;
import common.BaseConstant.*;

public class Member extends User {

    private String name;
    private String phoneNumber;
    private TypeMember typeMember;
    private Double totalSpending;

    public Member(String id, String name, String phoneNumber, String username, String password) {
        // TODO
        super(id, username, password);
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Member(String userName, String password, String name, String phoneNumber) {
        // TODO
        super();

    }

    public Member() {
    }

    public Member(String userName) {
        super(userName);
    }


    @Override
    public String toString() {
        return "Member [name=" + name + ", phoneNumber=" + phoneNumber + ", totalSpending=" + totalSpending
                + ", typeMember=" + typeMember + "]";
    }

    public void showInfo() {
        this.toString();
    }


}
