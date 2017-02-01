package com.winthan.ybs.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.winthan.ybs.YBSApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winthanhtike on 1/9/17.
 */

public class Bus {

    @SerializedName("bus_no")
    private int busNo;

    @SerializedName("bus_color")
    private String busColor;

    @SerializedName("bus_stop")
    private String busStop;

    public Bus() {
    }

    public Bus(int busNo, String busColor, String busStop) {
        this.busNo = busNo;
        this.busStop = busStop;
    }

    public int getBusNo() {
        return busNo;
    }

    public void setBusNo(int busNo) {
        this.busNo = busNo;
    }

    public String getBusStop() {
        return busStop;
    }

    public void setBusStop(String busStop) {
        this.busStop = busStop;
    }

    public String getBusColor() {
        return busColor;
    }

    public void setBusColor(String busColor) {
        this.busColor = busColor;
    }

    public static void saveBuses(List<Bus> buses){

        Context context = YBSApp.getContext();

        ContentValues[] busesValues = new ContentValues[buses.size()];

        for (int index = 0; index < buses.size(); index++){

            Bus bus = buses.get(index);
            busesValues[index] = bus.parseToContentValues();

        }

        int insertedCount = context.getContentResolver().bulkInsert(BusContract.BusEntry.CONTENT_URI, busesValues);

        Log.d(YBSApp.TAG, "Bulk inserted into bus table : " + insertedCount);

    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(BusContract.BusEntry.COLUMN_BUS_NO, busNo);
        cv.put(BusContract.BusEntry.COLUMN_BUS_COLOR, busColor);
        cv.put(BusContract.BusEntry.COLUMN_BUS_STOP, busStop);
        return cv;
    }

    public static Bus parseFromCursor(Cursor data) {
        Bus bus = new Bus();
        bus.busNo = Integer.parseInt(data.getString(data.getColumnIndex(BusContract.BusEntry.COLUMN_BUS_NO)));
        bus.busColor = data.getString(data.getColumnIndex(BusContract.BusEntry.COLUMN_BUS_COLOR));
        bus.busStop = data.getString(data.getColumnIndex(BusContract.BusEntry.COLUMN_BUS_STOP));
        return bus;
    }

}
