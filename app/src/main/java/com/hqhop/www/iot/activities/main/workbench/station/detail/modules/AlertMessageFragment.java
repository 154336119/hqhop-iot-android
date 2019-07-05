package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.alert.detail.DetailAlertActivity;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.AlertGridViewAdapter;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.bean.AllAlertBean;
import com.hqhop.www.iot.bean.AllEquipmentsBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 站点详情页-报警信息Fragment
 * Created by allen on 2017/7/24.
 */

public class AlertMessageFragment extends Fragment {

    private String TAG = "AlertMessageFragment";

    private View rootView;

    private boolean isFirst = true;

    private Spinner spinnerEquipment, spinnerDate;

    private ArrayAdapter<String> spinnerAdapter;

    private SmartRefreshLayout refreshLayout;

    private RelativeLayout containerNoData;

    private TextView tvNodata;

    private GridView gridView;

    private AlertGridViewAdapter adapter;

    private List<Integer> levels;

    private List<String> stations;

    private List<String> reasons;

    private List<String> dates;

    private List<String> equipmentNames;

    private List<String> equipmentIds;

    private String equipmentId;

    private int dayCount = 0;

    private DetailStationActivity context;

    private boolean isLoadmore = false, isRefresh = false, finishedRefresh = true, finishedLoadmore = true, canUseSpinner = false;

    private int currentPage = 1, itemsPerPage = 8, sortType = 0;

    private AllAlertBean alertBean;

    private AllEquipmentsBean equipmentsBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_alert_message, container, false);

        context = (DetailStationActivity) getActivity();

        refreshLayout = rootView.findViewById(R.id.refresh_layout_alert_message_detail_station);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(context).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(context));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            isRefresh = true;
            isLoadmore = false;
            finishedRefresh = false;
            if (equipmentIds.size() <= 1) {
                fetchEquipments();
            } else {
                fetchAlarms();
            }
        });
        refreshLayout.setOnLoadmoreListener(refreshLayout -> {
            currentPage += 1;
            isRefresh = false;
            isLoadmore = true;
            finishedLoadmore = false;
            fetchAlarms();
        });

        spinnerEquipment = rootView.findViewById(R.id.spinner_equipments_alert_message);
