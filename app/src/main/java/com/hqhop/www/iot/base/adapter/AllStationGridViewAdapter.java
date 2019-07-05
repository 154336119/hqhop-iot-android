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

/**
 * 所有站点页面中站点列表信息的Adapter
 * Created by allen on 2017/7/8.
 */

public class AllStationGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> types = new ArrayList<>();

    private List<String> stations = new ArrayList<>();

    private List<String> addresses = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public AllStationGridViewAdapter(Context context, List<String> types, List<String> stations, List<String> addresses) {
        this.context = context;
        this.types = types;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_station_all_station_list_grid_view, null);
            viewHolder.ivStation = (ImageView) convertView.findViewById(R.id.iv_icon_all_station);
            viewHolder.tvStation = (TextView) convertView.findViewById(R.id.tv_station_all_station);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tv_address_all_station);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < stations.size()) {
            switch (types.get(position).toLowerCase()) {
                case "gas":// 气化站
                    viewHolder.ivStation.setImageResource(R.drawable.avatar_gas);
                    break;
                case "oil":// 加油站
                    viewHolder.ivStation.setImageResource(R.drawable.avatar_oil);
                    break;
                case "cng":
                    viewHolder.ivStation.setImageResource(R.drawable.avatar_cng);
                    break;
                case "lng":
                    viewHolder.ivStation.setImageResource(R.drawable.avatar_lng);
                    break;
                case "l-cng":
                    viewHolder.ivStation.setImageResource(R.drawable.avatar_lcng);
                    break;
                case "charging":
                    viewHolder.ivStation.setImageResource(R.drawable.avatar_charging);
                    break;
                case "factory":
                    viewHolder.ivStation.setImageResource(R.drawable.avatar_factory);
                    break;
                case "ship":
                    viewHolder.ivStation.setImageResource(R.drawable.avatar_ship);
                    break;
                case "h2":
                    viewHolder.ivStation.setImageResource(R.drawable.avatar_ship);
                    break;
                default:
                    viewHolder.ivStation.setImageResource(R.mipmap.ic_launcher_round);
                    break;
            }
            viewHolder.tvStation.setText(stations.get(position));
//            viewHolder.tvType.setText(addresses.get(position));
            viewHolder.tvAddress.setText(TextUtils.isEmpty(addresses.get(position)) ? "暂无地址" : addresses.get(position));
        }

        return convertView;
    }

    private final class ViewHolder {
        ImageView ivStation;
        TextView tvStation;
        TextView tvAddress;
    }
}
