package Model.User;

import common.BaseConstant.*;

public class Member extends User {

    public String getName() {
        return name;
    }

    private String name;
    private String phoneNumber;
    private TypeMember memberType;
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

    public Member(String userName, String password) {
        super(userName, password);
    }



    @Override
    public String toString() {
        return "Member [name=" + name + ", phoneNumber=" + phoneNumber + ", totalSpending=" + totalSpending
                + ", typeMember=" + memberType + "]";
    }

    public void showInfo() {
        this.toString();
    }


    public Object getPhoneNumber() {
        return this.phoneNumber;
    }


    public Double getTotalSpending() {
        return this.totalSpending;
    }

    public TypeMember getMemberType() {
        return this.memberType;
    }
}