//        spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, equipmentNames);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerEquipment.setAdapter(spinnerAdapter);
        spinnerEquipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                isRefresh = true;
                currentPage = 1;
                if (canUseSpinner) {
                    equipmentId = equipmentIds.get(position);
                    fetchAlarms();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDate = rootView.findViewById(R.id.spinner_date_alert_message);
        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                isRefresh = true;
                currentPage = 1;
                if (canUseSpinner) {
//                    sortType = position;
                    switch (position) {
                        case 0:
                            dayCount = 0;
                            break;
                        case 1:
                            dayCount = 1;
                            break;
                        case 2:
                            dayCount = 3;
                            break;
                        case 3:
                            dayCount = 5;
                            break;
                    }
                    fetchAlarms();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gridView = rootView.findViewById(R.id.gridview_alert_message_detail_station);
        containerNoData = rootView.findViewById(R.id.container_no_data_workbench);
        tvNodata = rootView.findViewById(R.id.tv_nodata_layout);
        tvNodata.setText(getString(R.string.no_alarm_data));
        equipmentNames = new ArrayList<>();
        equipmentIds = new ArrayList<>();

        levels = new ArrayList<>();
        stations = new ArrayList<>();
        reasons = new ArrayList<>();
        dates = new ArrayList<>();
        adapter = new AlertGridViewAdapter(context, levels, stations, reasons, dates);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent detailAlertIntent = new Intent(getActivity(), DetailAlertActivity.class);
            Bundle bundle = new Bundle();
            AllAlertBean.DataBean dataBean = alertBean.getData().get(position);
            bundle.putParcelable(Common.BUNDLE_ALERT, dataBean);
            detailAlertIntent.putExtras(bundle);
            startActivity(detailAlertIntent);
        });

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, equipmentNames);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEquipment.setAdapter(spinnerAdapter);
            fetchEquipments();
        }
    }

    private void fetchEquipments() {
        context.stationDetailService.getEquipments(DetailStationActivity.stationId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AllEquipmentsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        canUseSpinner = true;
                        CommonUtils.hideProgressDialog(context.dialog);
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadMore();
                        // 没有数据
                        equipmentNames.clear();
                        equipmentIds.clear();
                        equipmentNames.add(getString(R.string.all));
                        equipmentIds.add("all");
                        spinnerAdapter.notifyDataSetChanged();
                        fetchAlarms();
                    }

                    @Override
                    public void onNext(AllEquipmentsBean bean) {
                        canUseSpinner = true;
                        CommonUtils.hideProgressDialog(context.dialog);
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadMore();
                        equipmentNames.clear();
                        equipmentIds.clear();
                        equipmentsBean = bean;
                        setEquipments();
                    }
                });
    }

    private void setEquipments() {
        if (equipmentsBean.isSuccess() && !equipmentsBean.getData().isEmpty()) {
            // 有数据
            equipmentNames.add(getString(R.string.all));
            equipmentIds.add("all");
            for (int i = 0; i < equipmentsBean.getData().size(); i++) {
                equipmentNames.add(equipmentsBean.getData().get(i).getName());
                equipmentIds.add(equipmentsBean.getData().get(i).getId());
            }
            spinnerAdapter.notifyDataSetChanged();
        } else {
            // 没有数据
            equipmentNames.add(getString(R.string.all));
            equipmentIds.add("all");
            spinnerAdapter.notifyDataSetChanged();
        }
        fetchAlarms();
    }

    private void fetchAlarms() {
        CommonUtils.showProgressDialogWithMessage(context, context.dialog, null);
//        context.stationDetailService.getWorkbenchAlertMessage(DetailStationActivity.stationId, currentPage, itemsPerPage, sortType)
        context.stationDetailService.getWorkbenchAlertMessage(DetailStationActivity.stationId, currentPage, itemsPerPage, dayCount, equipmentId, String.valueOf(System.currentTimeMillis()))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AllAlertBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        levels.clear();
                        stations.clear();
                        reasons.clear();
                        dates.clear();
                        adapter.notifyDataSetChanged();

                        containerNoData.setVisibility(View.VISIBLE);
                        canUseSpinner = true;
                        CommonUtils.hideProgressDialog(context.dialog);
                        if (isLoadmore) {
                            currentPage -= 1;
                        }
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadMore();
                    }

                    @Override
                    public void onNext(AllAlertBean bean) {
                        canUseSpinner = true;
                        CommonUtils.hideProgressDialog(context.dialog);
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadMore();

                        alertBean = bean;
                        if (isLoadmore && bean.getData().isEmpty()) {
                            currentPage -= 1;
                        }
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            setData();
                            containerNoData.setVisibility(View.GONE);
                        } else {
                            if (isRefresh) {
                                levels.clear();
                                stations.clear();
                                reasons.clear();
                                dates.clear();
                                adapter.notifyDataSetChanged();
                            }
                            containerNoData.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void setData() {
        if (isRefresh) {
            levels.clear();
            stations.clear();
            reasons.clear();
            dates.clear();

            for (int i = 0; i < alertBean.getData().size(); i++) {
                levels.add(1);
                stations.add(alertBean.getData().get(i).getStationName());
                reasons.add(alertBean.getData().get(i).getEquipmentName() + alertBean.getData().get(i).getComments() + "　" + alertBean.getData().get(i).getValue() + alertBean.getData().get(i).getUnit());
                dates.add(alertBean.getData().get(i).getStorageTime());
            }
            adapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < alertBean.getData().size(); i++) {
                levels.add(1);
                stations.add(dates.size(), alertBean.getData().get(i).getStationName());
                reasons.add(dates.size(), alertBean.getData().get(i).getEquipmentName() + alertBean.getData().get(i).getComments() + "　" + alertBean.getData().get(i).getValue() + alertBean.getData().get(i).getUnit());
                dates.add(dates.size(), alertBean.getData().get(i).getStorageTime());
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void finishRefresh() {
        if (finishedRefresh) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh(true);
            }
            CommonUtils.hideProgressDialog(context.dialog);
        }
    }

    private void finishLoadMore() {
        if (finishedLoadmore) {
            if (refreshLayout.isLoading()) {
                refreshLayout.finishLoadmore(true);
            }
            CommonUtils.hideProgressDialog(context.dialog);
        }
    }
}
