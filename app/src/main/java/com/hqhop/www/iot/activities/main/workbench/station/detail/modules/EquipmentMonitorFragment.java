package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.EquipmentMonitorInDetailStationGridViewAdapter;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.bean.EquipmentMonitorBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.umeng.analytics.MobclickAgent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 站点详情页-设备监控Fragment
 * Created by allen on 2017/7/24.
 */

public class EquipmentMonitorFragment extends Fragment {

    private String TAG = "EquipmentMonitorFragment";

    private View rootView;

    private GridView gridView;

    private EquipmentMonitorInDetailStationGridViewAdapter adapter;

    private RelativeLayout containerNoData;

    private TextView tvNodata;

    private List<Boolean> isNormals;

    private List<String> imgUrls;

    private List<String> types;

    private List<String> statuses;

    private List<ArrayList<String>> titles;

    private List<List<String>> values;

    private List<ArrayList<String>> units;

    private List<List<String>> alarms;

    private ArrayList<String> equipmentIds;

    private List<ArrayList<String>> parameterIds;

    private List<ArrayList<String>> equipmentTypes;

    private List<ArrayList<String>> equipmentConfigTypes;

    private List<ArrayList<String>> equipmentBizTypes;

    private List<ArrayList<String>> lowerLimitsList;

    private List<ArrayList<String>> upperLimitsList;

    private List<Integer> alertIndexes;

    private SmartRefreshLayout refreshLayout;

    private DetailStationActivity context;

    private boolean finishedFetchingData = true, isSchedule = false;

    private EquipmentMonitorBean equipmentMonitorBean;

    // 定时任务
    private final Timer timer = new Timer();

