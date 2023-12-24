package edu.gatech.cs6310.domain;

import javax.persistence.Entity;

@Entity
public class Customer extends User {

    private String account;
    private int rating;
    private int credit;

    private int tempCost;

    public Customer(String firstName, String lastName, String phone, String account, int rating, int credit) {
        super(firstName, lastName, phone);
        this.account = account;
        this.rating = rating;
        this.credit = credit;
        this.tempCost = 0;
    }

    public Customer() {

    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    public void setTempCost(int tempCost) {
        this.tempCost =tempCost;
    }

    public int getTempCost() {
        return tempCost;
    }

    @Override
    public String toString() {
        return "name:" + firstName + "_" + lastName + ",phone:" + phone + ",rating:" + rating + ",credit:" + credit;
    }
}
