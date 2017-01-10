package com.winthan.ybs.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.winthan.ybs.data.Bus;
import com.winthan.ybs.R;
import com.winthan.ybs.YBSApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by winthanhtike on 1/9/17.
 */

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private LayoutInflater inflater;

    private List<Bus> buses;

    public BusAdapter(List<Bus> buses) {
        this.buses = buses;
        inflater = LayoutInflater.from(YBSApp.getContext());
    }

    @Override
    public BusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bus_card, parent, false);
        return new BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusViewHolder holder, int position) {
        holder.bindData(buses.get(position));
    }

    @Override
    public int getItemCount() {
        return buses.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_color)
        ImageView ivColor;

        @BindView(R.id.tv_start)
        TextView tvStart;

        @BindView(R.id.tv_end)
        TextView tvEnd;

        @BindView(R.id.tv_bus_no)
        TextView tvBusNo;

        public BusViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Bus bus) {

            if (bus != null) {

                String[] buses = bus.getBusStop().split(",");

                tvStart.setText(buses[0]);

                tvEnd.setText(buses[buses.length-1]);

                tvBusNo.setText(String.valueOf(bus.getBusNo()));

                switch (bus.getBusColor()){

                    case "red":
                        ivColor.setBackgroundColor(ContextCompat.getColor(YBSApp.getContext(),R.color.red));
                        break;

                    case "blue":
                        ivColor.setBackgroundColor(ContextCompat.getColor(YBSApp.getContext(),R.color.blue));
                        break;

                    case "green":
                        ivColor.setBackgroundColor(ContextCompat.getColor(YBSApp.getContext(),R.color.green));
                        break;

                    case "purple":
                        ivColor.setBackgroundColor(ContextCompat.getColor(YBSApp.getContext(),R.color.purple));
                        break;

                    case "brown":
                        ivColor.setBackgroundColor(ContextCompat.getColor(YBSApp.getContext(),R.color.brown));
                        break;

                }

            } else {
                Toast.makeText(YBSApp.getContext(), "Object is Null", Toast.LENGTH_SHORT).show();
            }


        }

    }
}
