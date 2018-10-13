package com.hlub.dev.bookshop.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.hlub.dev.bookshop.BillActivity;
import com.hlub.dev.bookshop.constant.Constant;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.Bill;
import com.hlub.dev.bookshop.model.BillDetail;
import com.hlub.dev.bookshop.model.Book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDetailDAO implements Constant {

    private DatabaseManager databaseManager;
    private SQLiteDatabase sqLiteDatabase;
    private BillDAO billDAO;
    private BookDAO bookDAO;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BillDetailDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        sqLiteDatabase = databaseManager.getWritableDatabase();
        billDAO = new BillDAO(databaseManager);
        bookDAO = new BookDAO(databaseManager);
    }

    //insert
    public long insertBillDetail(BillDetail billDatail) {

        ContentValues values = new ContentValues();
        //values.put(COLUMN_BILL_DETAIL_ID, billDatail.getBillDetailId()); ->AUTOINCREMENT
        values.put(COLUMN_BOOK_ID, billDatail.getBook().getBookID());
        values.put(COLUMN_BILL_ID, billDatail.getBill().getBillId());
        values.put(COLUMN_QUANTITY_BUY, billDatail.getQuantityBuy());

        long id = sqLiteDatabase.insert(TABLE_BILL_DETAIL, null, values);

        Log.e("Insert billDetail", "inseet: " + id);
        return id;


    }

    //getAll
    public List<BillDetail> getAllBillDetail() {
        List<BillDetail> billDetailList = new ArrayList<>();
        /**String sSQL = "SELECT maHDCT, HoaDon.maHoaDon,HoaDon.ngayMua, " +
         "Sach.maSach, Sach.maTheLoai, Sach.tenSach, Sach.tacGia, Sach.NXB,
         Sach.giaBia, " +
         "Sach.soLuong,HoaDonChiTiet.soLuong FROM HoaDonChiTiet INNER JOIN
         HoaDon " +
         "on HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon INNER JOIN Sach on
         Sach.maSach = HoaDonChiTiet.maSach";**/

        String select = "SELECT " + COLUMN_BILL_DETAIL_ID + ",Bill." + COLUMN_BILL_ID + ",Bill." + COLUMN_BILL_DATE + "," +
                "Book." + COLUMN_BOOK_ID + ",Book." + COLUMN_BOOK_TYPE + ",Book." + COLUMN_BOOK_AUTHOR + ",Book." + COLUMN_BOOK_NXB + ",Book." + COLUMN_BOOK_PRICE + ",Book." + COLUMN_BOOK_QUANTITY + "," +
                "BillDeail." + COLUMN_QUANTITY_BUY +
                " FROM BillDeail INNER JOIN Bill ON " + " BillDeail." + COLUMN_BILL_ID + " = Bill." + COLUMN_BILL_ID +
                " INNER JOIN Book ON Book." + COLUMN_BOOK_ID + " = BillDeail." + COLUMN_BOOK_ID;
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            try {
                do {
                    BillDetail billDetail = new BillDetail();
                    //bill=new Bill(billID, date)
                    Bill bill = new Bill(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ID)), cursor.getLong(cursor.getColumnIndex(COLUMN_BILL_DATE)));
                    //book=new Book(bookID, bookType, tacgia, nxb,price, quantity)
                    Book book = new Book(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_TYPE)), cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NXB)), cursor.getFloat(cursor.getColumnIndex(COLUMN_BOOK_PRICE)), cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_QUANTITY)));


                    billDetail.setBillDetailId(cursor.getInt(cursor.getColumnIndex(COLUMN_BILL_DETAIL_ID)));//ma HDCT
                    billDetail.setBill(bill);//bill
                    billDetail.setBook(book);//book
                    billDetail.setQuantityBuy(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY_BUY)));//so luong mua

                    billDetailList.add(billDetail);
                    Log.d("//=====", billDetail.toString());
                } while (cursor.moveToNext());
            } catch (Exception e) {
                Log.e("Error getAllBillDetail", "get: " + e);
            }

        }
        return billDetailList;
    }

    //getAllBillDetailByID

    public List<BillDetail> getAllBillDetailByID(String maHoaDon) {
        List<BillDetail> billDetailList = new ArrayList<>();
        /**String sSQL = "SELECT maHDCT, HoaDon.maHoaDon,HoaDon.ngayMua, " +
         "Sach.maSach, Sach.maTheLoai, Sach.tenSach, Sach.tacGia, Sach.NXB,
         Sach.giaBia, " +
         "Sach.soLuong,HoaDonChiTiet.soLuong FROM HoaDonChiTiet INNER JOIN
         HoaDon " +
         "on HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon INNER JOIN Sach on
         Sach.maSach = HoaDonChiTiet.maSach  where HoaDonChiTiet.maHoaDon='"+maHoaDon+"'" ;**/

        String select = "SELECT " + COLUMN_BILL_DETAIL_ID + ",Bill." + COLUMN_BILL_ID + ",Bill." + COLUMN_BILL_DATE + "," +
                "Book." + COLUMN_BOOK_ID + ",Book." + COLUMN_BOOK_TYPE + ",Book." + COLUMN_BOOK_AUTHOR + ",Book." + COLUMN_BOOK_NXB + ",Book." + COLUMN_BOOK_PRICE + ",Book." + COLUMN_BOOK_QUANTITY + "," +
                "BillDeail." + COLUMN_QUANTITY_BUY +
                " FROM BillDeail INNER JOIN Bill ON " + " BillDeail." + COLUMN_BILL_ID + " = Bill." + COLUMN_BILL_ID +
                " INNER JOIN Book ON Book." + COLUMN_BOOK_ID + " = BillDeail." + COLUMN_BOOK_ID + " WHERE BillDeail." + COLUMN_BILL_ID + " ='" + maHoaDon + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            try {
                do {
                    BillDetail billDetail = new BillDetail();
                    //bill=new Bill(billID, date)
                    Bill bill = new Bill(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ID)), cursor.getLong(cursor.getColumnIndex(COLUMN_BILL_DATE)));
                    //book=new Book(bookID, bookType, tacgia, nxb,price, quantity)
                    Book book = new Book(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_TYPE)), cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NXB)), cursor.getFloat(cursor.getColumnIndex(COLUMN_BOOK_PRICE)), cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_QUANTITY)));


                    billDetail.setBillDetailId(cursor.getInt(cursor.getColumnIndex(COLUMN_BILL_DETAIL_ID)));//ma HDCT
                    billDetail.setBill(bill);//bill
                    billDetail.setBook(book);//book
                    billDetail.setQuantityBuy(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY_BUY)));//so luong mua

                    billDetailList.add(billDetail);
                    Log.d("//=====", billDetail.toString());
                } while (cursor.moveToNext());
            } catch (Exception e) {
                Log.e("Error getAllBillDetail", "get: " + e);
            }

        }
        return billDetailList;
    }

    public List<BillDetail> getListBillDetailByBillId(String billId) {
        List<BillDetail> billDetailList=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.query(TABLE_BILL_DETAIL,
                new String[]{COLUMN_BILL_DETAIL_ID,COLUMN_BILL_ID,COLUMN_BOOK_ID,COLUMN_QUANTITY_BUY},
                COLUMN_BILL_ID+"=?",
                new String[]{billId},
                null,null,null);
        if(cursor.moveToFirst()){
            do {
                BillDetail billDetail=new BillDetail();
                billDetail.setBillDetailId(cursor.getInt(cursor.getColumnIndex(COLUMN_BILL_DETAIL_ID)));
                billDetail.setBill(billDAO.getBillByID(cursor.getString(1)));
                billDetail.setBook(bookDAO.getBookById(cursor.getString(2)));
                billDetail.setQuantityBuy(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY_BUY)));

                billDetailList.add(billDetail);
            }while (cursor.moveToNext());
        }
        return billDetailList;
    }

    //update
    public int updateBillDetail(BillDetail billDetail) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_DETAIL_ID, billDetail.getBillDetailId());
        values.put(COLUMN_BILL_ID, billDetail.getBill().getBillId());
        values.put(COLUMN_BOOK_ID, billDetail.getBook().getBookID());
        values.put(COLUMN_QUANTITY_BUY, billDetail.getQuantityBuy());

        return sqLiteDatabase.update(TABLE_BILL_DETAIL, values, COLUMN_BILL_DETAIL_ID + "=?", new String[]{String.valueOf(billDetail.getBillDetailId())});
    }

    //delete
    public int deleteBillDetail(String billDetailID) {

        return sqLiteDatabase.delete(TABLE_BILL_DETAIL, COLUMN_BILL_DETAIL_ID + "=?", new String[]{billDetailID});
    }


    //kiem tra xem trong maHoaDon con HDCT nao k
    //van con HDCT ->true
    // k con ->false
    public boolean checkBill(String billID) {
        SQLiteDatabase sqLiteDatabase = databaseManager.getReadableDatabase();
        try {
            Cursor cursor = sqLiteDatabase.query(TABLE_BILL_DETAIL,
                    new String[]{COLUMN_BILL_ID},
                    COLUMN_BILL_ID + "=?",
                    new String[]{billID},
                    null, null, null);
            cursor.moveToFirst();
            int i = cursor.getCount();
            if (i <= 0) {
                return false;
            }
            return true;

        } catch (Exception e) {
            Log.e("check bill", "check: " + e);
            return false;
        }

    }


}
