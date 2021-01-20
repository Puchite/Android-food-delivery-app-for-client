package com.example.nompang.models;

public class product {
    public String name,description,status,imageuri,price,type,sweet;
    public int amount;
    public product() {
    }

    public product(String name, String description, String status, String imageuri, String price, String type, String sweet, int amount) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.imageuri = imageuri;
        this.price = price;
        this.type = type;
        this.sweet = sweet;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSweet() {
        return sweet;
    }

    public void setSweet(String sweet) {
        this.sweet = sweet;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
