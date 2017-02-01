package com.winthan.ybs.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.winthan.ybs.YBSApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winthanhtike on 1/10/17.
 */

public class BusLine {

    @SerializedName("name")
    private String name;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lng")
    private String lng;

    @SerializedName("bus_ids")
    private String[] busNos;

    public BusLine() {
    }

    public BusLine(String name, String lat, String lng, String[] busNos) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.busNos = busNos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String[] getBusNos() {
        return busNos;
    }

    public void setBusNos(String[] busNos) {
        this.busNos = busNos;
    }

    public static void saveBusLines(List<BusLine> busLines) {

        Context context = YBSApp.getContext();

        ContentValues[] busLineValues = new ContentValues[busLines.size()];

        for (int index = 0; index < busLines.size(); index++) {

            BusLine busLine = busLines.get(index);
            busLineValues[index] = busLine.parseToContentValues();

        }

        int insertCount = context.getContentResolver().bulkInsert(BusContract.BusLineEntry.CONTENT_URI,busLineValues);

        Log.d(YBSApp.TAG, "Bulk inserted into busline table : " + insertCount);

    }

    private ContentValues parseToContentValues() {

        String busNo = convertArrayToString(busNos);

        ContentValues cv = new ContentValues();

        cv.put(BusContract.BusLineEntry.COLUMN_BUS_STOP_NAME, name);
        cv.put(BusContract.BusLineEntry.COLUMN_BUS_NO, busNo);

        return cv;

    }

    public static BusLine parseFromCursor(Cursor data){

        BusLine busLine = new BusLine();
        busLine.name = data.getString(data.getColumnIndex(BusContract.BusLineEntry.COLUMN_BUS_STOP_NAME));
        String busNo = data.getString(data.getColumnIndex(BusContract.BusLineEntry.COLUMN_BUS_NO));
        busLine.busNos = busNo.split("__,__");
        return busLine;

    }

    public static String convertArrayToString(String[] array){
        String strSeparator = "__,__";
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
}
