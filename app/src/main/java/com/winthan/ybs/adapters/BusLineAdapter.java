package com.winthan.ybs.adapters;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winthan.ybs.R;
import com.winthan.ybs.YBSApp;
import com.winthan.ybs.data.BusLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.media.CamcorderProfile.get;

/**
 * Created by winthanhtike on 1/10/17.
 */

public class BusLineAdapter extends RecyclerView.Adapter<BusLineAdapter.BusLineViewHolder> {

    private LayoutInflater inflater;
    private List<BusLine> busLine;

    public BusLineAdapter(List<BusLine> busLine) {
        this.busLine = busLine;
        inflater = LayoutInflater.from(YBSApp.getContext());
    }

    @Override
    public BusLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.busline_card,parent,false);
        return new BusLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusLineViewHolder holder, int position) {

        holder.tvBusLineName.setText(busLine.get(position).getBusStop());

        holder.rvBusLine.setHasFixedSize(true);
        holder.rvBusLine.setLayoutManager(new LinearLayoutManager(YBSApp.getContext(),LinearLayoutManager.HORIZONTAL,false));
        holder.rvBusLine.setItemAnimator(new DefaultItemAnimator());

        String[] busNum = busLine.get(position).getBusLine().split(",");
        ArrayList<String> busNo = new ArrayList<>(Arrays.asList(busNum));

        holder.mAdapter = new BusNumberAdapter(busNo);
        holder.rvBusLine.setAdapter(holder.mAdapter);

    }

    public void clearData(){
        busLine.clear();
    }

    @Override
    public int getItemCount() {
        return busLine.size();
    }

    public class BusLineViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_bus_stop_name)
        TextView tvBusLineName;

        @BindView(R.id.rv_bus_line)
        RecyclerView rvBusLine;

        private BusNumberAdapter mAdapter;

        public BusLineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

    }
}