    private TimerTask task;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                fetchData();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_equipment_monitor, container, false);

        context = (DetailStationActivity) getActivity();

        containerNoData = rootView.findViewById(R.id.container_no_data_workbench);
        tvNodata = rootView.findViewById(R.id.tv_nodata_layout);
        tvNodata.setText(getString(R.string.no_equipment_data));

        refreshLayout = rootView.findViewById(R.id.refresh_layout_equipment_monitor_detail_station);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(context).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(context));
        // 刷新监听
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            isSchedule = false;
            fetchData();
        });

        gridView = rootView.findViewById(R.id.gridview_equipment_monitor_detail_station);
        isNormals = new ArrayList<>();
        imgUrls = new ArrayList<>();
        types = new ArrayList<>();
        statuses = new ArrayList<>();
        titles = new ArrayList<>();
        values = new ArrayList<>();
        units = new ArrayList<>();
        alarms = new ArrayList<>();
        equipmentIds = new ArrayList<>();
        parameterIds = new ArrayList<>();
        equipmentTypes = new ArrayList<>();
        equipmentConfigTypes = new ArrayList<>();
        equipmentBizTypes = new ArrayList<>();
        upperLimitsList = new ArrayList<>();
        lowerLimitsList = new ArrayList<>();
        alertIndexes = new ArrayList<>();
        adapter = new EquipmentMonitorInDetailStationGridViewAdapter(context, isNormals, imgUrls, types, statuses, titles, values, units, alarms, equipmentIds, parameterIds, equipmentTypes, equipmentConfigTypes, equipmentBizTypes, alertIndexes, upperLimitsList, lowerLimitsList);
        gridView.setAdapter(adapter);

        task = new TimerTask() {
            @Override
            public void run() {
                isSchedule = true;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        fetchData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    private void finishRefresh() {
        if (finishedFetchingData) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh(true);
            }
            CommonUtils.hideProgressDialog(context.dialog);
        }
    }

    private void fetchData() {
        finishedFetchingData = false;
        if (!isSchedule) {
            CommonUtils.showProgressDialogWithMessage(context, context.dialog, null);
        }
        context.stationDetailService.getEquipmentMonitorList(DetailStationActivity.stationId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EquipmentMonitorBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        finishedFetchingData = true;
                        finishRefresh();
                    }

                    @Override
                    public void onNext(EquipmentMonitorBean bean) {
                        finishedFetchingData = true;
                        finishRefresh();
                        isNormals.clear();
                        if (!isSchedule) {
                            imgUrls.clear();
                        }
                        types.clear();
                        statuses.clear();
                        titles.clear();
                        values.clear();
                        equipmentIds.clear();
                        parameterIds.clear();
                        units.clear();
                        alarms.clear();
                        equipmentTypes.clear();
                        equipmentConfigTypes.clear();
                        equipmentBizTypes.clear();
                        upperLimitsList.clear();
                        lowerLimitsList.clear();
                        alertIndexes.clear();
                        adapter.notifyDataSetChanged();
                        equipmentMonitorBean = bean;
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            setData();
                            containerNoData.setVisibility(View.GONE);
                            timer.schedule(task, 8000, 8000);
                        } else {
                            containerNoData.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void setData() {
        for (int i = 0; i < equipmentMonitorBean.getData().size(); i++) {
            types.add(equipmentMonitorBean.getData().get(i).getEquipName());
            ArrayList<String> titleTemp = new ArrayList<>();
            List<String> valueTemp = new ArrayList<>();
            ArrayList<String> unitTemp = new ArrayList<>();
            List<String> alarmTemp = new ArrayList<>();
            ArrayList<String> parameterIdTemp = new ArrayList<>();
            ArrayList<String> equipmentTypeTemp = new ArrayList<>();
            ArrayList<String> equipmentConfigTypeTemp = new ArrayList<>();
            ArrayList<String> equipmentBizTypeTemp = new ArrayList<>();
            ArrayList<String> upperLimitTemp = new ArrayList<>();
            ArrayList<String> lowerLimitTemp = new ArrayList<>();
            if (!isSchedule) {
                imgUrls.add(Common.BASE_URL + "images/" + equipmentMonitorBean.getData().get(i).getEquipImg());
            }
            // EquipValuesBean的原始数据
            List<EquipmentMonitorBean.DataBean.EquipValuesBean> originDataList = equipmentMonitorBean.getData().get(i).getEquipValues();
            Collections.sort(originDataList);
            for (int j = 0; j < originDataList.size(); j++) {
                if (originDataList.get(j).getShowDisplay() != 9999) {
                    alarmTemp.add(originDataList.get(j).getAlarm());
                    titleTemp.add(originDataList.get(j).getName());
                    float valueItemTemp;
                    try {
                        valueItemTemp = Float.parseFloat(originDataList.get(j).getValue());
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        valueTemp.add(decimalFormat.format(valueItemTemp));//format 返回的是字符串
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        valueTemp.add(originDataList.get(j).getValue());
                    }
                    unitTemp.add(TextUtils.isEmpty(originDataList.get(j).getUnit()) ? " " : originDataList.get(j).getUnit());
                    parameterIdTemp.add(originDataList.get(j).getId());
                    equipmentTypeTemp.add(originDataList.get(j).getType());
                    equipmentConfigTypeTemp.add(originDataList.get(j).getConfigType());
                    equipmentBizTypeTemp.add(originDataList.get(j).getBizType());
                    upperLimitTemp.add(originDataList.get(j).getPhysicalUpperLimit());
                    lowerLimitTemp.add(originDataList.get(j).getPhysicalLowerLimit());
                }
            }
            titles.add(titleTemp);
            values.add(valueTemp);
            units.add(unitTemp);
            alarms.add(alarmTemp);
            equipmentIds.add(equipmentMonitorBean.getData().get(i).getEquipId());
            parameterIds.add(parameterIdTemp);
            upperLimitsList.add(upperLimitTemp);
            lowerLimitsList.add(lowerLimitTemp);
            alertIndexes.add(-1);
            equipmentTypes.add(equipmentTypeTemp);
            equipmentConfigTypes.add(equipmentConfigTypeTemp);
            equipmentBizTypes.add(equipmentBizTypeTemp);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
