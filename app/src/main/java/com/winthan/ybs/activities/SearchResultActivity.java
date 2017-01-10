package com.winthan.ybs.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.winthan.ybs.data.Bus;
import com.winthan.ybs.adapters.BusAdapter;
import com.winthan.ybs.R;
import com.winthan.ybs.YBSApp;
import com.winthan.ybs.data.BusDatabaseHandler;
import com.winthan.ybs.data.BusModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity {

    private static final String BUS = "bus";
    private static final String START = "start";
    private static final String END = "end";

    @BindView(R.id.rv_buses)
    RecyclerView rvBuses;

    private BusAdapter mAdapter;

    private List<Bus> busList;

    private String start;

    private String end;

    public static Intent newInstance(String start,String end){

        Intent intent = new Intent(YBSApp.getContext(),SearchResultActivity.class);
        Bundle args = new Bundle();
        args.putString(START,start);
        args.putString(END,end);
        intent.putExtras(args);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this,this);

        if (getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            start = bundle.getString(START);
            end = bundle.getString(END);
        }

        BusDatabaseHandler db = new BusDatabaseHandler(this);
        busList = db.getAllBuses();

        if (busList.size() <= 0){
            BusModel.getInstance().addData();
            busList = db.getAllBuses();
        }

        rvBuses.setHasFixedSize(true);
        rvBuses.setLayoutManager(new LinearLayoutManager(this));
        rvBuses.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new BusAdapter(busList);
        rvBuses.setAdapter(mAdapter);


    }
}
