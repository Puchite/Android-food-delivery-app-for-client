package com.example.nompang.models;

public class Users {
    private  String name,phone,password,image,realname,location;
    public Users(){

    }

    public Users(String name, String phone, String password, String image, String realname, String location) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.realname = realname;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
