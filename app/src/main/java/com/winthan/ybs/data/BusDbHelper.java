package com.winthan.ybs.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by winthanhtike on 1/12/17.
 */

public class BusDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "ybs.db";

    private static final int DATABASE_VERSION = 1;

    public BusDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_BUS_TABLE = "CREATE TABLE " + BusContract.BusEntry.TABLE_COLUMN + " ("
                + BusContract.BusEntry.COLUMN_BUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BusContract.BusEntry.COLUMN_BUS_NO + " INTEGER, "
                + BusContract.BusEntry.COLUMN_BUS_COLOR + " TEXT, "
                + BusContract.BusEntry.COLUMN_BUS_STOP + " TEXT " + ");";

        String CREATE_BUS_LINE_TABLE = "CREATE TABLE " + BusContract.BusLineEntry.TABLE_COLUMN + " ("
                + BusContract.BusLineEntry.COLUMN_BUS_STOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BusContract.BusLineEntry.COLUMN_BUS_STOP_NAME + " TEXT, "
                + BusContract.BusLineEntry.COLUMN_BUS_NO + " TEXT" + " );";

        sqLiteDatabase.execSQL(CREATE_BUS_TABLE);
        sqLiteDatabase.execSQL(CREATE_BUS_LINE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BusContract.BusEntry.TABLE_COLUMN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BusContract.BusLineEntry.TABLE_COLUMN);
        onCreate(sqLiteDatabase);
    }
}
