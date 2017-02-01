package com.winthan.ybs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.winthan.ybs.data.BusData;
import com.winthan.ybs.data.BusLineData;
import com.winthan.ybs.utils.PrefManager;

/**
 * Created by winthanhtike on 1/11/17.
 */

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefManager prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()) {
            BusData.getInstance();
            BusLineData.getInstance();
        }

        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }

}
