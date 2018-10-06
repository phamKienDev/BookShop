package com.hlub.dev.bookshop.model;

public class BillDetail {
    private int billDetailId;
    private Bill bill;
    private Book book;
    private int quantityBuy;

    public BillDetail(int billDetailId, Bill bill, Book book, int quantityBuy) {
        this.billDetailId = billDetailId;
        this.bill = bill;
        this.book = book;
        this.quantityBuy = quantityBuy;
    }

    public BillDetail() {
    }

    public int getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(int billDetailId) {
        this.billDetailId = billDetailId;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantityBuy() {
        return quantityBuy;
    }

    public void setQuantityBuy(int quantityBuy) {
        this.quantityBuy = quantityBuy;
    }
}
