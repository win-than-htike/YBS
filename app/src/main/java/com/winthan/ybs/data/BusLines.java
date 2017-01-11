package com.winthan.ybs.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by winthanhtike on 1/10/17.
 */

public class BusLines {

    @SerializedName("busline")
    private List<String[]> busLines;

    public BusLines(List<String[]> busLines) {
        this.busLines = busLines;
    }

    public List<String[]> getBusLines() {
        return busLines;
    }

    public void setBusLines(List<String[]> busLines) {
        this.busLines = busLines;
    }

}
