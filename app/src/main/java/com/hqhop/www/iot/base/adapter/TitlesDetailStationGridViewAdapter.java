package com.hqhop.www.iot.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.view.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航模块总览信息的Adapter
 * ImageView x 1，TextView x 3
 * Created by allen on 2017/7/7.
 */

public class TitlesDetailStationGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<Integer> iconIds = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

    private List<String> codes = new ArrayList<>();

    private LayoutInflater layoutInflater;

    private HorizontalListView horizontalListView;

    public int selectedIndex = 0;

    public TitlesDetailStationGridViewAdapter(Context context, List<Integer> iconIds, List<String> titles, List<String> codes, HorizontalListView horizontalListView) {
        this.context = context;
        this.iconIds = iconIds;
        this.titles = titles;
        this.codes = codes;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
        this.horizontalListView = horizontalListView;
    }

    @Override
    public int getCount() {
        return codes.size();
    }

    @Override
    public View getItem(int position) {
//        return position;
        return this.getView(position, null, horizontalListView);
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
            convertView = layoutInflater.inflate(R.layout.item_title_detail_station, null);
            viewHolder.ivIcon = convertView.findViewById(R.id.iv_icon_detail_station);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title_detail_station);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        ImageView ivIcon = convertView.findViewById(R.id.iv_icon_detail_station);
//        TextView tvTitle = convertView.findViewById(R.id.tv_title_detail_station);

        viewHolder.ivIcon.setBackgroundResource(iconIds.get(position));
//        ivIcon.setBackgroundResource(iconIds.get(position));
//        switch (titles.get(position)) {
        switch (codes.get(position)) {
            case "1001":
                viewHolder.tvTitle.setText(context.getString(R.string.station_equipment_monitor));
                break;
            case "1002":
                viewHolder.tvTitle.setText(context.getString(R.string.station_alarm));
                break;
            case "1003":
                viewHolder.tvTitle.setText(context.getString(R.string.station_statistics));
                break;
            case "1004":
                viewHolder.tvTitle.setText(context.getString(R.string.station_technology));
                break;
            case "1005":
                viewHolder.tvTitle.setText(context.getString(R.string.station_maintenance));
                break;
            case "1006":
                viewHolder.tvTitle.setText(context.getString(R.string.station_scheduling));
                break;
            case "1007":
                viewHolder.tvTitle.setText(context.getString(R.string.station_inspection));
                break;
            case "1008":
                viewHolder.tvTitle.setText(context.getString(R.string.station_video));
                break;
            case "1010":
                viewHolder.tvTitle.setText(context.getString(R.string.station_gateway));
                break;
            case "1009":
                viewHolder.tvTitle.setText(context.getString(R.string.station_more));
                break;
            default:
                viewHolder.tvTitle.setText(context.getString(R.string.unknown));
                break;
        }
//        }
        if (selectedIndex == position) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.black_transparency_10p));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }

    private final class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
    }
}
