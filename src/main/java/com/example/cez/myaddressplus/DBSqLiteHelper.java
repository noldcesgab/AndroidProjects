package com.example.cez.myaddressplus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by user on 2/10/2017.
 * This class is API obtain references to your database, the system performs the potentially
 * long-running operations of creating and updating the database only when needed and not during app startup
 */

public class DBSqLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "addressplus1.db";
    private static final int DATABASE_VERSION = 2;

    //Constructor to call instantiate the class
    public DBSqLiteHelper(Context context) {super(context, DATABASE_NAME,null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) { DBConnHandler.onCreate(db);}


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DBConnHandler.onUpgrade(db, oldVersion, newVersion);
    }
}
