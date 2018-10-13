package com.hlub.dev.bookshop.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hlub.dev.bookshop.constant.Constant;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.Bill;
import com.hlub.dev.bookshop.model.BillDetail;
import com.hlub.dev.bookshop.model.Book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDAO implements Constant {
    private DatabaseManager databaseManager;
    static SQLiteDatabase sqLiteDatabase;


    public BillDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        sqLiteDatabase = databaseManager.getWritableDatabase();
    }


    //insert
    public long insertBill(Bill bill) {
        SQLiteDatabase sqLiteDatabase = databaseManager.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_ID, bill.getBillId());
        values.put(COLUMN_BILL_DATE, bill.getDate());

        long id = sqLiteDatabase.insert(TABLE_BILL, null, values);
        Log.e("Insert Bill", "Bill: " + id);
        return id;

    }

    //getAllBill
    public List<Bill> getAllBill() {

        List<Bill> billList = new ArrayList<>();

        try {
            String select = "SELECT * FROM " + TABLE_BILL;
            Cursor cursor = sqLiteDatabase.rawQuery(select, null);

            if (cursor.moveToFirst()) {
                do {
                    Bill bill = new Bill();
                    bill.setBillId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ID)));
                    bill.setDate(cursor.getLong(cursor.getColumnIndex(COLUMN_BILL_DATE)));

                    billList.add(bill);
                } while (cursor.moveToNext());
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            Log.e("Error Bill", "getBill:" + e);
        }
        return billList;

    }

    //lấy HĐ theo ngày
    public List<Bill> getListBillByDate(Date date) {
        List<Bill> billList = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = databaseManager.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_BILL,
                    new String[]{COLUMN_BILL_ID, COLUMN_BILL_DATE},
                    COLUMN_BILL_DATE + "=?",
                    new String[]{String.valueOf(date)},
                    null, null, null);
            Log.e("DATE: ", String.valueOf(date));
            if (cursor.moveToFirst()) {
                do {
                    billList.add(new Bill(cursor.getString(0), cursor.getLong(1)));
                } while (cursor.moveToNext());
            }
            return billList;
        } catch (Exception e) {
            Log.e("GetlistbyDate", "Date: " + e);
            return null;
        }

    }

    //getBillByID
    public Bill getBillByID(String billID) {
        Bill bill = null;
        try {
            SQLiteDatabase sqLiteDatabase = databaseManager.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_BILL,
                    new String[]{COLUMN_BILL_ID, COLUMN_BILL_DATE},
                    COLUMN_BILL_ID + "=?",
                    new String[]{billID},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                bill = new Bill();
                bill.setBillId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ID)));
                bill.setDate(cursor.getLong(cursor.getColumnIndex(COLUMN_BILL_DATE)));
            }
            return bill;
        } catch (Exception e) {
            return null;
        }
    }

    //UPDATE
    public int updateBill(Bill bill) {
        SQLiteDatabase sqLiteDatabase = databaseManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_DATE, String.valueOf(bill.getDate()));

        return sqLiteDatabase.update(TABLE_BILL, values, COLUMN_BILL_ID + " =?", new String[]{bill.getBillId()});

    }

    //delete
    public int deleteBill(String billID) {
        SQLiteDatabase sqLiteDatabase = databaseManager.getWritableDatabase();

        return sqLiteDatabase.delete(TABLE_BILL, COLUMN_BILL_ID + "=?", new String[]{billID});
    }

    public static int formatBillDateMonth(long billDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        //lấy list bill theo tháng
        try {
            // chuyển long ->dạng string
            String dateString = sdf.format(new Date(billDate)).toString();
            //chuyển String ->date
            Date date = sdf.parse(dateString);
            int month = date.getMonth() + 1;
            Log.e("Month: ", String.valueOf(month));
            return month;
        } catch (Exception e) {
            Log.e("Month error : ", String.valueOf(e));
            return 0;
        }
    }

    public List<Bill> getListBillByMonth(String day,String month) {
        SQLiteDatabase sqLiteDatabase = databaseManager.getWritableDatabase();
        List<Bill> billList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_BILL + " WHERE strftime('%m',Bill.ngaymua/1000,'unixepoch') ='" + month + "'" +
                " AND strftime('%d',Bill.ngaymua/1000,'unixepoch') ='" + day+"'";

        //String sql = "SELECT * FROM " + TABLE_BILL + " WHERE strftime('%d-%m',Bill.ngaymua/1000,'unixepoch') ='" + day + "'-'"+month+"'" ;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Log.e("select bill: ", sql);
        if (cursor.moveToFirst()) {
            do {
                Bill bill = new Bill();
                bill.setBillId(cursor.getString(0));
                bill.setDate(cursor.getLong(1));

                billList.add(bill);
            } while (cursor.moveToNext());

        }
        return billList;
    }


}
