package com.hlub.dev.bookshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hlub.dev.bookshop.constant.Constant;
import com.hlub.dev.bookshop.model.Book;
import com.hlub.dev.bookshop.model.TypeBook;
import com.hlub.dev.bookshop.model.User;

public class DatabaseManager extends SQLiteOpenHelper implements Constant{


    //co so du lieu
    public DatabaseManager(Context context) {
        super(context, "BookManager", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_TYPE_BOOK);
        sqLiteDatabase.execSQL(CREATE_TABLE_BOOK);
        sqLiteDatabase.execSQL(CREATE_TABLE_BILL);
        sqLiteDatabase.execSQL(CREATE_TABLE_BILL_DETAIL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE_BOOK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL_DETAIL);
        onCreate(sqLiteDatabase);
    }


}
