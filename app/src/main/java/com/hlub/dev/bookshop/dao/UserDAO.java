package com.hlub.dev.bookshop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DatabaseManager databaseManager;
    private SQLiteDatabase sqLiteDatabase;

    public UserDAO(Context context) {
        databaseManager=new DatabaseManager(context);
    }


    public long insertUser(User user){

        SQLiteDatabase sqLiteDatabase=databaseManager.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(User.COLUMN_USERNAME,user.getUserName());
        contentValues.put(User.COLUMN_PASSWORD,user.getPassWord());
        contentValues.put(User.COLUMN_PHONE,user.getPhone());
        contentValues.put(User.COLUMN_HOTEN,user.getHoTen());

        //insert
        Long id=sqLiteDatabase.insert(User.TABLE_USER,null,contentValues);
        Log.e("insertUser","insertUser: "+id);

        //dong ket noi
        sqLiteDatabase.close();
        return id;

    }
    public User getUserByUsername(String username){
        User user=null;

        // get readable database as we are not inserting anything
        sqLiteDatabase=databaseManager.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(User.TABLE_USER,
                new String[]{User.COLUMN_USERNAME,User.COLUMN_PASSWORD,User.COLUMN_PHONE,User.COLUMN_HOTEN},
                User.COLUMN_USERNAME+"=?",
                new String[]{username},null,null,null);

        //lay ket qua Cursor
        if(cursor!=null && cursor.moveToFirst()){
            String user_name=cursor.getString(cursor.getColumnIndex(User.COLUMN_USERNAME));
            String user_pass=cursor.getString(cursor.getColumnIndex(User.COLUMN_PASSWORD));
            String user_phone=cursor.getString(cursor.getColumnIndex(User.COLUMN_PHONE));
            String user_hoten=cursor.getString(cursor.getColumnIndex(User.COLUMN_HOTEN));


            user=new User();
            user.setUserName(user_name);
            user.setPassWord(user_pass);
            user.setPhone(user_phone);
            user.setHoTen(user_hoten);
        }
        return user;
    }

    public List<User> getAllUsers(){
        List<User> usersList=new ArrayList<>();

        String selectQuery="SELECT * FROM "+User.TABLE_USER;
        sqLiteDatabase=databaseManager.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,null);
        //Cursor cursor=sqLiteDatabase.query(User.TABLE_USER,null,null,null,null,null,null);

        // looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                User user=new User();
                user.setUserName(cursor.getString(cursor.getColumnIndex(User.COLUMN_USERNAME)));
                user.setPassWord(cursor.getString(cursor.getColumnIndex(User.COLUMN_PASSWORD)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(User.COLUMN_PHONE)));
                user.setHoTen(cursor.getString(cursor.getColumnIndex(User.COLUMN_HOTEN)));

                usersList.add(user);
            }while (cursor.moveToNext());
        }

        //close conection
        sqLiteDatabase.close();

        return usersList;

    }

    //delete
    public void deleteUser(String userName){
        sqLiteDatabase=databaseManager.getWritableDatabase();
        sqLiteDatabase.delete(User.TABLE_USER,
                User.COLUMN_USERNAME+" =?",
                new String[]{userName});
        sqLiteDatabase.close();
    }

    //update
    public int updateUser(User user){
        sqLiteDatabase=databaseManager.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(User.COLUMN_PHONE,user.getPhone());
        values.put(User.COLUMN_HOTEN,user.getHoTen());

        //update row
        return sqLiteDatabase.update(User.TABLE_USER,
                values,
                User.COLUMN_USERNAME+ " =?",
                new String[]{user.getUserName()});
    }

    //changePassword
    public int changePassword(User user){
        sqLiteDatabase=databaseManager.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(User.COLUMN_PASSWORD,user.getPassWord());

        //update row
        return sqLiteDatabase.update(User.TABLE_USER,
                values,
                User.COLUMN_USERNAME+" =?"
                ,new String[]{user.getUserName()});
    }
}
