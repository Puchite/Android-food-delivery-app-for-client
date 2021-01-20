package com.example.nompang.models;

public class drink {
    public String cocoa,milk;
    public drink(){}

    public drink(String cocoa, String milk) {
        this.cocoa = cocoa;
        this.milk = milk;
    }

    public String getCocoa() {
        return cocoa;
    }

    public void setCocoa(String cocoa) {
        this.cocoa = cocoa;
    }

    public String getMilk() {
        return milk;
    }

    public void setMilk(String milk) {
        this.milk = milk;
    }
}
