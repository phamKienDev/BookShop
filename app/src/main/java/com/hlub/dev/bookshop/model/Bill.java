package com.hlub.dev.bookshop.model;

import java.util.Date;

public class Bill {
    private String billId;
    private Date date;

    public Bill(String billId, Date date) {
        this.billId = billId;
        this.date = date;
    }

    public Bill() {
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
