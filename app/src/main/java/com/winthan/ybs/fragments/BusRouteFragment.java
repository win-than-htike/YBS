package com.winthan.ybs.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winthan.ybs.R;
import com.winthan.ybs.adapters.BusLineAdapter;
import com.winthan.ybs.data.BusDatabaseHandler;
import com.winthan.ybs.data.BusLine;
import com.winthan.ybs.data.BusLineModel;
import com.winthan.ybs.utils.CSVFile;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusRouteFragment extends Fragment {

    @BindView(R.id.rv_buses_route)
    RecyclerView rvBusesRoute;

    private List<BusLine> busLines;

    private BusLineAdapter mAdapter;

    private BusDatabaseHandler handler;

    public BusRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bus_route, container, false);
        ButterKnife.bind(this,view);

        handler = new BusDatabaseHandler(getActivity());
        busLines = handler.getAllBusLine();

        if (busLines.size() <= 0){
            BusLineModel.getInstance().addBusLine();
            busLines = handler.getAllBusLine();

        }

        rvBusesRoute.setHasFixedSize(true);
        rvBusesRoute.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBusesRoute.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new BusLineAdapter(busLines);
        rvBusesRoute.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.close();
    }
}
