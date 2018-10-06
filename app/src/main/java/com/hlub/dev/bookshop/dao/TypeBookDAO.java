package com.hlub.dev.bookshop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hlub.dev.bookshop.constant.Constant;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.TypeBook;

import java.util.ArrayList;
import java.util.List;

public class TypeBookDAO implements Constant{

    private DatabaseManager databaseManager;

    public TypeBookDAO(Context context) {
        databaseManager=new DatabaseManager(context);
    }

    //insert Typebook

    public long inserTypeBook(TypeBook typeBook){
        //xin quyen
       SQLiteDatabase sqLiteDatabase=databaseManager.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(COLUMN_TYPEBOOK_ID,typeBook.getTypeBookID());
        values.put(COLUMN_TYPEBOOK_NAME,typeBook.getTypeBookName());
        values.put(COLUMN_TYPEBOOK_DES,typeBook.getTypeBookDes());
        values.put(COLUMN_TYPEBOOK__POSITION,typeBook.getTypeBookPosition());



        Long id=sqLiteDatabase.insert(TABLE_TYPE_BOOK,null,values);
        Log.e("Typebok","Insert"+id);
        sqLiteDatabase.close();
        return id;
    }

    //get all list typebook
    public List<TypeBook> getAllListTypebook(){
        List<TypeBook> typeBookList=new ArrayList<>();

        String select="SELECT * FROM "+TABLE_TYPE_BOOK;
        SQLiteDatabase sqLiteDatabase=databaseManager.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do {
                TypeBook typeBook=new TypeBook();
                typeBook.setTypeBookID(cursor.getString(cursor.getColumnIndex(COLUMN_TYPEBOOK_ID)));
                typeBook.setTypeBookName(cursor.getString(cursor.getColumnIndex(COLUMN_TYPEBOOK_NAME)));
                typeBook.setTypeBookDes(cursor.getString(cursor.getColumnIndex(COLUMN_TYPEBOOK_DES)));
                typeBook.setTypeBookPosition(cursor.getInt(cursor.getColumnIndex(COLUMN_TYPEBOOK__POSITION)));
                typeBookList.add(typeBook);
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return typeBookList;
    }

    //delete typebook
    public int deleteTypeBook(TypeBook typeBook){
        SQLiteDatabase sqLiteDatabase=databaseManager.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_TYPE_BOOK,
                COLUMN_TYPEBOOK_ID +" =?",
                new String[]{typeBook.getTypeBookID()});
    }

    //update typebook
    public int updateTypebok(TypeBook type){
        SQLiteDatabase sqLiteDatabase=databaseManager.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(COLUMN_TYPEBOOK_NAME,type.getTypeBookName());
        values.put(COLUMN_TYPEBOOK_DES,type.getTypeBookDes());
        values.put(COLUMN_TYPEBOOK__POSITION,type.getTypeBookPosition());

        //update row
        return sqLiteDatabase.update(TABLE_TYPE_BOOK,
                values,
                COLUMN_TYPEBOOK_ID+" =?",
                new String[]{type.getTypeBookID()});

    }
    public TypeBook getTypeBookById(String typeBookID){
        TypeBook typeBook=null;
        SQLiteDatabase sqLiteDatabase=databaseManager.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query(TABLE_TYPE_BOOK,
                new String[]{COLUMN_TYPEBOOK_ID,COLUMN_TYPEBOOK_NAME,COLUMN_TYPEBOOK_DES,COLUMN_TYPEBOOK__POSITION},
                COLUMN_TYPEBOOK_ID+ " =?",
                new String[]{typeBookID},
                null,null,null);
        if (cursor!=null &&cursor.moveToFirst()){
            String typebook_id=cursor.getString(cursor.getColumnIndex(COLUMN_TYPEBOOK_ID));
            String typebook_name=cursor.getString(cursor.getColumnIndex(COLUMN_TYPEBOOK_NAME));
            String typebook_des=cursor.getString(cursor.getColumnIndex(COLUMN_TYPEBOOK_DES));
            int typebook_pos=cursor.getInt(cursor.getColumnIndex(COLUMN_TYPEBOOK_ID));

            typeBook=new TypeBook();
            typeBook.setTypeBookID(typebook_id);
            typeBook.setTypeBookName(typebook_name);
            typeBook.setTypeBookDes(typebook_des);
            typeBook.setTypeBookPosition(typebook_pos);
        }
        return typeBook;
    }
    public List<String> getListTypeBookName(){
        List<String> typeBookList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=databaseManager.getWritableDatabase();
        String select="SELECT "+COLUMN_TYPEBOOK_NAME+" FROM "+TABLE_TYPE_BOOK;
        Cursor cursor=sqLiteDatabase.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do {

                //add list
                typeBookList.add(cursor.getString(cursor.getColumnIndex(COLUMN_TYPEBOOK_NAME)));
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return typeBookList;
    }
}
