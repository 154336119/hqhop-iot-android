package com.hqhop.www.iot.base.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqhop.www.iot.R;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * 类型站点主页中站点列表信息的Adapter
 * Created by allen on 2017/7/8.
 */

public class StationListGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> stations = new ArrayList<>();

    private List<Integer> statuses = new ArrayList<>();

    private List<String> addresses = new ArrayList<>();

    private List<String> sales = new ArrayList<>();

    private List<Integer> alerts = new ArrayList<>();

    private List<String> scenes = new ArrayList<>();

    private List<String> types = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public StationListGridViewAdapter(Context context, List<String> stations, List<Integer> statuses, List<String> addresses, List<String> sales, List<Integer> alerts, List<String> scenes, List<String> types) {
        this.context = context;
        this.stations = stations;
        this.statuses = statuses;
        this.addresses = addresses;
        this.sales = sales;
        this.alerts = alerts;
        this.scenes = scenes;
        this.types = types;
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
            convertView = layoutInflater.inflate(R.layout.item_station_list_grid_view, null);
            viewHolder.ivStation = convertView.findViewById(R.id.iv_icon_station_list);
            viewHolder.ivAlarm = convertView.findViewById(R.id.iv_alarm_station_list);
            viewHolder.tvStation = convertView.findViewById(R.id.tv_station_station_list);
            viewHolder.tvStatus = convertView.findViewById(R.id.tv_status_station_list);
            viewHolder.ivStatus = convertView.findViewById(R.id.iv_status_station_list);
            viewHolder.tvType = convertView.findViewById(R.id.tv_type_station_list);
            viewHolder.tvScene = convertView.findViewById(R.id.tv_scene_station_list);
            viewHolder.badgeView = new QBadgeView(context);
            viewHolder.badgeView.setOnDragStateChangedListener((dragState, badge, targetView) -> {

            });
            viewHolder.badgeView.bindTarget(viewHolder.ivStation);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        switch (types.get(position).toLowerCase()) {
            case "gas":// 气化站
                viewHolder.ivStation.setImageResource(R.drawable.avatar_gas);
                // 站点类型
                viewHolder.tvType.setText(R.string.gasify_station);
                break;
            case "oil":// 加油站
                viewHolder.ivStation.setImageResource(R.drawable.avatar_oil);
                viewHolder.tvType.setText(R.string.gasonline_station);
                break;
            case "cng":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_cng);
                viewHolder.tvType.setText(R.string.cng_station);
                break;
            case "lng":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_lng);
                viewHolder.tvType.setText(R.string.lng_station);
                break;
            case "l-cng":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_lcng);
                viewHolder.tvType.setText(R.string.lcng_station);
                break;
            case "charging":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_charging);
                viewHolder.tvType.setText(R.string.charging_station);
                break;
            case "factory":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_factory);
                viewHolder.tvType.setText(R.string.factory);
                break;
            case "ship":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_ship);
                viewHolder.tvType.setText(R.string.ship);
                break;
            case "h2":
                viewHolder.ivStation.setImageResource(R.drawable.avatar_ship);
                viewHolder.tvType.setText(R.string.hydrogen);
                break;
            default:
                viewHolder.ivStation.setImageResource(R.mipmap.ic_launcher_round);
                viewHolder.tvType.setText(R.string.unknown);
                break;
        }

        viewHolder.badgeView.setGravityOffset(-1, -2, true).bindTarget(viewHolder.ivStation).setBadgeNumber(alerts.get(position));
        viewHolder.badgeView.setBadgeNumber(alerts.get(position));

        viewHolder.tvStation.setText(stations.get(position));
        if (statuses.get(position) == 1) {
            viewHolder.tvStatus.setVisibility(View.VISIBLE);
            viewHolder.tvStatus.setBackgroundResource(R.drawable.shape_text_station_status_online);
            viewHolder.tvStatus.setText(context.getString(R.string.normal));
        } else if (statuses.get(position) == 2) {
            viewHolder.tvStatus.setVisibility(View.VISIBLE);
            viewHolder.tvStatus.setBackgroundResource(R.drawable.shape_text_station_status_offline);
            viewHolder.tvStatus.setText(context.getString(R.string.offline));
        } else if (statuses.get(position) == 3) {
            viewHolder.tvStatus.setVisibility(View.VISIBLE);
            viewHolder.tvStatus.setBackgroundResource(R.drawable.shape_text_station_status_alarm);
            viewHolder.tvStatus.setText(context.getString(R.string.alarm));
        } else {
            viewHolder.tvStatus.setVisibility(View.GONE);
        }
        // 场景
        if (!TextUtils.isEmpty(scenes.get(position))) {
            viewHolder.tvScene.setText(scenes.get(position));
            viewHolder.tvScene.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private final class ViewHolder {
        ImageView ivStation;
        ImageView ivAlarm;
        TextView tvStation;
        TextView tvStatus;
        ImageView ivStatus;
        TextView tvType;
        TextView tvScene;
        QBadgeView badgeView;
    }
}
