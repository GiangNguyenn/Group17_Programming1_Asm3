package Model.User;

import Model.Productions.Order;
import common.BaseConstant;
import common.BaseConstant.*;
import common.BaseConstant.TypeMember;

import common.Utils;

import java.util.ArrayList;
import java.util.stream.Stream;

import static common.BaseConstant.TypeMember;
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

    public String converMemberTypeToString(){
        if(this.memberType.equals(TypeMember.NORMAL)){
            return"Normal";
        } else if (this.memberType.equals(TypeMember.SILVER)) {
            return"Silver";
        } else if (this.memberType.equals(TypeMember.GOLD)) {
            return "Gold";
        } else if( this.memberType.equals(TypeMember.PLATINUM)){
            return "Platinum";
        } else {
        return "";
        }
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


    public String getPhoneNumber() {
        return this.phoneNumber;
    }


    public Double getTotalSpending() {
        return this.totalSpending;
    }

    public void setTotalSpending(Double totalSpending) {
        this.totalSpending = totalSpending;
    }

    public TypeMember getMemberType() {
        return this.memberType;
    }

    public void setMemberType(TypeMember memberType) {
        this.memberType = memberType;
    }

    double calculateTotalSpending(){
        ArrayList<Double> totalSpendingOfCustomer = new ArrayList<>();          //Get all the orders belong to the current users
        Double totalSpending = lstOrder.stream().filter(order -> order.getMemberID().equalsIgnoreCase(Utils.current_user.getId())).mapToDouble(Order::getTotalPrice).sum();
        return totalSpending;
    }

        TypeMember processMemberType(){        //5 10 25
            if (totalSpending > BaseConstant.LITMIT_PLATINUM){
                return TypeMember.PLATINUM;
            } else if (totalSpending > BaseConstant.LITMIT_GOLD) {
                return TypeMember.GOLD;
            } else if (totalSpending > BaseConstant.LITMIT_SILVER) {
                return TypeMember.SILVER;
            } else {
                return TypeMember.NORMAL;
            }

        }

    public void updateMemberInfo(){
        this.setTotalSpending(this.calculateTotalSpending());
        this.setMemberType(this.processMemberType());
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
