package com.winthan.ybs.fragments;


import android.app.LoaderManager;
import android.content.Context;
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
import com.winthan.ybs.adapters.BusAdapter;
import com.winthan.ybs.data.Bus;
import com.winthan.ybs.data.BusContract;
import com.winthan.ybs.utils.ItemClickListener;
import com.winthan.ybs.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.rv_buses)
    RecyclerView rvBuses;

    private static final int BUS_LOADER = 1;

    private BusAdapter mAdapter;

    private ItemClickListener itemClickListener;

    public BusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        itemClickListener = (ItemClickListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bus, container, false);
        ButterKnife.bind(this,view);

        PrefManager prefManager = new PrefManager(getActivity());
        prefManager.setFirstTimeLaunch(false);

        rvBuses.setHasFixedSize(true);
        rvBuses.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBuses.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getLoaderManager().initLoader(BUS_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        return new CursorLoader(getActivity(),
                BusContract.BusEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        List<Bus> buses = new ArrayList<>();

            if (cursor != null && cursor.moveToFirst()){

                do {

                    Bus bus = Bus.parseFromCursor(cursor);
                    buses.add(bus);

                }while (cursor.moveToNext());

            }

        mAdapter = new BusAdapter(buses,itemClickListener);
        rvBuses.setAdapter(mAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
