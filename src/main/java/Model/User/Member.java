package Model.User;

import Model.Productions.Order;
import Service.OrderService;
import common.BaseConstant;
import common.BaseConstant.*;
import common.BaseHelper;
import common.Utils;

import java.util.ArrayList;
import java.util.Objects;

import static common.Utils.lstOrder;

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

    public void showInfo() {
        this.toString();
    }


    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String convertTypeMemeberToString() {
        if(this.memberType.equals(TypeMember.NORMAL)) {
            return "Normal";
        } else if (this.memberType.equals(TypeMember.GOLD)) {
            return "Gold"; 
        } else if (this.memberType.equals(TypeMember.PLATINUM)) {
            return "Platinum";
        } else if(this.memberType.equals(TypeMember.SILVER)) {
            return "Silver";
        } else {
            return "";
        }
    }


    public Double getTotalSpending() {
        return this.totalSpending;
    }

    public TypeMember getMemberType() {
        return this.memberType;
    }

    double totalSpending(){
        ArrayList<Double> totalSpendingOfCustomer = new ArrayList<>();          //Get all the orders belong to the current users
        double resultDouble = 0;
        for (Order order : lstOrder) {
            if (order.getMemberID().equalsIgnoreCase(Utils.current_user.getId())) {
                totalSpendingOfCustomer.add(order.getTotalPrice());
            }
        }
        for (int i = 0; i < totalSpendingOfCustomer.size(); i++){
            resultDouble += totalSpendingOfCustomer.get(0);
        }
        return resultDouble;
    }
    
    TypeMember memberType(){        //5 10 25
        if (totalSpending > 25000000){
            return TypeMember.PLATINUM;
        } else if (totalSpending > 10000000) {
            return TypeMember.GOLD;
        } else if (totalSpending > 5000000) {
            return TypeMember.SILVER;
        } else {
            return TypeMember.NORMAL;
        }

    }

    public double discountAmount(){
        switch (this.getMemberType()){
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
