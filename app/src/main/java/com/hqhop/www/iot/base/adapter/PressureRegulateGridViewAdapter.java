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
 * 导航模块调压出口压力的Adapter
 * Created by allen on 2017/7/8.
 */

public class PressureRegulateGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> stations = new ArrayList<>();

    private List<String> values = new ArrayList<>();

    private List<String> dates = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public PressureRegulateGridViewAdapter(Context context, List<String> stations, List<String> values, List<String> dates) {
        this.context = context;
        this.stations = stations;
        this.values = values;
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
            convertView = layoutInflater.inflate(R.layout.item_pressure_regulate_grid_view, null);
            viewHolder.tvStation = (TextView) convertView.findViewById(R.id.tv_station_pressure);
            viewHolder.tvValue = (TextView) convertView.findViewById(R.id.tv_value_pressure);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date_pressure);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < stations.size()) {
            viewHolder.tvStation.setText(stations.get(position));
            viewHolder.tvValue.setText(values.get(position));
            viewHolder.tvDate.setText(dates.get(position));
        }

        return convertView;
    }

    private final class ViewHolder {
        TextView tvStation;
        TextView tvValue;
        TextView tvDate;
    }
}
