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
 * 导航模块报警信息的Adapter
 * Created by allen on 2017/7/8.
 */

public class AlertGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<Integer> levels = new ArrayList<>();

    private List<String> stations = new ArrayList<>();

    private List<String> reasons = new ArrayList<>();

    private List<String> dates = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public AlertGridViewAdapter(Context context, List<Integer> levels, List<String> stations, List<String> reasons, List<String> dates) {
        this.context = context;
        this.levels = levels;
        this.stations = stations;
        this.reasons = reasons;
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
            convertView = layoutInflater.inflate(R.layout.item_alert_grid_view, null);
            viewHolder.ivLevel = (ImageView) convertView.findViewById(R.id.iv_level_alert);
            viewHolder.tvStation = (TextView) convertView.findViewById(R.id.tv_station_alert);
            viewHolder.tvReason = (TextView) convertView.findViewById(R.id.tv_reason_alert);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date_alert);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < stations.size()) {
//            // 显示危险级别图片
//            if (levels.get(position) <= 1) {
//                viewHolder.ivLevel.setVisibility(View.INVISIBLE);
//            } else if (levels.get(position) > 1 && levels.get(position) <= 10) {
//                viewHolder.ivLevel.setVisibility(View.VISIBLE);
//                viewHolder.ivLevel.setImageResource(R.drawable.ic_emergency_3rd);
//            } else if (levels.get(position) > 10) {
//                viewHolder.ivLevel.setVisibility(View.VISIBLE);
//                viewHolder.ivLevel.setImageResource(R.drawable.ic_emergency_2nd);
//            }
            viewHolder.tvStation.setText(stations.get(position));
            viewHolder.tvReason.setText(reasons.get(position));
            viewHolder.tvDate.setText(dates.get(position));
        }

        return convertView;
    }

    private final class ViewHolder {
        ImageView ivLevel;
        TextView tvStation;
        TextView tvReason;
        TextView tvDate;
    }
}
