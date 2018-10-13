package com.hlub.dev.bookshop.model;

import java.util.Date;

public class Bill {
    private String billId;
    private long date;

    public Bill(String billId, long date) {
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
