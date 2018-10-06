package com.hlub.dev.bookshop.constant;

public interface Constant {

    //BOOK
    
     String TABLE_BOOK = "Book";

    
     String COLUMN_BOOK_ID = "maSach";
     String COLUMN_BOOK_TYPE = "maTheLoai";
     String COLUMN_BOOK_AUTHOR = "tacGia";
     String COLUMN_BOOK_NXB = "NXB";
     String COLUMN_BOOK_PRICE = "giaBan";
     String COLUMN_BOOK_QUANTITY = "soLuong";

    
     String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + " (" +
            COLUMN_BOOK_ID + " CHAR(5) PRIMARY KEY NOT NULL," +
            COLUMN_BOOK_TYPE + " CHAR(50) NOT NULL, " +
            COLUMN_BOOK_AUTHOR + " VARCHAR(50), " +
            COLUMN_BOOK_NXB + " VARCHAR(50)," +
            COLUMN_BOOK_PRICE + " FLOAT NOT NULL," +
            COLUMN_BOOK_QUANTITY + " INTEGER  NOT NULL" +
            ")";

    //TYPEBOOK
    
     String TABLE_TYPE_BOOK="TypeBook";

    
     String COLUMN_TYPEBOOK_ID="maTheLoai";
     String COLUMN_TYPEBOOK_NAME="tenTheLoai";
     String COLUMN_TYPEBOOK_DES="moTa";
     String COLUMN_TYPEBOOK__POSITION="viTri";

    
     String CREATE_TABLE_TYPE_BOOK="CREATE TABLE "+TABLE_TYPE_BOOK+"(" +
            COLUMN_TYPEBOOK_ID+" CHAR(5) PRIMARY KEY NOT NULL ,"+
            COLUMN_TYPEBOOK_NAME+" VARCHAR(100) NOT NULL ,"+
            COLUMN_TYPEBOOK_DES+" VARCHAR(255)  ,"+
            COLUMN_TYPEBOOK__POSITION+" INTEGER "+
            ")";


    //USER
    
     String TABLE_USER = "User";

    
     String COLUMN_USERNAME = "userName";
     String COLUMN_PASSWORD = "Password";
     String COLUMN_PHONE = "Phone";
     String COLUMN_HOTEN = "hoTen";

    //cau lenh tao bang
     String CREATE_TABLE_USER = " CREATE TABLE " + TABLE_USER + " ( " +
            COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
            COLUMN_PASSWORD + " TEXT," +
            COLUMN_PHONE + " TEXT," +
            COLUMN_HOTEN + " TEXT " +
            " ) ";

     //BILL
    String TABLE_BILL="Bill";

    String COLUMN_BILL_ID="maHoaDon";
    String COLUMN_BILL_DATE="ngaymua";

    String CREATE_TABLE_BILL="CREATE TABLE "+ TABLE_BILL+" ( "+
            COLUMN_BILL_ID+" CHAR(7) PRIMARY KEY NOT NULL,"+
            COLUMN_BILL_DATE+ " DATE NOT NULL"+
            " ) ";

    //BILL DETAIL

    String TABLE_BILL_DETAIL="BillDeail";

    String COLUMN_BILL_DETAIL_ID="maHoaDonChiTiet";
    //String COLUMN_BILL_ID="maHoaDon";
    //String COLUMN_BOOK_ID="maSach";
    String COLUMN_QUANTITY_BUY="soLuongMua";

    String CREATE_TABLE_BILL_DETAIL="CREATE TABLE "+TABLE_BILL_DETAIL+ "( "+
            COLUMN_BILL_DETAIL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            COLUMN_BILL_ID+" CHAR(7) NOT NULL ,"+
            COLUMN_BOOK_ID + " CHAR(5) NOT NULL ," +
            COLUMN_QUANTITY_BUY+ " INTEGER "+
            " ) ";

}
