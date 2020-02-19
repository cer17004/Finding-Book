package com.example.findingbook.dataLayer;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DBAddressTableHandler extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION   = 1;
    private static final String DATABASE_NAME      = "findingbook.db";
    public  static final String TABLE_ADDRESSES    = "addresses";
    public  static final String COLUMN_ID          = "_id";
    public  static final String COLUMN_PRODUCTNAME = "productname";

    public DBAddressTableHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
