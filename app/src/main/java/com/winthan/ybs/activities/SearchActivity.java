package com.winthan.ybs.activities;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.winthan.ybs.R;
import com.winthan.ybs.adapters.BusLineAdapter;
import com.winthan.ybs.data.BusContract;
import com.winthan.ybs.data.BusLine;
import com.winthan.ybs.utils.PrefManager;
import com.winthan.ybs.utils.SearchUiHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    private static final int LOADER_SEARCH = 3;

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @BindView(R.id.rv_buses)
    RecyclerView rvBuses;

    @BindView(R.id.search_clear)
    ImageButton ivSearchClear;

    private SearchUiHelper mSearchHelper;

    private BusLineAdapter mAdapter;

    private List<BusLine> buses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this, this);

        PrefManager prefManager = new PrefManager(this);
        prefManager.setFirstTimeLaunch(false);

        mAdapter = new BusLineAdapter(buses);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.clearData();
                Cursor cursor = getContentResolver().query(BusContract.BusLineEntry.CONTENT_URI,
                        null,
                        BusContract.BusLineEntry.COLUMN_BUS_STOP_NAME + " LIKE ?",
                        new String[]{"%" +newText + "%"},
                        null);

                try {

                    if (cursor != null && cursor.moveToFirst()) {
                        Log.i("Search", cursor.getCount() + "");
                        do {

                            BusLine bus = BusLine.parseFromCursor(cursor);
                            buses.add(bus);

                        } while (cursor.moveToNext());

                    }

                } finally {
                    cursor.close();
                }

                rvBuses.setHasFixedSize(true);
                rvBuses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvBuses.setItemAnimator(new DefaultItemAnimator());
                rvBuses.setAdapter(mAdapter);

                return true;
            }
        });

    }

}
