package com.winthan.ybs.data;

import com.google.gson.annotations.SerializedName;

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
}
