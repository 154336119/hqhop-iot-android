package com.hqhop.www.iot.base.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hqhop.www.iot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注模块可折叠布局子项的Adapter
 * Created by allen on 2017/7/8.
 */

public class FollowExpandableChildGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> titles = new ArrayList<>();

    private List<String> values = new ArrayList<>();

    private List<String> units = new ArrayList<>();

    private List<String> alarms = new ArrayList<>();

//    private List<String> equipmentIds = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public FollowExpandableChildGridViewAdapter(Context context, List<String> titles, List<String> values, List<String> units, List<String> alarms) {
        this.context = context;
        this.titles = titles;
        this.values = values;
        this.units = units;
        this.alarms = alarms;
//        this.equipmentIds = equipmentIds;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return titles.size();
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
            convertView = layoutInflater.inflate(R.layout.item_follow_expandable_child_grid_view, null);
            viewHolder.tvStation = convertView.findViewById(R.id.tv_title_expandable_child_follow);
            viewHolder.tvValue = convertView.findViewById(R.id.tv_value_expandable_child_follow);
            viewHolder.tvUnit = convertView.findViewById(R.id.tv_unit_expandable_child_follow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        if (position < titles.size()) {
//            viewHolder.tvStation.setText(titles.get(position));
//            viewHolder.tvValue.setText(values.get(position));
//            viewHolder.tvUnit.setText(units.get(position));
//        }

        viewHolder.tvStation.setText(titles.get(position));
        float tempValue = 0.0F;
        try {
            tempValue = Float.parseFloat(values.get(position).trim());
            viewHolder.tvValue.setText(String.valueOf(tempValue));
            viewHolder.tvUnit.setText(units.get(position).trim());
            if (TextUtils.isEmpty(alarms.get(position))) {
                // 正常
                viewHolder.tvValue.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                viewHolder.tvUnit.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (alarms.get(position).equals("1")) {
                viewHolder.tvValue.setTextColor(context.getResources().getColor(R.color.colorTextOverviewOnline));
                viewHolder.tvUnit.setTextColor(context.getResources().getColor(R.color.colorTextOverviewOnline));
                viewHolder.tvUnit.append("↓");
            } else if (alarms.get(position).equals("2")) {
                viewHolder.tvValue.setTextColor(context.getResources().getColor(R.color.red));
                viewHolder.tvUnit.setTextColor(context.getResources().getColor(R.color.red));
                viewHolder.tvUnit.append("↑");
            }
        } catch (NumberFormatException e) {
//            viewHolder.tvValue.setText(values.get(position));
            viewHolder.tvValue.setText("-");
            e.printStackTrace();
        }

        return convertView;
    }

    private final class ViewHolder {
        TextView tvStation;
        TextView tvValue;
        TextView tvUnit;
    }
}
