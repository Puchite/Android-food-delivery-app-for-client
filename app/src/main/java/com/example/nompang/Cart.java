package com.example.nompang;

public class Cart {

    public String imageuri,name,price,amount,sweet,type,description;

    public Cart(){

    }

    public Cart(String imageuri, String name, String price, String amount, String sweet, String type, String description) {
        this.imageuri = imageuri;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.sweet = sweet;
        this.type = type;
        this.description = description;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSweet() {
        return sweet;
    }

    public void setSweet(String sweet) {
        this.sweet = sweet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
