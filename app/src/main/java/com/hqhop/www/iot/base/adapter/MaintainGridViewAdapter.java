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
 * 导航模块维保信息的Adapter
 * Created by allen on 2017/7/8.
 */

public class MaintainGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> levels = new ArrayList<>();

    private List<String> stations = new ArrayList<>();

    private List<String> deadlines = new ArrayList<>();

    private List<String> dates = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public MaintainGridViewAdapter(Context context, List<String> emergencyDegresses, List<String> stations, List<String> deadlines, List<String> dates) {
        this.context = context;
        this.levels = emergencyDegresses;
        this.stations = stations;
        this.deadlines = deadlines;
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
            convertView = layoutInflater.inflate(R.layout.item_maintain_grid_view, null);
            viewHolder.ivLevel = (ImageView) convertView.findViewById(R.id.iv_level_maintain);
            viewHolder.tvStation = (TextView) convertView.findViewById(R.id.tv_station_maintain);
            viewHolder.tvDeadline = (TextView) convertView.findViewById(R.id.tv_deadline_maintain);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date_maintain);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < stations.size()) {
            // 显示危险级别图片
            if ("1".equals(levels.get(position))) {
                //            viewHolder.ivLevel.setText(levels.get(position));
            } else {
                //            viewHolder.ivLevel.setText(levels.get(position));
            }
            viewHolder.tvStation.setText(stations.get(position));
            viewHolder.tvDeadline.setText(deadlines.get(position));
            viewHolder.tvDate.setText(dates.get(position));
        }

        return convertView;
    }

    private final class ViewHolder {
        ImageView ivLevel;
        TextView tvStation;
        TextView tvDeadline;
        TextView tvDate;
    }
}
