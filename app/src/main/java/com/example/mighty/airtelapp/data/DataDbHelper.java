package com.example.mighty.airtelapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mighty.airtelapp.data.DataContract.DataEntry;

public class DataDbHelper extends SQLiteOpenHelper {

    //Database name
    private static final String DATABASE_NAME = "users.db";

    // Database version
    private static final int DATABASE_VERSION = 2;

    // constructor of the db helper
    public DataDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_AIRDAT_TABLE = "CREATE TABLE " + DataEntry.TABLE_NAME +  "("+
                DataEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                DataEntry.COLUMN_RECIPIENT_NUMBER + " TEXT , " +
                DataEntry.COLUMN_DATA_BUNDLE_NAME + " TEXT , " +
                DataEntry.COLUMN_DATA_BUNDLE_VALUE + " TEXT , " +
                DataEntry.COLUMN_DATA_BUNDLE_COST + " TEXT , " +
                DataEntry.COLUMN_SPINNER_ROW + " TEXT , " +
                DataEntry.COLUMN_TIME_RECEIVED + " NUMERIC , " +
                DataEntry.COLUMN_STATUS + " TEXT , " +
                DataEntry.COLUMN_TIME_DONE + " NUMERIC " + ");";
        db.execSQL(SQL_CREATE_AIRDAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME);
//        onCreate(db);
    }

}
