package com.winthan.ybs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winthan.ybs.R;
import com.winthan.ybs.YBSApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by winthanhtike on 1/10/17.
 */

public class BusNumberAdapter extends RecyclerView.Adapter<BusNumberAdapter.BusNumberViewHolder> {

    private LayoutInflater inflater;
    private List<String> busNo;

    public BusNumberAdapter(List<String> busNo) {
        this.busNo = busNo;
        inflater = LayoutInflater.from(YBSApp.getContext());
    }

    @Override
    public BusNumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bus_no_card,parent,false);
        return new BusNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusNumberViewHolder holder, int position) {
        holder.tvBusNo.setText(busNo.get(position));
    }

    @Override
    public int getItemCount() {
        return busNo.size();
    }

    public class BusNumberViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_bus_number)
        TextView tvBusNo;

        public BusNumberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
