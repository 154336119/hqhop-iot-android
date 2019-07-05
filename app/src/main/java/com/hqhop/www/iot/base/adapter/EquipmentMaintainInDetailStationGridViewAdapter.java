package com.hqhop.www.iot.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqhop.www.iot.R;

import java.util.List;

/**
 * 站点详情-设备监控的Adapter
 * Created by allen on 2017/7/7.
 */

public class EquipmentMaintainInDetailStationGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> levels;

    private List<String> deadlines;

    private List<String> dates;

    private LayoutInflater layoutInflater;

    public EquipmentMaintainInDetailStationGridViewAdapter(Context context, List<String> levels, List<String> deadlines, List<String> dates) {
        this.context = context;
        this.levels = levels;
        this.deadlines = deadlines;
        this.dates = dates;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return dates.size();
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
            convertView = layoutInflater.inflate(R.layout.item_equipment_maintain_detail_station, null);
            viewHolder.ivLevel = (ImageView) convertView.findViewById(R.id.iv_level_equipment_maintain);
            viewHolder.tvDeadline = (TextView) convertView.findViewById(R.id.tv_deadline_equipment_maintain);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date_equipment_maintain);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        if (levels.get(position).equals("1")) {
//
//        } else if (levels.get(position).equals("2")) {
//
//        }
        viewHolder.tvDeadline.setText(deadlines.get(position));
        viewHolder.tvDate.setText(dates.get(position));

        return convertView;
    }

    private final class ViewHolder {
        /**
         * 左上角图标
         */
        ImageView ivLevel;
        TextView tvDeadline;
        TextView tvDate;
    }
}
