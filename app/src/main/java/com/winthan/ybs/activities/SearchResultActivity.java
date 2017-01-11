package com.winthan.ybs.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.winthan.ybs.adapters.AutoCompleteTextViewAdapter;
import com.winthan.ybs.adapters.BusLineAdapter;
import com.winthan.ybs.data.Bus;
import com.winthan.ybs.adapters.BusAdapter;
import com.winthan.ybs.R;
import com.winthan.ybs.YBSApp;
import com.winthan.ybs.data.BusDatabaseHandler;
import com.winthan.ybs.data.BusLine;
import com.winthan.ybs.data.BusLineModel;
import com.winthan.ybs.data.BusLines;
import com.winthan.ybs.data.BusModel;
import com.winthan.ybs.utils.ItemClickListener;
import com.winthan.ybs.utils.MMFontConvert;
import com.winthan.ybs.utils.SearchUiHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.provider.CalendarContract.Instances.END;

public class SearchResultActivity extends AppCompatActivity implements ItemClickListener {

    @BindView(R.id.rv_buses)
    RecyclerView rvBuses;

    @BindView(R.id.search_view)
    AutoCompleteTextView etSearch;

    @BindView(R.id.tv_search_text)
    TextView tvSearchText;

    @BindView(R.id.search_clear)
    ImageView ivSearchClear;

    private BusLineAdapter mAdapter;

    private SearchUiHelper mSearchHelper;
    private List<BusLine> busList = new ArrayList<>();

    private BusDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this, this);

        db = new BusDatabaseHandler(getApplicationContext());
//        List<BusLine> busLineList = db.getAllBusLine();
//
//        String[] bustop = new String[busLineList.size()];
//        for (int i = 0; i < busLineList.size(); i++) {
//            bustop[i] = busLineList.get(i).getBusStop();
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchResultActivity.this, android.R.layout.simple_dropdown_item_1line, bustop);
//        etSearch.setAdapter(adapter);
//        final List<BusLine> busLineList = db.getAllBusLine();
//        BusLine[] busLines = new BusLine[busLineList.size()];
//        etSearch.setThreshold(3);
//        busLines = busLineList.toArray(busLines);
//        ArrayAdapter<BusLine> adapter = new ArrayAdapter<BusLine>(SearchResultActivity.this, android.R.layout.simple_dropdown_item_1line, busLines) {
//            @NonNull
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                TextView itemView = (TextView) super.getView(position, convertView, parent);
//                itemView.setText(getItem(position).getBusStop());
//                return itemView;
//            }
//        };
//        etSearch.setAdapter(adapter);

//        etSearch.setThreshold(3);
//        ArrayList<BusLine> busLines = (ArrayList<BusLine>) db.getAllBusLine();
//        autoCompleteTextViewAdapter = new AutoCompleteTextViewAdapter(this,busLines);
//        etSearch.setAdapter(autoCompleteTextViewAdapter);

        mSearchHelper = new SearchUiHelper(etSearch, ivSearchClear);
        mSearchHelper.setOnSearchListener(new SearchUiHelper.OnSearchListener() {
            @Override
            public void onSearchTextChanged(CharSequence searchText) {


            }

            @Override
            public void onSearchSubmit(CharSequence searchText) {

                if (MMFontConvert.isZawgyi(searchText.toString())) {
                    busList = db.getSearchBusLine(MMFontConvert.zawgyiToUni(searchText.toString()).toString());
                    if (busList.size() <= 0) {
                        BusLineModel.getInstance().addBusLine();
                        busList = db.getSearchBusLine(MMFontConvert.zawgyiToUni(searchText.toString()).toString());
                    }

                } else {
                    busList = db.getSearchBusLine(searchText.toString());
                    if (busList.size() <= 0) {
                        BusLineModel.getInstance().addBusLine();
                        busList = db.getSearchBusLine(searchText.toString());
                    }
                }

                Log.i("Select From DB", busList.size() + "");
                rvBuses.setHasFixedSize(true);
                rvBuses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvBuses.setItemAnimator(new DefaultItemAnimator());

                mAdapter = new BusLineAdapter(busList);
                rvBuses.setAdapter(mAdapter);


            }

            @Override
            public void onClearSearch() {
                mAdapter.clearData();
            }

        });

    }

    @Override
    public void onTapBus(Bus bus, int position) {

    }

    private class LoadBusLine extends AsyncTask<Void, Void, List<BusLine>> {

        private String searchText;

        public LoadBusLine(String searchText) {

            this.searchText = searchText;

        }

        @Override
        protected List<BusLine> doInBackground(Void... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(List<BusLine> busLines) {
            super.onPostExecute(busLines);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
