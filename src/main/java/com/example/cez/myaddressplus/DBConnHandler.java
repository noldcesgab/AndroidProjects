package com.example.cez.myaddressplus;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static android.util.Log.*;

/**
 * Created by user on 2/10/2017.
 */

public class DBConnHandler {

    private static String TAG;

    //Database table and columns declarations
    public static final String TABLE_ADDRESS = "addressplus1";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ANNOTATE = "annotate";
    public static final String COLUMN_FNAME = "fname";
    public static final String COLUMN_LNAME = "lname";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_POSTALCODE = "postalcode";

    //Prepare to create the database table statement
    private static final String DATABASE_CREATE = "CREATE table "
            + TABLE_ADDRESS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_ANNOTATE + " text not null, "
            + COLUMN_FNAME + " text not null, "
            + COLUMN_LNAME + " text not null, "
            + COLUMN_ADDRESS + " text not null, "
            + COLUMN_CITY + " text not null, "
            + COLUMN_COUNTRY + " text not null, "
            + COLUMN_POSTALCODE + " text not null" + ");";



    public static void onCreate(SQLiteDatabase database) {

        Log.d(TAG,"DB = " + DATABASE_CREATE );
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        v(DBConnHandler.class.getName(), "Upgrading database from version " +
        oldVersion + "to " + newVersion + " which will destroy old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(database);
    }

}
