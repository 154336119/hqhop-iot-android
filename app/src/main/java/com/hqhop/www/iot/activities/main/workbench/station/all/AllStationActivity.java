package com.hqhop.www.iot.activities.main.workbench.station.all;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.activities.main.workbench.station.type.NewStationTypeActivity;
import com.hqhop.www.iot.api.workbench.type.StationTypeService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.AllStationGridViewAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.StationTypeBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllStationActivity extends BaseAppCompatActivity {

    private GridView gridviewAllStation;

    private AllStationGridViewAdapter stationListAdapter;

    private RelativeLayout containerNoData;

    private TextView tvNodata;

    private SmartRefreshLayout refreshLayout;

    private List<String> stationListTypes;

    private List<String> stationListStations;

    private List<String> stationListAddresses;

    private List<String> stationListIds;

    private List<Integer> stationListAlarmCounts;

    private List<String> stationListLeaderNames;

    private List<String> stationListLeaderMobiles;

    private List<String> stationListLongitudess;

    private List<String> stationListLatitudes;

    private Spinner spinner, spinnerStatus;

    private Dialog dialog;

    private StationTypeService stationTypeService;

    private int pageNumber = 1;

    private int itemsPerPage = 10;

    private boolean isLoadmore = false, isRefresh = false, finishedRefresh = true, finishedLoadmore = true, canUseSpinner = false;

    private int sortType = 3, status = 0;

    private StationTypeBean stationTypeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        fetchData();
    }

    private void init() {
        setToolBarTitle(getString(R.string.all_station));

        dialog = CommonUtils.createLoadingDialog(this);

        stationTypeService = RetrofitManager.getInstance(this).createService(StationTypeService.class);

        refreshLayout = findViewById(R.id.refresh_layout_all_station);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(this));
        // 刷新监听
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNumber = 1;
            isRefresh = true;
            isLoadmore = false;
            finishedRefresh = false;
            fetchData();
        });
        refreshLayout.setOnLoadmoreListener(refreshLayout -> {
            pageNumber += 1;
            isRefresh = false;
            isLoadmore = true;
            finishedLoadmore = false;
            fetchData();
        });

        gridviewAllStation = findViewById(R.id.gridview_station_all_station);
        stationListTypes = new ArrayList<>();
        stationListStations = new ArrayList<>();
        stationListAddresses = new ArrayList<>();
        stationListIds = new ArrayList<>();
        stationListAlarmCounts = new ArrayList<>();
        stationListLeaderNames = new ArrayList<>();
        stationListLeaderMobiles = new ArrayList<>();
        stationListLongitudess = new ArrayList<>();
        stationListLatitudes = new ArrayList<>();
        stationListAdapter = new AllStationGridViewAdapter(this, stationListTypes, stationListStations, stationListAddresses);
        gridviewAllStation.setAdapter(stationListAdapter);
        gridviewAllStation.setOnItemClickListener((parent, view, position, id) -> {
            Intent detailStationIntent = new Intent(AllStationActivity.this, DetailStationActivity.class);
//            Bundle bundle = new Bundle();
//            StationTypeBean.DataBean dataBean = stationTypeBean.getData().get(position);
//            bundle.putParcelable(Common.BUNDLE_STATION, dataBean);
//            detailStationIntent.putExtras(bundle);
            detailStationIntent.putExtra("name", stationListStations.get(position));
            detailStationIntent.putExtra("id", stationListIds.get(position));
            detailStationIntent.putExtra("alarmCount", stationListAlarmCounts.get(position));
            detailStationIntent.putExtra("leaderName", stationListLeaderNames.get(position));
            detailStationIntent.putExtra("leaderPhone", stationListLeaderMobiles.get(position));
            detailStationIntent.putExtra("stationType", stationListTypes.get(position));
            detailStationIntent.putExtra("longitude", stationListLongitudess.get(position));
            detailStationIntent.putExtra("latitude", stationListLatitudes.get(position));
            startActivity(detailStationIntent);
        });

        spinner = findViewById(R.id.spinner_type_all_station);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                sortType = position;
                if (position == 0) {
                    sortType = 3;
                } else if (position == 1) {
                    sortType = 4;
                } else {
                    sortType = 3;
                }
                if (canUseSpinner) {
                    isRefresh = true;
                    pageNumber = 1;
                    fetchData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerStatus = findViewById(R.id.spinner_status_all_station);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    status = 0;
                } else if (position == 1) {
                    status = 1;
                } else if (position == 2) {
                    status = 2;
                } else if (position == 3) {
                    status = 3;
                }
                if (canUseSpinner) {
                    isRefresh = true;
                    pageNumber = 1;
                    fetchData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        containerNoData = findViewById(R.id.container_no_data_workbench);
        tvNodata = findViewById(R.id.tv_nodata_layout);
        tvNodata.setText(getString(R.string.no_station_data));
    }

    private void fetchData() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
