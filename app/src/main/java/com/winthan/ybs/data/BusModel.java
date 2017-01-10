package com.winthan.ybs.data;

import android.database.sqlite.SQLiteDatabase;

import com.google.gson.reflect.TypeToken;
import com.winthan.ybs.YBSApp;
import com.winthan.ybs.utils.CommonInstances;
import com.winthan.ybs.utils.JsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by winthanhtike on 1/9/17.
 */

public class BusModel {

    private static final String BUS_LIST = "bus.json";

    private static BusModel objInstance;

    private static List<Bus> busList;

    private BusModel(){
        busList = initializeBusesList();
    }

    public static BusModel getInstance(){
        if (objInstance == null){
            objInstance = new BusModel();
        }

        return objInstance;
    }

    public List<Bus> hotelsList() {
        return busList;
    }

    private List<Bus> initializeBusesList(){

        List<Bus> buses = new ArrayList<>();

        try {
            String basicItem = JsonUtils.getInstance().loadBasicItemData(BUS_LIST);
            Type listType = new TypeToken<List<Bus>>(){
            }.getType();
            buses = CommonInstances.getGsonInstance().fromJson(basicItem, listType);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return buses;
    }

    public void addData(){

        BusDatabaseHandler handler = new BusDatabaseHandler(YBSApp.getContext());
        for (int i = 0; i < busList.size(); i++){
            handler.addBus(busList.get(i));
        }

    }

    public String convertArrayToString(String[] array){

        String str = "";
        for (int i = 0;i<array.length; i++) {

            str = str+array[i];
            if(i<array.length-1){
                str = str+",";
            }
        }
        return str;
    }



}
