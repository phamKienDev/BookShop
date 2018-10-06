package com.hlub.dev.bookshop.model;

public class User {



    private String userName;
    private String passWord;
    private String phone;
    private String hoTen;


    public User(String userName, String passWord, String phone, String hoTen) {
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
        this.hoTen = hoTen;
    }

    public User() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

}
