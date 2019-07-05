package com.hqhop.www.iot.base.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.follow.module.ModuleSettingActivity;
import com.hqhop.www.iot.activities.main.follow.station.DetailItemActivity;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.api.follow.ModuleService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.http.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注模块可折叠布局的Adapter
 * Created by allen on 2017/7/27.
 */

public class FollowStationListViewAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<String> ids;

    private List<String> stations;

    private List<String> statuses;

    private List<ArrayList<String>> titles;

    private List<ArrayList<String>> values;

    private List<ArrayList<String>> units;

    private List<ArrayList<String>> alarms;

    private List<ArrayList<String>> parameterNames;

    private List<ArrayList<String>> parameterIds;

    private List<ArrayList<String>> equipmentIds;

    private List<Integer> stationIds;

    private GridView gridView;

    private LayoutInflater layoutInflater;

    private int clickedItemIndex = -1;

    private ModuleService moduleService;

    public FollowStationListViewAdapter(Context context, List<String> ids, List<String> stations, List<String> statuses, List<ArrayList<String>> titles, List<ArrayList<String>> values, List<ArrayList<String>> units, List<ArrayList<String>> alarms, List<ArrayList<String>> parameterNames, List<ArrayList<String>> parameterIds, List<ArrayList<String>> equipmentIds, List<Integer> stationIds) {
        this.context = context;
        this.ids = ids;
        this.stations = stations;
        this.statuses = statuses;
        this.titles = titles;
        this.values = values;
        this.units = units;
        this.parameterNames = parameterNames;
        this.parameterIds = parameterIds;
        this.alarms = alarms;
        this.equipmentIds = equipmentIds;
        this.stationIds = stationIds;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
        if (moduleService == null) {
            moduleService = RetrofitManager.getInstance(context).createService(ModuleService.class);
        }
    }

    @Override
    public int getGroupCount() {
        return stations.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return stations.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return titles.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_expandable_parent_follow, null);
        }
        TextView tvStation = convertView.findViewById(R.id.tv_station_expandable_parent_follow);
//        ImageView ivStatus = (ImageView) convertView.findViewById(R.id.iv_status_expandable_parent_follow);
        TextView tvStatus = convertView.findViewById(R.id.tv_status_expandable_parent_follow);
        ImageView ivArrow = convertView.findViewById(R.id.iv_arrow_expandable_parent_follow);
        if (isExpanded && clickedItemIndex == groupPosition) {
            // 展开
            unfoldRotationAnimation(ivArrow);
        } else if (!isExpanded && clickedItemIndex == groupPosition) {
            // 折叠
            foldRotationAnimation(ivArrow);
        }
//        for (int i = 0; i < alarms.get(groupPosition).size(); i++) {
        if (alarms.size() > 0 && alarms.get(groupPosition).size() > 0) {
            for (String alarm : alarms.get(groupPosition)) {
                if (TextUtils.isEmpty(alarm)) {
                    tvStatus.setText(context.getString(R.string.normal));
                    tvStatus.setBackgroundResource(R.drawable.shape_follow_normal);
                } else {
                    tvStatus.setText(context.getString(R.string.unnormal));
                    tvStatus.setBackgroundResource(R.drawable.shape_follow_exception);
                    break;
                }
            }
        }
//        if (statuses.get(groupPosition).equals("正常")) {
//            ivStatus.setImageResource(R.drawable.ic_stamp);
//        } else if (statuses.get(groupPosition).equals("高危")) {
//            ivStatus.setImageResource(R.drawable.ic_alert_message_detail_station);
//        }
        tvStation.setText(stations.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_expandable_child_follow, null);
        }
        TextView tvMore = convertView.findViewById(R.id.tv_more_expandable_child_follow);
        TextView tvSetting = convertView.findViewById(R.id.tv_setting_expandable_child_follow);
//        TextView tvRemoveFollow = convertView.findViewById(R.id.tv_remove_follow_expandable_child_follow);
        gridView = convertView.findViewById(R.id.gridview_expandable_child_follow);
        FollowExpandableChildGridViewAdapter adapter = new FollowExpandableChildGridViewAdapter(context, titles.get(groupPosition), values.get(groupPosition), units.get(groupPosition), alarms.get(groupPosition));
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent oneIntent = new Intent(context, DetailItemActivity.class);
                oneIntent.putExtra("type", "follow");
//                oneIntent.putExtra("index", position);// 传递position，再DetailItemActivity页面显示该索引处的数据
                oneIntent.putExtra("station", stations.get(groupPosition));
                oneIntent.putExtra("title", titles.get(groupPosition).get(position));
                oneIntent.putExtra("parameterName", parameterNames.get(groupPosition).get(position));
                oneIntent.putExtra("parameterId", parameterIds.get(groupPosition).get(position));
                oneIntent.putExtra("equipmentId", equipmentIds.get(groupPosition).get(position));
                oneIntent.putExtra("unit", units.get(groupPosition).get(position));
                oneIntent.putExtra("stationId", stationIds.get(groupPosition));
                context.startActivity(oneIntent);
            }
        });
        tvMore.setOnClickListener(view -> {
            Intent detailIntent = new Intent(context, DetailStationActivity.class);
            detailIntent.putExtra("type", 2);
            detailIntent.putExtra("id", stationIds.get(groupPosition).toString());
            context.startActivity(detailIntent);
//            Dialog dialog = CommonUtils.createLoadingDialog(context);
//            CommonUtils.showProgressDialogWithMessage(context, dialog, null);
        });
        tvSetting.setOnClickListener(view -> {
            if (ids.isEmpty()) {
                return;
            }
            Intent moduleSettingIntent = new Intent(context, ModuleSettingActivity.class);
            moduleSettingIntent.putExtra("id", ids.get(groupPosition));
            context.startActivity(moduleSettingIntent);
        });
//        tvRemoveFollow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                moduleService.setMudules(App.userid, stationIds.get(groupPosition).toString(), "delete")
//                        .subscribeOn(Schedulers.io())
//                        .subscribeOn(AndroidSchedulers.mainThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<ModuleBean>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
////                        CommonUtils.showToast("网络错误");
////                        finishedFetchingData = true;
////                        finishRefresh();
////                                finish();
//                                FollowFragment.getInstance(context).refresh();
//                            }
//
//                            @Override
//                            public void onNext(ModuleBean bean) {
//                                FollowFragment.getInstance(context).refresh();
////                        finishedFetchingData = true;
////                        finishRefresh();
//////                        alertLevels.clear();
//////                        alertStations.clear();
//////                        alertReasons.clear();
//////                        alertDates.clear();
////                        moduleBean = bean;
////                        detailModuleFragment.setData();
////                        quickModuleFragment.setData();
////                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
////                            setAlertData();
////                        }
//                            }
//                        });
////        CommonUtils.showToast("详细模块保存");
//            }
//        });

        return convertView;
    }

    /**
     * 如果需要监听子项点击事件，则返回true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 折叠时ImageView旋转动画
     */
    private void foldRotationAnimation(View view) {
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(view, "rotation", -180F, 0F);
        rotateAnim.setDuration(Common.DURATION_FOLD_ANIMATION);
        rotateAnim.start();
    }

    /**
     * 展开时ImageView旋转动画
     */
    private void unfoldRotationAnimation(View view) {
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(view, "rotation", 0F, -180F);
        rotateAnim.setDuration(Common.DURATION_FOLD_ANIMATION);
        rotateAnim.start();
    }

    public void setSelected(int position) {
        clickedItemIndex = position;
    }
}
