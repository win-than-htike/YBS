package com.winthan.ybs;

import android.app.Application;
import android.content.Context;

/**
 * Created by winthanhtike on 1/9/17.
 */

public class YBSApp extends Application {

    public static final String TAG = YBSApp.class.getSimpleName();

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
