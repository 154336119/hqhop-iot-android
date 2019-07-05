package com.hqhop.www.iot.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hqhop.www.iot.R;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * 导航模块总览信息的Adapter
 * ImageView x 1，TextView x 3
 * Created by allen on 2017/7/7.
 */

public class OverviewGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> iconIds = new ArrayList<>();

    private List<String> types = new ArrayList<>();

    private List<String> onlines = new ArrayList<>();

    private List<String> totals = new ArrayList<>();

    private List<Integer> alarms = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public OverviewGridViewAdapter(Context context, List<String> iconIds, List<String> types, List<String> onlines, List<String> totals, List<Integer> alarms) {
        this.context = context;
        this.iconIds = iconIds;
        this.types = types;
        this.onlines = onlines;
        this.totals = totals;
        this.alarms = alarms;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return types.size();
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
            if (types.size() == 2) {
                convertView = layoutInflater.inflate(R.layout.item_overview_grid_view2, null);
            } else {
                convertView = layoutInflater.inflate(R.layout.item_overview_grid_view, null);
            }
            viewHolder.ivStation = convertView.findViewById(R.id.iv_station_overview);
            viewHolder.ivAlarm = convertView.findViewById(R.id.iv_alarm_overview);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title_overview);
            viewHolder.tvOnline = convertView.findViewById(R.id.tv_online_overview);
            viewHolder.tvTotal = convertView.findViewById(R.id.tv_total_overview);
            viewHolder.containerData = convertView.findViewById(R.id.container_data_overview);
            viewHolder.badgeView = new QBadgeView(context);
            viewHolder.badgeView.setOnDragStateChangedListener((dragState, badge, targetView) -> {

            });
            viewHolder.badgeView.bindTarget(viewHolder.ivStation);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.badgeView.setGravityOffset(-2, -3, true).bindTarget(viewHolder.ivStation).setBadgeNumber(alarms.get(position));

//        if (alarms.get(position) > 0) {
//            viewHolder.ivAlarm.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.ivAlarm.setVisibility(View.GONE);
//        }

        switch (types.get(position).toLowerCase()) {
            case "gas":// 气化站
//                viewHolder.ivStation.setImageResource(R.drawable.avatar_gas);
                viewHolder.ivStation.setImageResource(R.drawable.avatar_gas_type_workbench);
                viewHolder.tvTitle.setText(context.getString(R.string.gasify_station));
                break;
            case "oil":// 加油站
                viewHolder.ivStation.setImageResource(R.drawable.avatar_oil);
                viewHolder.tvTitle.setText(context.getString(R.string.gasonline_station));
                break;
            case "cng":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_cng);
//                viewHolder.tvTitle.setText(context.getString(R.string.cng_station));
                viewHolder.tvTitle.setText(context.getString(R.string.gas_station));
                break;
            case "lng":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_lng);
//                viewHolder.tvTitle.setText(context.getString(R.string.lng_station));
                viewHolder.tvTitle.setText(context.getString(R.string.gas_station));
                break;
            case "l-cng":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_lcng);
//                viewHolder.tvTitle.setText(context.getString(R.string.lcng_station));
                viewHolder.tvTitle.setText(context.getString(R.string.gas_station));
                break;
            case "charging":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_charging);
                viewHolder.tvTitle.setText(context.getString(R.string.charging_station));
                break;
            case "factory":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_factory);
                viewHolder.tvTitle.setText(context.getString(R.string.factory));
                break;
            case "ship":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_ship);
                viewHolder.tvTitle.setText(context.getString(R.string.ship));
                break;
            case "h2":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_hydrogen);
                viewHolder.tvTitle.setText(context.getString(R.string.hydrogen));
                break;
            default:
                viewHolder.ivStation.setImageResource(R.mipmap.ic_launcher_round);
                viewHolder.tvTitle.setText(context.getString(R.string.other));
                break;
        }
        viewHolder.tvOnline.setText(onlines.get(position));
        viewHolder.tvTotal.setText(totals.get(position));

//        if (position < iconIds.size()) {
//            viewHolder.ivStation.setImageResource(iconIds.get(position));
//        } else {
//            // 显示默认图片
//            viewHolder.ivStation.setImageResource(R.mipmap.ic_launcher_round);
//        }
//        if (position < types.size()) {
//            viewHolder.tvTitle.setText(types.get(position));
//        } else {
//            viewHolder.tvTitle.setText("?????");
//        }
//        if (position < onlines.size() && position < totals.size()) {
//
//            viewHolder.containerData.setVisibility(View.VISIBLE);
//            viewHolder.tvOnline.setText(onlines.get(position));
//            viewHolder.tvTotal.setText(totals.get(position));
//        } else {
//            viewHolder.containerData.setVisibility(View.INVISIBLE);
//        }
        return convertView;
    }

    private final class ViewHolder {
        ImageView ivStation;
        ImageView ivAlarm;
        TextView tvTitle;
        TextView tvOnline;
        TextView tvTotal;
        LinearLayout containerData;
        QBadgeView badgeView;
    }
}
