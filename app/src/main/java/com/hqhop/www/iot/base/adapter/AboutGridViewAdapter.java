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

public class AboutGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> titles = new ArrayList<>();

    private List<String> contents = new ArrayList<>();

    private List<String> types = new ArrayList<>();

    private List<Boolean> isShowMores = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public AboutGridViewAdapter(Context context, List<String> titles, List<String> contents, List<String> types, List<Boolean> isShowMores) {
        this.context = context;
        this.titles = titles;
        this.contents = contents;
        this.types = types;
        this.isShowMores = isShowMores;
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
            convertView = layoutInflater.inflate(R.layout.item_about_grid_view, null);
            viewHolder.ivIcon = convertView.findViewById(R.id.iv_icon_about);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title_about);
            viewHolder.tvContent = convertView.findViewById(R.id.tv_content_about);
            viewHolder.ivMore = convertView.findViewById(R.id.iv_more_about);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(titles.get(position));
        if (TextUtils.isEmpty(contents.get(position))) {
            viewHolder.tvContent.setVisibility(View.GONE);
        } else {
            viewHolder.tvContent.setVisibility(View.VISIBLE);
            viewHolder.tvContent.setText(contents.get(position));
        }
        if (!isShowMores.get(position)) {
            viewHolder.ivMore.setVisibility(View.GONE);
        }
        switch (types.get(position)) {
            case "tel":
                viewHolder.ivIcon.setImageResource(R.drawable.about_phone);
                break;
            case "email":
                viewHolder.ivIcon.setImageResource(R.drawable.about_email);
                break;
            case "app":
                viewHolder.ivIcon.setImageResource(R.drawable.about_version);
                break;
            case "scan":
                viewHolder.ivIcon.setImageResource(R.drawable.about_scan);
                break;
            case "feedback":
                viewHolder.ivIcon.setImageResource(R.drawable.about_company);
                break;
            case "profile":
                viewHolder.ivIcon.setImageResource(R.drawable.icon_profile);
                break;
            default:
                break;
        }

        return convertView;
    }

    private final class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvContent;
        ImageView ivMore;
    }
}
