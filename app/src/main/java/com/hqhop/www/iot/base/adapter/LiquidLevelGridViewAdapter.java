package com.hqhop.www.iot.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hqhop.www.iot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航模块液位信息的Adapter
 * Created by allen on 2017/7/8.
 */

public class LiquidLevelGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> stations = new ArrayList<>();

    private List<String> informations = new ArrayList<>();

    private List<String> dates = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public LiquidLevelGridViewAdapter(Context context, List<String> stations, List<String> informations, List<String> dates) {
        this.context = context;
        this.stations = stations;
        this.informations = informations;
        this.dates = dates;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_liquid_level_grid_view, null);
            viewHolder.tvStation = (TextView) convertView.findViewById(R.id.tv_station_liquid);
            viewHolder.tvInformation = (TextView) convertView.findViewById(R.id.tv_information_liquid);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date_liquid);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < stations.size()) {
            viewHolder.tvStation.setText(stations.get(position));
            viewHolder.tvInformation.setText(informations.get(position));
            viewHolder.tvDate.setText(dates.get(position));
        }

        return convertView;
    }

    private final class ViewHolder {
        TextView tvStation;
        TextView tvInformation;
        TextView tvDate;
    }
}
