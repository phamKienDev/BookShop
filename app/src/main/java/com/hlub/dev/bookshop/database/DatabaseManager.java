package com.hlub.dev.bookshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hlub.dev.bookshop.model.Book;
import com.hlub.dev.bookshop.model.TypeBook;
import com.hlub.dev.bookshop.model.User;

public class DatabaseManager extends SQLiteOpenHelper {


    //co so du lieu
    public DatabaseManager(Context context) {
        super(context, "BookManager", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(User.CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(TypeBook.CREATE_TABLE_TYPE_BOOK);
        sqLiteDatabase.execSQL(Book.CREATE_TABLE_BOOK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + User.TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TypeBook.TABLE_TYPE_BOOK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Book.TABLE_BOOK);
        onCreate(sqLiteDatabase);
    }


}
