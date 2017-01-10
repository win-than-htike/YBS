package com.winthan.ybs;

import android.app.Application;
import android.content.Context;

/**
 * Created by winthanhtike on 1/9/17.
 */

public class YBSApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }
}
