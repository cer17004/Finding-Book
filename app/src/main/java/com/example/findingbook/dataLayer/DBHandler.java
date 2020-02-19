package com.example.findingbook.dataLayer;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

    private static final String TAG = "DBHandler Class";

    private static final int    DATABASE_VERSION       = 5;
    private static final String DATABASE_NAME          = "findingbook.db";

    public  static final String TABLE_ADDRESSES        = "addresses";
    public  static final String ADRS_COLUMN_ID         = "address_id";
    public  static final String ADRS_COLUMN_ADRS_ONE   = "address_one";
    public  static final String ADRS_COLUMN_ADRS_TWO   = "address_two";
    public  static final String ADRS_COLUMN_CITY       = "address_city";
    public  static final String ADRS_COLUMN_STATE      = "address_state";
    public  static final String ADRS_COLUMN_ZIPCODE    = "address_zipcode";
    public  static final String ADRS_COLUMN_LATITUDE   = "latitude";
    public  static final String ADRS_COLUMN_LONGITUDE  = "longitude";

    public  static final String TABLE_PERSONS                 = "persons";
    public  static final String PRSN_COLUMN_ID                = "person_id";
    public  static final String PRSN_COLUMN_ADDRESS_ID        = "address_id";
    public  static final String PRSN_COLUMN_FIRST_NAME        = "first_name";
    public  static final String PRSN_COLUMN_LAST_NAME         = "last_name";
    public  static final String PRSN_COLUMN_PHONE_NUMBER      = "phone_number";
    public  static final String PRSN_COLUMN_FACEBOOK          = "facebook";
    public  static final String PRSN_COLUMN_EMAIL             = "email";
    public  static final String PRSN_COLUMN_MOVED_TO_AREABOOK = "moved_to_areabook";
    public  static final String PRSN_COLUMN_STATUS            = "status";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG,"DBHandle.onCreate function called!!");

        String adrsQuery = "CREATE TABLE " + TABLE_ADDRESSES + " (" +
                ADRS_COLUMN_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ADRS_COLUMN_ADRS_ONE  + " TEXT, " +
                ADRS_COLUMN_ADRS_TWO  + " TEXT, " +
                ADRS_COLUMN_CITY      + " TEXT, " +
                ADRS_COLUMN_STATE     + " TEXT, " +
                ADRS_COLUMN_ZIPCODE   + " INT, "  +
                ADRS_COLUMN_LATITUDE  + " REAL, " +
                ADRS_COLUMN_LONGITUDE + " REAL "  +
                ");";

        String prsnQuery = "CREATE TABLE " + TABLE_PERSONS + " (" +
                PRSN_COLUMN_ID                + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRSN_COLUMN_ADDRESS_ID        + " INT, "   +
                PRSN_COLUMN_FIRST_NAME        + " TEXT, "   +
                PRSN_COLUMN_LAST_NAME         + " TEXT, "   +
                PRSN_COLUMN_PHONE_NUMBER      + " TEXT, "   +
                PRSN_COLUMN_FACEBOOK          + " TEXT, "   +
                PRSN_COLUMN_EMAIL             + " TEXT, "   +
                PRSN_COLUMN_MOVED_TO_AREABOOK + " INTEGER, " +
                PRSN_COLUMN_STATUS            + " INTEGER " +
                ");";

        db.execSQL(adrsQuery);
        db.execSQL(prsnQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG,"DBHandle.onUpgrade function called!!");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESSES + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS + ";");

        onCreate(db);
    }


    // Print out the database as a string
    public String getTableAddresses() {

        System.out.println("MyDBHandle.databaseToString function called!!");

        String dbString = "";

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_ADDRESSES + ";";

        // Cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);

        // Move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("productname")) != null) {
                dbString += c.getString(c.getColumnIndex("productname"));
                dbString += "\n";
            }
            c.moveToNext();
        }

        db.close();
        return dbString;
    }


}
