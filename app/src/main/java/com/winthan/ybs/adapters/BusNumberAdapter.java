package com.winthan.ybs.adapters;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.winthan.ybs.R;
import com.winthan.ybs.YBSApp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        View view = inflater.inflate(R.layout.bus_no_card, parent, false);
        return new BusNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusNumberViewHolder holder, int position) {

        String image = busNo.get(position) + ".png";
        holder.tvBusNo.setImageBitmap(getBitmapFromAssets(image));

    }

    private Bitmap getBitmapFromAssets(String filename) {

        AssetManager am = YBSApp.getContext().getAssets();
        InputStream is = null;

        try {
            is = am.open("photo/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;

    }

    @Override
    public int getItemCount() {
        Log.i("BusNo",busNo.size()+"");
        return busNo.size();
    }

    public class BusNumberViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_bus_number)
        ImageView tvBusNo;

        public BusNumberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
