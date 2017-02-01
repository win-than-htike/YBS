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

public class BusData {

    private static final String BUS_LIST = "bus.json";

    private static BusData objInstance;

    private BusData(){
        loadBusData();
    }

    public static BusData getInstance(){
        if (objInstance == null){
            objInstance = new BusData();
        }

        return objInstance;
    }

    public void loadBusData(){

        try {
            String basicItem = JsonUtils.getInstance().loadBasicItemData(BUS_LIST);
            Type listType = new TypeToken<List<Bus>>(){
            }.getType();
            List<Bus> buses = CommonInstances.getGsonInstance().fromJson(basicItem, listType);
            Bus.saveBuses(buses);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
