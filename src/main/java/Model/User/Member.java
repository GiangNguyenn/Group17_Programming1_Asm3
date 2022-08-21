package Model.User;

import Model.Productions.Order;
import common.BaseConstant.TypeMember;
import common.Utils;

import java.util.ArrayList;

import static common.Utils.lstOrder;

public class Member extends User {
    private String name;
    private String phoneNumber;
    private TypeMember memberType;
    private Double totalSpending;

    public Member(String id, String name, String phoneNumber, String username, String password) {
        // TODO
        super(id, username, password);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.totalSpending = totalSpending();
        this.memberType = memberType();
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

    public String getName() {
        return name;
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

    double totalSpending() {
        ArrayList<Double> totalSpendingOfCustomer = new ArrayList<>();          //Get all the orders belong to the current users
        double resultDouble = 0;
        for (Order order : lstOrder) {
            if (order.getMemberID().equalsIgnoreCase(Utils.current_user.getId())) {
                totalSpendingOfCustomer.add(order.getTotalPrice());
            }
        }
        for (int i = 0; i < totalSpendingOfCustomer.size(); i++) {
            resultDouble += totalSpendingOfCustomer.get(0);
        }
        return resultDouble;
    }

    TypeMember memberType() {        //5 10 25
        if (totalSpending > 25000000.0) {
            return TypeMember.PLATINUM;
        } else if (totalSpending > 10000000.0) {
            return TypeMember.GOLD;
        } else if (totalSpending > 5000000.0) {
            return TypeMember.SILVER;
        } else {
            return TypeMember.NORMAL;
        }

    }

    public double discountAmount() {
        switch (this.getMemberType()) {
            case SILVER -> {
                return 0.95;
            }
            case GOLD -> {
                return 0.90;
            }
            case PLATINUM -> {
                return 0.85;
            }
            default -> {
                return 1.0;
            }
        }
    }
}
