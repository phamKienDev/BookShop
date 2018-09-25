package com.hlub.dev.bookshop.model;

public class User {

    //ten bang
    public static final String TABLE_USER = "User";

    //ten cot
    public static final String COLUMN_USERNAME = "userName";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_PHONE = "Phone";
    public static final String COLUMN_HOTEN = "hoTen";

    //cau lenh tao bang
    public static final String CREATE_TABLE_USER = " CREATE TABLE " + TABLE_USER + " ( " +
            COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
            COLUMN_PASSWORD + " TEXT," +
            COLUMN_PHONE + " TEXT," +
            COLUMN_HOTEN + " TEXT " +
            " ) ";

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
