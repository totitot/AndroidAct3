package com.crisolokenth.act3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by totitot on 1/27/2018.
 */

public class DBAdapter {
    static final String DATABASE_NAME = "userdetailsDB";
    static final String DATABASE_TABLE = "userdetailsTB";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " (_id integer primary key autoincrement"
                    + ", name text not null"
                    + ", username text not null"
                    + ", password text not null"
                    + ", address text not null"
                    + ", gender text not null"
                    + ", email text not null"
                    + ");";

    final Context context;
    DataBaseOpenHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DataBaseOpenHelper(context);
    }

    private static class DataBaseOpenHelper extends SQLiteOpenHelper {
        DataBaseOpenHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DATABASE_CREATE);
            Log.w("DBHelper","database created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w("DBHelper", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close(){
        DBHelper.close();
    }
}
