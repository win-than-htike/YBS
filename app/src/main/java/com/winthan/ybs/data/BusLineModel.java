package com.winthan.ybs.data;

import com.google.gson.reflect.TypeToken;
import com.winthan.ybs.YBSApp;
import com.winthan.ybs.utils.CommonInstances;
import com.winthan.ybs.utils.JsonUtils;
import com.winthan.ybs.utils.MMFontConvert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.winthan.ybs.utils.MMFontConvert.zawgyiToUni;

/**
 * Created by winthanhtike on 1/10/17.
 */

public class BusLineModel {

    private static final String BUS_LIST = "bus_line.json";

    private static BusLineModel objInstance;

    private List<String[]> busLines;

    private BusLineModel() {
        busLines = initializeBusLinesList();
    }

    public static BusLineModel getInstance(){
        if (objInstance == null){
            objInstance = new BusLineModel();
        }
        return objInstance;
    }

    public List<String[]> getBusLines() {
        return busLines;
    }

    private List<String[]> initializeBusLinesList(){

        List<String[]> buses = new ArrayList<>();

        try {
            String basicItem = JsonUtils.getInstance().loadBasicItemData(BUS_LIST);
            String covertedText = MMFontConvert.zawgyiToUni(basicItem).toString();

            Type listType = new TypeToken<BusLines>(){
            }.getType();
            BusLines bus = CommonInstances.getGsonInstance().fromJson(covertedText, listType);
            buses = bus.getBusLines();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return buses;
    }

    public void addBusLine(){

        BusDatabaseHandler handler = new BusDatabaseHandler(YBSApp.getContext());

        for (String[] busLineData : busLines){
            handler.addBusLine(busLineData);
        }
    }

}