//        stationTypeService.getStations(App.userid, 1, 10, StationTypeActivity.type, sortType, 0, 0)
        String temp;
        if (NewStationTypeActivity.type.toLowerCase().equals("cng")) {
            temp = "[cng,lng,l-cng]";
        } else {
            temp = NewStationTypeActivity.type;
        }
//        stationTypeService.getStations(App.userid, pageNumber, itemsPerPage, NewStationTypeActivity.type, sortType, 0, status)
        stationTypeService.getStations(App.userid, pageNumber, itemsPerPage, temp, sortType, 0, status)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StationTypeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stationListTypes.clear();
                        stationListStations.clear();
                        stationListAddresses.clear();
                        stationListIds.clear();
                        stationListAlarmCounts.clear();
                        stationListLeaderNames.clear();
                        stationListLeaderMobiles.clear();
                        stationListLongitudess.clear();
                        stationListLatitudes.clear();
                        stationListAdapter.notifyDataSetChanged();

                        containerNoData.setVisibility(View.VISIBLE);
                        canUseSpinner = true;
                        CommonUtils.showToast(getString(R.string.network_error));
//                        finishedFetchingStations = true;
//                        finishRefresh();
                        CommonUtils.hideProgressDialog(dialog);
                        if (isLoadmore) {
                            pageNumber -= 1;
                        }
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadMore();
                    }

                    @Override
                    public void onNext(StationTypeBean bean) {
                        canUseSpinner = true;
//                        finishedFetchingStations = true;
//                        finishRefresh();
                        CommonUtils.hideProgressDialog(dialog);
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadMore();

                        stationTypeBean = bean;
                        if (isLoadmore && bean.getData().isEmpty()) {
                            pageNumber -= 1;
                        }
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            containerNoData.setVisibility(View.GONE);
                            setStationListData();
                        } else {
                            if (isRefresh) {
                                stationListTypes.clear();
                                stationListStations.clear();
                                stationListAddresses.clear();
                                stationListIds.clear();
                                stationListAlarmCounts.clear();
                                stationListLeaderNames.clear();
                                stationListLeaderMobiles.clear();
                                stationListLongitudess.clear();
                                stationListLatitudes.clear();
                                stationListAdapter.notifyDataSetChanged();
                            }
                            containerNoData.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void setStationListData() {
        if (isRefresh) {
            stationListTypes.clear();
            stationListStations.clear();
            stationListAddresses.clear();
            stationListIds.clear();
            stationListAlarmCounts.clear();
            stationListLeaderNames.clear();
            stationListLeaderMobiles.clear();
            stationListLongitudess.clear();
            stationListLatitudes.clear();

            for (int i = 0; i < stationTypeBean.getData().size(); i++) {
                stationListTypes.add(stationTypeBean.getData().get(i).getType());
                stationListStations.add(stationTypeBean.getData().get(i).getName());
                stationListIds.add(String.valueOf(stationTypeBean.getData().get(i).getId()));
                stationListAlarmCounts.add(stationTypeBean.getData().get(i).getAlarmCount());
                stationListLeaderNames.add(stationTypeBean.getData().get(i).getLeaderName());
                stationListLeaderMobiles.add(stationTypeBean.getData().get(i).getLeaderMobile());
                stationListLongitudess.add(stationTypeBean.getData().get(i).getLongitude());
                stationListLatitudes.add(stationTypeBean.getData().get(i).getLatitude());
                stationListAddresses.add(stationTypeBean.getData().get(i).getAddress());
            }
            stationListAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < stationTypeBean.getData().size(); i++) {
                stationListTypes.add(stationListAddresses.size(), stationTypeBean.getData().get(i).getType());
                stationListStations.add(stationListAddresses.size(), stationTypeBean.getData().get(i).getName());
                stationListIds.add(stationListAddresses.size(), String.valueOf(stationTypeBean.getData().get(i).getId()));
                stationListAlarmCounts.add(stationListAddresses.size(), stationTypeBean.getData().get(i).getAlarmCount());
                stationListLeaderNames.add(stationListAddresses.size(), stationTypeBean.getData().get(i).getLeaderName());
                stationListLeaderMobiles.add(stationListAddresses.size(), stationTypeBean.getData().get(i).getLeaderMobile());
                stationListLongitudess.add(stationListAddresses.size(), stationTypeBean.getData().get(i).getLongitude());
                stationListLatitudes.add(stationListAddresses.size(), stationTypeBean.getData().get(i).getLatitude());
                stationListAddresses.add(stationListAddresses.size(), stationTypeBean.getData().get(i).getAddress());
            }
            stationListAdapter.notifyDataSetChanged();
        }
    }

    private void finishRefresh() {
        if (finishedRefresh) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh(true);
            }
            CommonUtils.hideProgressDialog(dialog);
        }
    }

    private void finishLoadMore() {
        if (finishedLoadmore) {
            if (refreshLayout.isLoading()) {
                refreshLayout.finishLoadmore(true);
            }
            CommonUtils.hideProgressDialog(dialog);
        }
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_station;
    }
}
