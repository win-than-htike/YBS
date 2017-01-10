package com.winthan.ybs.data;

/**
 * Created by winthanhtike on 1/10/17.
 */

public class BusLine {

    private String busStop;

    private String busLine;

    public BusLine() {
    }

    public BusLine(String busStop, String busLine) {
        this.busStop = busStop;
        this.busLine = busLine;
    }

    public String getBusStop() {
        return busStop;
    }

    public void setBusStop(String busStop) {
        this.busStop = busStop;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }
}
