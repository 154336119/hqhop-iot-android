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
 * 导航模块经营概览的Adapter
 * Created by allen on 2017/7/8.
 */

public class BusinessGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> titles = new ArrayList<>();

    private List<String> values = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public BusinessGridViewAdapter(Context context, List<String> titles, List<String> values) {
        this.context = context;
        this.titles = titles;
        this.values = values;
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
            convertView = layoutInflater.inflate(R.layout.item_business_grid_view, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title_business);
            viewHolder.tvValue = (TextView) convertView.findViewById(R.id.tv_value_business);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < titles.size()) {
            viewHolder.tvTitle.setText(titles.get(position));
            viewHolder.tvValue.setText(values.get(position));
        }

        return convertView;
    }

    private final class ViewHolder {
        TextView tvTitle;
        TextView tvValue;
    }
}
