package com.hlub.dev.bookshop.model;

public class Book {


    private String bookID;
    private String typeBookID;
    private String author;
    private String nxb;
    private float bookPrice;
    private int bookQuantity;
    public String name;


    public Book() {
    }

    public Book(String bookID, String typeBookID, String author, String nxb, float bookPrice, int bookQuantity) {
        this.bookID = bookID;
        this.typeBookID = typeBookID;
        this.author = author;
        this.nxb = nxb;
        this.bookPrice = bookPrice;
        this.bookQuantity = bookQuantity;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTypeBookID() {
        return typeBookID;
    }

    public void setTypeBookID(String typeBookID) {
        this.typeBookID = typeBookID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}
