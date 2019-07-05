package com.hqhop.www.iot.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqhop.www.iot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航模块总览信息的Adapter
 * ImageView x 1，TextView x 3
 * Created by allen on 2017/7/7.
 */

public class LocationTuneGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> stations = new ArrayList<>();

    private List<String> addresses = new ArrayList<>();

    private LayoutInflater layoutInflater;

    private ViewHolder viewHolder;

    private int clickedItemIndex = -1;

    public LocationTuneGridViewAdapter(Context context, List<String> stations, List<String> addresses) {
        this.context = context;
        this.stations = stations;
        this.addresses = addresses;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
//        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_location_tune_grid_view, null);
            viewHolder.tvStation = (TextView) convertView.findViewById(R.id.tv_station_location_tune);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tv_address_location_tune);
            viewHolder.ivSelected = (ImageView) convertView.findViewById(R.id.iv_selected_location_tune);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvStation.setText(stations.get(position));
        viewHolder.tvAddress.setText(addresses.get(position));
        if (clickedItemIndex == position) {
            viewHolder.ivSelected.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivSelected.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void setSelected(int position) {
        clickedItemIndex = position;
    }

    private final class ViewHolder {
        TextView tvStation;
        TextView tvAddress;
        ImageView ivSelected;
    }
}
