package com.winthan.ybs.data;

import com.google.gson.reflect.TypeToken;
import com.winthan.ybs.utils.CommonInstances;
import com.winthan.ybs.utils.JsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by winthanhtike on 1/13/17.
 */

public class BusLineData {

    private static final String BUSLINE_LIST = "bus_stops.json";

    private static BusLineData objInstance;

    private BusLineData() {
        loadBusLineData();
    }

    public static BusLineData getInstance(){

        if (objInstance == null){
            objInstance = new BusLineData();
        }

        return objInstance;

    }

    public void loadBusLineData(){

        try {
            String basicItem = JsonUtils.getInstance().loadBasicItemData(BUSLINE_LIST);
            Type listType = new TypeToken<List<BusLine>>(){
            }.getType();
            List<BusLine> buses = CommonInstances.getGsonInstance().fromJson(basicItem, listType);
            BusLine.saveBusLines(buses);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
