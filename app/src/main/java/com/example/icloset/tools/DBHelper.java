package com.example.icloset.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_qq.db";  //name of database
    private static final int DATABASE_VERSION = 1 ;         //version of databse

    public DBHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    /**
     * Build Databse：person，collect
     * _id: key，self-increment
     * **/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("===","Create person Database！");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS person(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " account VARCHAR, password VARCHAR, head VARCHAR)");

        Log.i("===","Create collect Database！");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS collect(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " title VARCHAR, time VARCHAR, pic VARCHAR, url VARCHAR)");

        Log.i("===","Create records Database！");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS records(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " account VARCHAR, name VARCHAR, amt VARCHAR, time VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion,int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase){
        super.onOpen(sqLiteDatabase);
    }

}
