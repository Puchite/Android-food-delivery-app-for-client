package com.example.nompang.models;

public class Cart {

    public String BDescription, BImage, BName, BSweet, BType, BAmount;

    public Cart(){

    }

    public Cart(String BDescription, String BImage, String BName, String BSweet, String BType, String BAmount) {
        this.BDescription = BDescription;
        this.BImage = BImage;
        this.BName = BName;
        this.BSweet = BSweet;
        this.BType = BType;
        this.BAmount = BAmount;
    }

    public String getBDescription() {
        return BDescription;
    }

    public void setBDescription(String BDescription) {
        this.BDescription = BDescription;
    }

    public String getBImage() {
        return BImage;
    }

    public void setBImage(String BImage) {
        this.BImage = BImage;
    }

    public String getBName() {
        return BName;
    }

    public void setBName(String BName) {
        this.BName = BName;
    }

    public String getBSweet() {
        return BSweet;
    }

    public void setBSweet(String BSweet) {
        this.BSweet = BSweet;
    }

    public String getBType() {
        return BType;
    }

    public void setBType(String BType) {
        this.BType = BType;
    }

    public String getBAmount() {
        return BAmount;
    }

    public void setBAmount(String BAmount) {
        this.BAmount = BAmount;
    }
}
