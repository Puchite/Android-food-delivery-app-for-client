package com.example.nompang.models;

public class Products {
    String nameDrinks;
//    public Products(){
//
//    }

    public Products(String name) {
        this.nameDrinks = name;
    }

    public String getName(){
        return nameDrinks;
    }

    public void setName(String name) {
        this.nameDrinks = name;
    }
}
