package com.winthan.ybs.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.winthan.ybs.R;
import com.winthan.ybs.YBSApp;
import com.winthan.ybs.data.Bus;
import com.winthan.ybs.data.BusLine;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;
import static android.view.View.Y;

/**
 * Created by winthanhtike on 1/11/17.
 */

public class AutoCompleteTextViewAdapter extends ArrayAdapter<BusLine> {

    private ArrayList<BusLine> busLineList;
    private ArrayList<BusLine> suggestions;
    private ArrayList<BusLine> itemsAll;

    public AutoCompleteTextViewAdapter(Context context, ArrayList<BusLine> busLineList) {
        super(context, 0, busLineList);
        this.busLineList = busLineList;
        this.suggestions = new ArrayList<BusLine>();
        this.itemsAll = (ArrayList<BusLine>) busLineList.clone();

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) YBSApp.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(android.R.layout.simple_dropdown_item_1line, parent,false);
        }

        TextView busStop = (TextView) v.findViewById(android.R.id.text1);
        BusLine busLine = busLineList.get(position);
//        busStop.setText(busLine.getBusStop());

        return v;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
//            String str = ((BusLine)(resultValue)).getBusStop();
            return null;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (BusLine busLine : itemsAll) {
//                    if(busLine.getBusStop().toLowerCase().startsWith(constraint.toString().toLowerCase())){
//                        suggestions.add(busLine);
//                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<BusLine> filteredList = (ArrayList<BusLine>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (BusLine c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}
