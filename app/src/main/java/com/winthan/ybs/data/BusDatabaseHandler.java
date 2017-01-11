package com.winthan.ybs.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.winthan.ybs.utils.MMFontConvert;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static android.R.attr.theme;
import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Created by winthanhtike on 1/9/17.
 */

public class BusDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "busdb";

    private static final String TABLE_CONTACTS = "bus";

    private static final String TABLE_BUS_LINE = "busline";

    private static final String KEY_BUS_ID = "_id";
    private static final String KEY_BUS_NO = "bus_no";
    private static final String KEY_BUS_COLOR = "bus_color";
    private static final String KEY_BUS_STOP = "bus_stop";

    private static final String KEY_BUS_STOP_ID = "_id";
    private static final String KEY_BUS_STOP_NAME = "bus_stop_name";
    private static final String KEY_BUS_LINE = "bus_line";

    public BusDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_BUS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_BUS_ID + " INTEGER PRIMARY KEY," + KEY_BUS_NO + " INTEGER," + KEY_BUS_COLOR + " TEXT,"
                + KEY_BUS_STOP + " TEXT" + ")";

        String CREATE_BUS_LINE_TABLE = "CREATE TABLE " + TABLE_BUS_LINE + "("
                + KEY_BUS_STOP_ID + " INTEGER PRIMARY KEY," + KEY_BUS_STOP_NAME + " TEXT,"
                + KEY_BUS_LINE + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_BUS_TABLE);
        sqLiteDatabase.execSQL(CREATE_BUS_LINE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BUS_LINE);
        onCreate(sqLiteDatabase);

    }

    public void addBus(Bus bus) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BUS_NO, bus.getBusNo());
        values.put(KEY_BUS_COLOR, bus.getBusColor());
        values.put(KEY_BUS_STOP,bus.getBusStop());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();

    }

    public void addBusLine(String[] busLine){

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BUS_STOP_NAME,busLine[0]);
        values.put(KEY_BUS_LINE,busLine[1]);

        db.insert(TABLE_BUS_LINE,null,values);
        db.close();


    }

    public List<BusLine> getAllBusLine(){

        List<BusLine> busLines = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_BUS_LINE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){

            do {

                BusLine busLine = new BusLine();
                busLine.setBusStop(MMFontConvert.uniToZawgyi(cursor.getString(cursor.getColumnIndex(KEY_BUS_STOP_NAME))).toString());
                busLine.setBusLine(MMFontConvert.uniToZawgyi(cursor.getString(cursor.getColumnIndex(KEY_BUS_LINE))).toString());
                busLines.add(busLine);

            }while (cursor.moveToNext());

        }

        return busLines;

    }

    public List<BusLine> getSearchBusLine(String searchQuery){

        List<BusLine> busLines = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query
                (
                        TABLE_BUS_LINE,
                        new String[] { KEY_BUS_STOP_NAME, KEY_BUS_LINE },
                        KEY_BUS_STOP_NAME + " LIKE '%" + searchQuery + "%'",
                        null, null, null, null, null
                );

        if (cursor.moveToFirst()){

            do {

                BusLine busLine = new BusLine();
                busLine.setBusStop(MMFontConvert.uniToZawgyi(cursor.getString(cursor.getColumnIndex(KEY_BUS_STOP_NAME))).toString());
                busLine.setBusLine(MMFontConvert.uniToZawgyi(cursor.getString(cursor.getColumnIndex(KEY_BUS_LINE))).toString());
                busLines.add(busLine);

            }while (cursor.moveToNext());

        }

        return busLines;

    }

    public List<Bus> getAllBuses() {

        List<Bus> buses = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {

                Bus bus = new Bus();
                bus.setBusNo(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_BUS_NO))));
                bus.setBusColor(cursor.getString(cursor.getColumnIndex(KEY_BUS_COLOR)));
                bus.setBusStop(cursor.getString(cursor.getColumnIndex(KEY_BUS_STOP)));
                buses.add(bus);

            } while (cursor.moveToNext());

        }

        return buses;

    }



}
