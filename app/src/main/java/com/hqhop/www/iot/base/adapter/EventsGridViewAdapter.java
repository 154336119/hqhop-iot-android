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
import com.hqhop.www.iot.base.util.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 工作台模块事件的Adapter
 */

public class EventsGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> ids;

    private List<String> names;

    private List<String> contents;

    private List<String> dates;

    private List<String> scenes;

    private LayoutInflater layoutInflater;

    private String eventTypeStr;

    public EventsGridViewAdapter(Context context, List<String> ids, List<String> names, List<String> contents, List<String> dates, List<String> scenes) {
        this.context = context;
        this.ids = ids;
        this.names = names;
        this.contents = contents;
        this.dates = dates;
        this.scenes = scenes;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return ids.size();
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
            convertView = layoutInflater.inflate(R.layout.item_events_grid_view, null);
            viewHolder.tvType = convertView.findViewById(R.id.tv_type_events);
            viewHolder.ivIcon = convertView.findViewById(R.id.iv_icon_events);
            viewHolder.tvStation = convertView.findViewById(R.id.tv_station_events);
            viewHolder.tvContent = convertView.findViewById(R.id.tv_content_events);
            viewHolder.tvDate = convertView.findViewById(R.id.tv_date_events);
            viewHolder.tvTimeInterval = convertView.findViewById(R.id.tv_time_interval_events);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvStation.setText(names.get(position));
        viewHolder.tvContent.setText(contents.get(position));
        viewHolder.tvDate.setText(dates.get(position));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            viewHolder.tvTimeInterval.setText(TimeUtil.getTimeFormatText(context, sdf.parse(dates.get(position))));
            viewHolder.tvTimeInterval.setVisibility(View.VISIBLE);
        } catch (ParseException e) {
            e.printStackTrace();
            viewHolder.tvTimeInterval.setVisibility(View.GONE);
        }
        switch (scenes.get(position)) {
//            case "event_category_alarm":
//                viewHolder.ivIcon.setImageResource(R.drawable.scene_alarm);
//                break;
//            case "event_category_scene":
//                viewHolder.ivIcon.setImageResource(R.drawable.scene_status_change);
//                break;
//            case "event_category_online":
//                viewHolder.ivIcon.setImageResource(R.drawable.scene_online);
//                break;
//            case "event_category_offline":
//                viewHolder.ivIcon.setImageResource(R.drawable.scene_offline);
//                break;
//            case "alarm_clear":// 报警清除
            case "alarm_clear":// 报警清除
                viewHolder.ivIcon.setImageResource(R.drawable.scene_alarm_clear);
                eventTypeStr = context.getString(R.string.alarm_clear);
                break;
//            case "alarm":// 告警
            case "alarm":// 告警
                viewHolder.ivIcon.setImageResource(R.drawable.scene_alarm);
                eventTypeStr = context.getString(R.string.alarm);
                break;
            case "scene":// 场景切换
                viewHolder.ivIcon.setImageResource(R.drawable.scene_status_change);
                eventTypeStr = context.getString(R.string.scene_change);
                break;
//            case "online":// 上线
            case "online":// 上线
                viewHolder.ivIcon.setImageResource(R.drawable.scene_online);
                eventTypeStr = context.getString(R.string.online);
                break;
//            case "offline":// 离线
            case "offline":// 离线
                viewHolder.ivIcon.setImageResource(R.drawable.scene_offline);
                eventTypeStr = context.getString(R.string.offline);
                break;
            default:
                viewHolder.ivIcon.setImageResource(R.drawable.scene_status_change);
                break;
        }
        if (!TextUtils.isEmpty(eventTypeStr)) {
//            viewHolder.tvStation.append(" [" + eventTypeStr + "]");
            viewHolder.tvType.setText(eventTypeStr);
        }

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
        ImageView ivIcon;
        TextView tvType;
        TextView tvStation;
        TextView tvContent;
        TextView tvDate;
        TextView tvTimeInterval;
    }
}
