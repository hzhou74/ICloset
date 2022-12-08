package com.example.icloset.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.icloset.bean.Person;

import java.util.ArrayList;
import java.util.List;


public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);

        db = helper.getWritableDatabase();
    }

    /**
     * New User Info
     * @param persons
     */
    public void add(List<Person> persons) {
        db.beginTransaction();  //begin session
        try {
            for (Person person : persons) {
                db.execSQL("INSERT INTO person VALUES(null, ?, ?,?)", new Object[]{person.getAccount(), person.getPassword(),person.head});
            }
            db.setTransactionSuccessful();  //set successful
        } finally {
            db.endTransaction();    //end session
        }
    }

    /**
     * Update user info
     */
    public void updatePerson(String account,String path){
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("head", path);
            db.update("person", values,"account=?",new String[]{account});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * look up user ingo
     */
    public List<Person> query() {
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Person person = new Person();
            person.account = c.getString(c.getColumnIndex("account"));
            person.password = c.getString(c.getColumnIndex("password"));
            person.head = c.getString(c.getColumnIndex("head"));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    /**
     * query all persons, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        return c;
    }

    /**
     * query all persons, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor2() {
        Cursor c = db.rawQuery("SELECT * FROM collect", null);
        return c;
    }

    /**
     * query all persons, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor3() {
        Cursor c = db.rawQuery("SELECT * FROM records", null);
        return c;
    }


    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
