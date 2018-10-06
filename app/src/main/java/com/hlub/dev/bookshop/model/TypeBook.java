package com.hlub.dev.bookshop.model;

public class TypeBook {





    private String typeBookID;
    private String typeBookName;
    private String typeBookDes;
    private int typeBookPosition;

    public TypeBook() {

    }

    public TypeBook(String typeBookID, String typeBookName, String typeBookDes, int typeBookPosition) {
        this.typeBookID = typeBookID;
        this.typeBookName = typeBookName;
        this.typeBookDes = typeBookDes;
        this.typeBookPosition = typeBookPosition;
    }

    public String getTypeBookID() {
        return typeBookID;
    }

    public void setTypeBookID(String typeBookID) {
        this.typeBookID = typeBookID;
    }

    public String getTypeBookName() {
        return typeBookName;
    }

    public void setTypeBookName(String typeBookName) {
        this.typeBookName = typeBookName;
    }

    public String getTypeBookDes() {
        return typeBookDes;
    }

    public void setTypeBookDes(String typeBookDes) {
        this.typeBookDes = typeBookDes;
    }

    public int getTypeBookPosition() {
        return typeBookPosition;
    }

    public void setTypeBookPosition(int typeBookPosition) {
        this.typeBookPosition = typeBookPosition;
    }
}
