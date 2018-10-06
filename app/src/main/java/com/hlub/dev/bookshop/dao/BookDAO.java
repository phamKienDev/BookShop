package com.hlub.dev.bookshop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hlub.dev.bookshop.constant.Constant;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.Book;
import com.hlub.dev.bookshop.model.User;

import java.util.ArrayList;
import java.util.List;

public class BookDAO implements Constant{
    private DatabaseManager databaseManager;

    public BookDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;

    }

    public long insertBook(Book book) {
        SQLiteDatabase sqLiteDatabase = databaseManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID, book.getBookID());
        values.put(COLUMN_BOOK_TYPE, book.getTypeBookID());
        values.put(COLUMN_BOOK_AUTHOR, book.getAuthor());
        values.put(COLUMN_BOOK_NXB, book.getNxb());
        values.put(COLUMN_BOOK_PRICE, book.getBookPrice());
        values.put(COLUMN_BOOK_QUANTITY, book.getBookQuantity());

        //insert
        Long id = sqLiteDatabase.insert(TABLE_BOOK, null, values);
        Log.e("insertUser", "insertUser: " + id);

        //dong ket noi
        sqLiteDatabase.close();
        return id;
    }

    public List<Book> getAllListBook() {
        SQLiteDatabase sqLiteDatabase = databaseManager.getWritableDatabase();
        List<Book> books = new ArrayList<>();
        String select = " SELECT * FROM " + TABLE_BOOK;
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setBookID(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID)));
                book.setTypeBookID(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_TYPE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR)));
                book.setNxb(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NXB)));
                book.setBookPrice(cursor.getFloat(cursor.getColumnIndex(COLUMN_BOOK_PRICE)));
                book.setBookQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_QUANTITY)));
                books.add(book);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return books;
    }

    //delete book
    public int deleteBook(Book book) {
        SQLiteDatabase sqLiteDatabase = databaseManager.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_BOOK, COLUMN_BOOK_ID + " =?", new String[]{book.getBookID()});
    }

    //get Book
    public Book getBookById(String idBook) {
        Book book = null;
        SQLiteDatabase sqLiteDatabase = databaseManager.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_BOOK,
                new String[]{COLUMN_BOOK_ID, COLUMN_BOOK_TYPE, COLUMN_BOOK_AUTHOR, COLUMN_BOOK_NXB, COLUMN_BOOK_PRICE, COLUMN_BOOK_QUANTITY},
                COLUMN_BOOK_ID + " =?",
                new String[]{idBook},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String book_id = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID));
            String type_book_id = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_TYPE));
            String book_author = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR));
            String book_nxb = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NXB));
            float book_price = cursor.getFloat(cursor.getColumnIndex(COLUMN_BOOK_PRICE));
            int book_quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_QUANTITY));

            book=new Book();
            book.setBookID(book_id);
            book.setTypeBookID(type_book_id);
            book.setAuthor(book_author);
            book.setNxb(book_nxb);
            book.setBookPrice(book_price);
            book.setBookQuantity(book_quantity);

        }

        return book;
    }
    public long updateQuantityBookWhenBuy(Book book){
        SQLiteDatabase sqLiteDatabase=databaseManager.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_BOOK_QUANTITY,book.getBookQuantity());

        long id= sqLiteDatabase.update(TABLE_BOOK,
                values,
                COLUMN_BOOK_ID+ " =?",
                new String[]{book.getBookID()});
        Log.e("Update buy","update buy"+id);
        return id;
    }

    public List<Book> getListBestSeller(String month){
        List<Book> bookList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=databaseManager.getWritableDatabase();
        String sSQL = "SELECT maSach, SUM(soLuongMua) as soLuongMua FROM BillDeail INNER JOIN Bill " +
        "ON Bill.maHoaDon = BillDeail.maHoaDon WHERE strftime('%m',Bill.ngaymua) = '"+month+"' " +
        "GROUP BY maSach ORDER BY soLuongMua DESC LIMIT 10";
        Cursor cursor=sqLiteDatabase.rawQuery(sSQL,null);
        if(cursor.moveToNext()){
            do {
//                Book book=new Book();
//                book.setBookID(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID)));
//                book.setTypeBookID(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_TYPE)));
//                book.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR)));
//                book.setNxb(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NXB)));
//                book.setBookPrice(cursor.getFloat(cursor.getColumnIndex(COLUMN_BOOK_PRICE)));
//                book.setBookQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_QUANTITY)));
                Book book=getBookById(cursor.getString(0));

                bookList.add(book);
            }while (cursor.moveToNext());
        }
        return bookList;
    }
}
