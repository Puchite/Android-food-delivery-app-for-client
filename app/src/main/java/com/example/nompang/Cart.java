package com.example.nompang;

public class Cart {

    public String imageuri,name,price,amount;

    public Cart(){

    }

    public Cart(String imageuri, String name, String price, String amount) {
        this.imageuri = imageuri;
        this.name = name;
        this.price = price;
        this.amount = amount;
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
}
