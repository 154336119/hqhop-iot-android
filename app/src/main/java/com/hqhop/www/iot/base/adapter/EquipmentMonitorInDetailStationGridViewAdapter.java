package com.hqhop.www.iot.base.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.follow.station.DetailItemActivity;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.base.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 站点详情-设备监控的Adapter
 * Created by allen on 2017/7/7.
 */

public class EquipmentMonitorInDetailStationGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<Boolean> isNormals;

    private List<String> imgUrls;

    private List<String> types;

    private List<String> statuses;

    private List<ArrayList<String>> titles;

    private List<List<String>> values;

    private List<ArrayList<String>> units;

    private List<List<String>> alarms;

    private List<String> equipmentIds;

    private List<ArrayList<String>> parameterIds;

    private List<ArrayList<String>> equipmentTypes;

    private List<ArrayList<String>> equipmentConfigTypes;

    private List<ArrayList<String>> equipmentBizTypes;

    private List<ArrayList<String>> upperLimits;

    private List<ArrayList<String>> lowerLimits;

    private List<Integer> alertIndexes;

    private LayoutInflater layoutInflater;

    public EquipmentMonitorInDetailStationGridViewAdapter(Context context, List<Boolean> isNormals, List<String> imgUrls, List<String> types, List<String> statuses,
                                                          List<ArrayList<String>> titles, List<List<String>> values, List<ArrayList<String>> units, List<List<String>> alarms,
                                                          List<String> equipmentIds, List<ArrayList<String>> parameterIds, List<ArrayList<String>> equipmentTypes, List<ArrayList<String>> equipmentConfigTypes, List<ArrayList<String>> equipmentBizTypes, List<Integer> alertIndexes,
                                                          List<ArrayList<String>> upperLimits, List<ArrayList<String>> lowerLimits) {
        this.context = context;
        this.isNormals = isNormals;
        this.imgUrls = imgUrls;
        this.types = types;
        this.statuses = statuses;
        this.titles = titles;
        this.values = values;
        this.units = units;
        this.alarms = alarms;
        this.equipmentIds = equipmentIds;
        this.parameterIds = parameterIds;
        this.equipmentTypes = equipmentTypes;
        this.equipmentConfigTypes = equipmentConfigTypes;
        this.equipmentBizTypes = equipmentBizTypes;
        this.upperLimits = upperLimits;
        this.lowerLimits = lowerLimits;
        this.alertIndexes = alertIndexes;
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
            convertView = layoutInflater.inflate(R.layout.item_equipment_monitor_detail_station, null);
            viewHolder.containerType = convertView.findViewById(R.id.container_type_equipment_motinor);
            viewHolder.containerData0 = convertView.findViewById(R.id.container_data0_equipment_motinor);
            viewHolder.containerData1 = convertView.findViewById(R.id.container_data1_equipment_motinor);
            viewHolder.containerData2 = convertView.findViewById(R.id.container_data2_equipment_motinor);
            viewHolder.containerData3 = convertView.findViewById(R.id.container_data3_equipment_motinor);
            viewHolder.ivStatus = convertView.findViewById(R.id.iv_status_equipment_monitor);
            viewHolder.ivIcon = convertView.findViewById(R.id.iv_icon_equipment_monitor);
            viewHolder.tvIcon = convertView.findViewById(R.id.tv_icon_equipment_monitor);
            viewHolder.tvStatus = convertView.findViewById(R.id.tv_status_equipment_monitor);
            viewHolder.tvTitle1 = convertView.findViewById(R.id.tv_title_1_equipment_monitor);
            viewHolder.tvValue1 = convertView.findViewById(R.id.tv_value_1_equipment_monitor);
            viewHolder.tvTitle2 = convertView.findViewById(R.id.tv_title_2_equipment_monitor);
            viewHolder.tvValue2 = convertView.findViewById(R.id.tv_value_2_equipment_monitor);
            viewHolder.tvTitle3 = convertView.findViewById(R.id.tv_title_3_equipment_monitor);
            viewHolder.tvValue3 = convertView.findViewById(R.id.tv_value_3_equipment_monitor);
            viewHolder.tvTitle4 = convertView.findViewById(R.id.tv_title_4_equipment_monitor);
            viewHolder.tvValue4 = convertView.findViewById(R.id.tv_value_4_equipment_monitor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (isNormals.size() > position && !isNormals.get(position)) {
            viewHolder.ivStatus.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivStatus.setVisibility(View.GONE);
        }
        viewHolder.tvIcon.setText(types.get(position));
        if (statuses.size() > position) {
            viewHolder.tvStatus.setText(statuses.get(position));
        }
        Glide.with(context).load(imgUrls.get(position)).into(viewHolder.ivIcon);
        viewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
        if (titles.get(position).size() > 0) {
            viewHolder.tvTitle1.setText(titles.get(position).get(0));
        }

        if (position != 0) {
            // 不是第一项，调整ivStatus的marginTop值
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(viewHolder.ivStatus.getLayoutParams());
            lp.setMargins(0, 15, 0, 0);
            viewHolder.ivStatus.setLayoutParams(lp);
        }

        if (values.get(position).size() > 0) {
            viewHolder.containerType.setClickable(true);
            viewHolder.containerType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (!TextUtils.isEmpty(equipmentTypes.get(position).get(0)) && !equipmentTypes.get(position).get(0).equals("switcher")) {
                    if (!equipmentConfigTypes.get(position).get(0).equals("switcher") && !equipmentBizTypes.get(position).get(0).contains("status")) {
                        Intent intent = new Intent(context, DetailItemActivity.class);
                        intent.putExtra("type", "monitor");
                        intent.putExtra("index", 0);
                        intent.putExtra("station", DetailStationActivity.stationName);
                        intent.putExtra("title", types.get(position));
                        intent.putStringArrayListExtra("parameterNames", titles.get(position));
                        intent.putStringArrayListExtra("parameterIds", parameterIds.get(position));
                        intent.putStringArrayListExtra("parameterTypes", equipmentTypes.get(position));
                        intent.putStringArrayListExtra("upperLimits", upperLimits.get(position));
                        intent.putStringArrayListExtra("lowerLimits", lowerLimits.get(position));
                        intent.putStringArrayListExtra("units", units.get(position));
                        intent.putExtra("equipmentId", equipmentIds.get(position));
                        context.startActivity(intent);
                    } else {
                        CommonUtils.showToast("该设备暂无详细信息");
                    }
                }
            });
            viewHolder.containerData0.setClickable(true);
            // 第1项容器点击事件
            viewHolder.containerData0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (!TextUtils.isEmpty(equipmentTypes.get(position).get(0)) && !equipmentTypes.get(position).get(0).equals("switcher")) {
                    if (!equipmentConfigTypes.get(position).get(0).equals("switcher") && !equipmentBizTypes.get(position).get(0).contains("status")) {
                        Intent intent = new Intent(context, DetailItemActivity.class);
                        intent.putExtra("type", "monitor");
                        intent.putExtra("index", 0);
                        intent.putExtra("station", DetailStationActivity.stationName);
                        intent.putExtra("title", types.get(position));
                        intent.putStringArrayListExtra("parameterNames", titles.get(position));
                        intent.putStringArrayListExtra("parameterIds", parameterIds.get(position));
                        intent.putStringArrayListExtra("parameterTypes", equipmentTypes.get(position));
                        intent.putStringArrayListExtra("upperLimits", upperLimits.get(position));
                        intent.putStringArrayListExtra("lowerLimits", lowerLimits.get(position));
                        intent.putStringArrayListExtra("units", units.get(position));
                        intent.putExtra("equipmentId", equipmentIds.get(position));
                        context.startActivity(intent);
                    } else {
                        CommonUtils.showToast("该设备暂无详细信息");
                    }
                }
            });
            float f0 = 0;
            try {
                f0 = Float.parseFloat(values.get(position).get(0));
                viewHolder.tvValue1.setText(values.get(position).get(0) + units.get(position).get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                viewHolder.tvValue1.setText(values.get(position).get(0));
            }
            if (equipmentConfigTypes.get(position).get(0).equals("switcher") && values.get(position).get(0).equals("0")) {
                viewHolder.tvValue1.setText("关");
                viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentConfigTypes.get(position).get(0).equals("switcher") && values.get(position).get(0).equals("1")) {
                viewHolder.tvValue1.setText("开");
                viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentConfigTypes.get(position).get(0).contains("status") && values.get(position).get(0).equals("0")) {
                viewHolder.tvValue1.setText("正常");
                viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentConfigTypes.get(position).get(0).contains("status") && values.get(position).get(0).equals("1")) {
                viewHolder.tvValue1.setText("故障");
                viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(0).equals("alarm") && values.get(position).get(0).equals("0")) {
                viewHolder.tvValue1.setText("正常");
                viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(0).equals("alarm") && values.get(position).get(0).equals("1")) {
                viewHolder.tvValue1.setText("报警");
                viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(0).equals("error") && values.get(position).get(0).equals("0")) {
                viewHolder.tvValue1.setText("正常");
                viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(0).equals("error") && values.get(position).get(0).equals("1")) {
                viewHolder.tvValue1.setText("数据错误");
                viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(0).equals("exception") && values.get(position).get(0).equals("0")) {
                viewHolder.tvValue1.setText("正常");
                viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(0).equals("exception") && values.get(position).get(0).equals("1")) {
                viewHolder.tvValue1.setText("异常");
                viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                if (alarms.get(position).size() > 0) {
                    if (alarms.get(position).get(0).equals("0")) {// 正常
                        viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.black));
                    } else if (alarms.get(position).get(0).equals("1")) {// 偏低
                        viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.outsideBlue));
                        viewHolder.tvValue1.append("↓");
                        viewHolder.ivStatus.setVisibility(View.VISIBLE);
                    } else if (alarms.get(position).get(0).equals("2")) {// 偏高
                        viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.red));
                        viewHolder.tvValue1.append("↑");
                        viewHolder.ivStatus.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.black));
                    }
                }
            }
        } else {
            viewHolder.containerData0.setClickable(false);
        }

        if (titles.get(position).size() > 1) {
            viewHolder.tvTitle2.setText(titles.get(position).get(1));
            viewHolder.tvValue2.setText(values.get(position).get(1));
            viewHolder.containerData1.setClickable(true);
            // 第2项容器点击事件
            viewHolder.containerData1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (!TextUtils.isEmpty(equipmentTypes.get(position).get(1)) && !equipmentTypes.get(position).get(1).equals("switcher")) {
                    if (!equipmentConfigTypes.get(position).get(1).equals("switcher") && !equipmentBizTypes.get(position).get(1).contains("status")) {
                        Intent intent = new Intent(context, DetailItemActivity.class);
                        intent.putExtra("type", "monitor");
                        intent.putExtra("index", 1);
                        intent.putExtra("station", DetailStationActivity.stationName);
                        intent.putExtra("title", types.get(position));
                        intent.putStringArrayListExtra("parameterNames", titles.get(position));
                        intent.putStringArrayListExtra("parameterIds", parameterIds.get(position));
                        intent.putStringArrayListExtra("parameterTypes", equipmentTypes.get(position));
                        intent.putStringArrayListExtra("upperLimits", upperLimits.get(position));
                        intent.putStringArrayListExtra("lowerLimits", lowerLimits.get(position));
                        intent.putStringArrayListExtra("units", units.get(position));
                        intent.putExtra("equipmentId", equipmentIds.get(position));
                        context.startActivity(intent);
                    } else {
                        CommonUtils.showToast("该设备暂无详细信息");
                    }
                }
            });

            float f1 = 0;
            try {
                f1 = Float.parseFloat(values.get(position).get(1));
                viewHolder.tvValue2.setText(values.get(position).get(1) + units.get(position).get(1));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                viewHolder.tvValue2.setText(values.get(position).get(1));
            }
            if (equipmentConfigTypes.get(position).get(1).equals("switcher") && values.get(position).get(1).equals("0")) {
                viewHolder.tvValue2.setText("关");
                viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentConfigTypes.get(position).get(1).equals("switcher") && values.get(position).get(1).equals("1")) {
                viewHolder.tvValue2.setText("开");
                viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentConfigTypes.get(position).get(1).contains("status") && values.get(position).get(1).equals("0")) {
                viewHolder.tvValue2.setText("正常");
                viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentConfigTypes.get(position).get(1).contains("status") && values.get(position).get(1).equals("1")) {
                viewHolder.tvValue2.setText("故障");
                viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(1).equals("alarm") && values.get(position).get(1).equals("0")) {
                viewHolder.tvValue2.setText("正常");
                viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(1).equals("alarm") && values.get(position).get(1).equals("1")) {
                viewHolder.tvValue2.setText("报警");
                viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(1).equals("error") && values.get(position).get(1).equals("0")) {
                viewHolder.tvValue2.setText("正常");
                viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(1).equals("error") && values.get(position).get(1).equals("1")) {
                viewHolder.tvValue2.setText("数据错误");
                viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(1).equals("exception") && values.get(position).get(1).equals("0")) {
                viewHolder.tvValue2.setText("正常");
                viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(1).equals("exception") && values.get(position).get(1).equals("1")) {
                viewHolder.tvValue2.setText("异常");
                viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                if (alarms.get(position).get(1).equals("0")) {// 正常
                    viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.black));
                } else if (alarms.get(position).get(1).equals("1")) {// 偏低
                    viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.outsideBlue));
                    viewHolder.ivStatus.setVisibility(View.VISIBLE);
                    viewHolder.tvValue2.append("↓");
                } else if (alarms.get(position).get(1).equals("2")) {// 偏高
                    viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.red));
                    viewHolder.tvValue2.append("↑");
                    viewHolder.ivStatus.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tvValue2.setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        } else {
            viewHolder.containerData1.setClickable(false);
        }

        if (titles.get(position).size() > 2) {
            viewHolder.tvTitle3.setText(titles.get(position).get(2));
            viewHolder.tvValue3.setText(values.get(position).get(2));
            viewHolder.containerData2.setClickable(true);
            // 第3项容器点击事件
            viewHolder.containerData2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (!TextUtils.isEmpty(equipmentTypes.get(position).get(2)) && !equipmentTypes.get(position).get(2).equals("switcher")) {
                    if (!equipmentConfigTypes.get(position).get(2).equals("switcher") && !equipmentBizTypes.get(position).get(2).contains("status")) {
                        Intent intent = new Intent(context, DetailItemActivity.class);
                        intent.putExtra("type", "monitor");
                        intent.putExtra("index", 2);
                        intent.putExtra("station", DetailStationActivity.stationName);
                        intent.putExtra("title", types.get(position));
                        intent.putStringArrayListExtra("parameterNames", titles.get(position));
                        intent.putStringArrayListExtra("parameterIds", parameterIds.get(position));
                        intent.putStringArrayListExtra("parameterTypes", equipmentTypes.get(position));
                        intent.putStringArrayListExtra("upperLimits", upperLimits.get(position));
                        intent.putStringArrayListExtra("lowerLimits", lowerLimits.get(position));
                        intent.putStringArrayListExtra("units", units.get(position));
                        intent.putExtra("equipmentId", equipmentIds.get(position));
                        context.startActivity(intent);
                    } else {
                        CommonUtils.showToast("该设备暂无详细信息");
                    }
                }
            });

            float f2 = 0;
            try {
                f2 = Float.parseFloat(values.get(position).get(2));
                viewHolder.tvValue3.setText(values.get(position).get(2) + units.get(position).get(2));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                viewHolder.tvValue3.setText(values.get(position).get(2));
            }
            if (equipmentConfigTypes.get(position).get(2).equals("switcher") && values.get(position).get(2).equals("0")) {
                viewHolder.tvValue3.setText("关");
                viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentConfigTypes.get(position).get(2).equals("switcher") && values.get(position).get(2).equals("1")) {
                viewHolder.tvValue3.setText("开");
                viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentConfigTypes.get(position).get(2).contains("status") && values.get(position).get(2).equals("0")) {
                viewHolder.tvValue3.setText("正常");
                viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentConfigTypes.get(position).get(2).contains("status") && values.get(position).get(2).equals("1")) {
                viewHolder.tvValue3.setText("故障");
                viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(2).equals("alarm") && values.get(position).get(2).equals("0")) {
                viewHolder.tvValue3.setText("正常");
                viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(2).equals("alarm") && values.get(position).get(2).equals("1")) {
                viewHolder.tvValue3.setText("报警");
                viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(2).equals("error") && values.get(position).get(2).equals("0")) {
                viewHolder.tvValue3.setText("正常");
                viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(2).equals("error") && values.get(position).get(2).equals("1")) {
                viewHolder.tvValue3.setText("数据错误");
                viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(2).equals("exception") && values.get(position).get(2).equals("0")) {
                viewHolder.tvValue3.setText("正常");
                viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(2).equals("exception") && values.get(position).get(2).equals("1")) {
                viewHolder.tvValue3.setText("异常");
                viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                if (alarms.get(position).get(2).equals("0")) {// 正常
                    viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.black));
                } else if (alarms.get(position).get(2).equals("1")) {// 偏低
                    viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.outsideBlue));
                    viewHolder.tvValue3.append("↓");
                    viewHolder.ivStatus.setVisibility(View.VISIBLE);
                } else if (alarms.get(position).get(2).equals("2")) {// 偏高
                    viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.red));
                    viewHolder.tvValue3.append("↑");
                    viewHolder.ivStatus.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tvValue3.setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        } else {
            viewHolder.containerData2.setClickable(false);
        }

        if (titles.get(position).size() > 3) {
            viewHolder.tvTitle4.setText(titles.get(position).get(3));
            viewHolder.tvValue4.setText(values.get(position).get(3));
            viewHolder.containerData3.setClickable(true);
            // 第4项容器点击事件
            viewHolder.containerData3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (!TextUtils.isEmpty(equipmentTypes.get(position).get(3)) && !equipmentTypes.get(position).get(3).equals("switcher")) {
                    if (!equipmentConfigTypes.get(position).get(3).equals("switcher") && !equipmentBizTypes.get(position).get(3).contains("status")) {
                        Intent intent = new Intent(context, DetailItemActivity.class);
                        intent.putExtra("type", "monitor");
                        intent.putExtra("index", 3);
                        intent.putExtra("station", DetailStationActivity.stationName);
                        intent.putExtra("title", types.get(position));
                        intent.putStringArrayListExtra("parameterNames", titles.get(position));
                        intent.putStringArrayListExtra("parameterIds", parameterIds.get(position));
                        intent.putStringArrayListExtra("parameterTypes", equipmentTypes.get(position));
                        intent.putStringArrayListExtra("upperLimits", upperLimits.get(position));
                        intent.putStringArrayListExtra("lowerLimits", lowerLimits.get(position));
                        intent.putStringArrayListExtra("units", units.get(position));
                        intent.putExtra("equipmentId", equipmentIds.get(position));
                        context.startActivity(intent);
                    } else {
                        CommonUtils.showToast("该设备暂无详细信息");
                    }
                }
            });

            float f3 = 0;
            try {
                f3 = Float.parseFloat(values.get(position).get(3));
                viewHolder.tvValue4.setText(values.get(position).get(3) + units.get(position).get(3));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                viewHolder.tvValue4.setText(values.get(position).get(3));
            }
            if (equipmentConfigTypes.get(position).get(3).equals("switcher") && values.get(position).get(3).equals("0")) {
                viewHolder.tvValue4.setText("关");
                viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentConfigTypes.get(position).get(3).equals("switcher") && values.get(position).get(3).equals("1")) {
                viewHolder.tvValue4.setText("开");
                viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentConfigTypes.get(position).get(3).contains("status") && values.get(position).get(3).equals("0")) {
                viewHolder.tvValue4.setText("正常");
                viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentConfigTypes.get(position).get(3).contains("status") && values.get(position).get(3).equals("1")) {
                viewHolder.tvValue4.setText("故障");
                viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(3).equals("alarm") && values.get(position).get(3).equals("0")) {
                viewHolder.tvValue4.setText("正常");
                viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(3).equals("alarm") && values.get(position).get(3).equals("1")) {
                viewHolder.tvValue4.setText("报警");
                viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(3).equals("error") && values.get(position).get(3).equals("0")) {
                viewHolder.tvValue4.setText("正常");
                viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(3).equals("error") && values.get(position).get(3).equals("1")) {
                viewHolder.tvValue4.setText("数据错误");
                viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.red));
            } else if (equipmentBizTypes.get(position).get(3).equals("exception") && values.get(position).get(3).equals("0")) {
                viewHolder.tvValue4.setText("正常");
                viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (equipmentBizTypes.get(position).get(3).equals("exception") && values.get(position).get(3).equals("1")) {
                viewHolder.tvValue4.setText("异常");
                viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                if (alarms.get(position).get(3).equals("0")) {// 正常
                    viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.black));
                } else if (alarms.get(position).get(3).equals("1")) {// 偏低
                    viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.outsideBlue));
                    viewHolder.tvValue4.append("↓");
                    viewHolder.ivStatus.setVisibility(View.VISIBLE);
                } else if (alarms.get(position).get(3).equals("2")) {// 偏高
                    viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.red));
                    viewHolder.tvValue4.append("↑");
                    viewHolder.ivStatus.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tvValue4.setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        } else {
            viewHolder.containerData3.setClickable(false);
        }

        return convertView;
    }

    private final class ViewHolder {
        RelativeLayout containerType;
        LinearLayout containerData0, containerData1, containerData2, containerData3;

        /**
         * 左上角图标
         */
        ImageView ivStatus;
        ImageView ivIcon;
        TextView tvIcon;
        TextView tvStatus;
        TextView tvTitle1;
        TextView tvValue1;
        TextView tvTitle2;
        TextView tvValue2;
        TextView tvTitle3;
        TextView tvValue3;
        TextView tvTitle4;
        TextView tvValue4;
    }
}
