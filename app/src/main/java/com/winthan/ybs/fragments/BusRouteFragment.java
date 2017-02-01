package com.winthan.ybs.fragments;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winthan.ybs.R;
import com.winthan.ybs.adapters.BusLineAdapter;
import com.winthan.ybs.data.BusContract;
import com.winthan.ybs.data.BusLine;
import com.winthan.ybs.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusRouteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_BUSLINE = 2;

    @BindView(R.id.rv_buses_route)
    RecyclerView rvBusesRoute;

    private BusLineAdapter mAdapter;

    public BusRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bus_route, container, false);
        ButterKnife.bind(this,view);

        PrefManager prefManager = new PrefManager(getActivity());
        prefManager.setFirstTimeLaunch(false);

        rvBusesRoute.setHasFixedSize(true);
        rvBusesRoute.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBusesRoute.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getLoaderManager().initLoader(LOADER_BUSLINE, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(),
                BusContract.BusLineEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        List<BusLine> buses = new ArrayList<>();

            if (cursor != null && cursor.moveToFirst()){

                do {

                    BusLine bus = BusLine.parseFromCursor(cursor);
                    buses.add(bus);

                }while (cursor.moveToNext());

            }

        mAdapter = new BusLineAdapter(buses);
        rvBusesRoute.setAdapter(mAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
