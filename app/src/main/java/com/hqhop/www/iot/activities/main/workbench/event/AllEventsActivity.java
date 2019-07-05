package com.hqhop.www.iot.activities.main.workbench.event;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.alert.detail.DetailAlertActivity;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.api.workbench.WorkbenchService;
import com.hqhop.www.iot.api.workbench.type.StationTypeService;
import com.hqhop.www.iot.base.adapter.EventsGridViewAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.EventsBean;
import com.hqhop.www.iot.bean.StationTypeBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllEventsActivity extends BaseAppCompatActivity {

    private SmartRefreshLayout refreshLayout;

    private Spinner spinnerStation, spinnerType;

    private ArrayAdapter<String> spinnerAdapter;

    private String queryStationId;

    private GridView gridView;

    private RelativeLayout containerNoData;

    private TextView tvNodata;

    private StationTypeBean stationTypeBean;

    private List<String> stationNames = new ArrayList<>();

    private List<String> stationIds = new ArrayList<>();

    private EventsGridViewAdapter adapter;

    private List<String> eventStationIds;

    private List<String> eventStations;

    private List<String> eventContents;

    private List<String> eventDates;

    private List<String> eventScenes;

    private Dialog dialog;

    private int pageNumber = 1;

    private int itemsPerPage = 10;

    private String eventsType = "all";

    private boolean isLoadmore = false, isRefresh = false, finishedRefresh = true, finishedLoadmore = true, canUseSpinner = false;

    private WorkbenchService workbenchService;

    private StationTypeService stationTypeService;

    private EventsBean eventsBean;

    private List<String> leaderNames, leaderMobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle(getString(R.string.all_events));

        workbenchService = RetrofitManager.getInstance(this).createService(WorkbenchService.class);
        stationTypeService = RetrofitManager.getInstance(this).createService(StationTypeService.class);

        dialog = CommonUtils.createLoadingDialog(this);

        refreshLayout = findViewById(R.id.refresh_layout_all_events);
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

        spinnerStation = findViewById(R.id.spinner_station_all_events);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stationNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStation.setAdapter(spinnerAdapter);
        spinnerStation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                isRefresh = true;
                pageNumber = 1;
                if (canUseSpinner) {
                    queryStationId = stationIds.get(position);
                    fetchData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerType = findViewById(R.id.spinner_type_all_events);
        spinnerType.setSelection(0, true);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                isRefresh = true;
                pageNumber = 1;
                switch (position) {
                    case 0:
                        if (canUseSpinner) {
                            eventsType = "all";
                            fetchData();
                        }
                        break;
                    case 1:
                        if (canUseSpinner) {
                            eventsType = "alarm";
//                            eventsType = "报警";
                            fetchData();
                        }
                        break;
                    case 2:
                        if (canUseSpinner) {
                            eventsType = "alarm_clear";
//                            eventsType = "报警清除";
                            fetchData();
                        }
                        break;
                    case 3:
                        if (canUseSpinner) {
                            eventsType = "scene";
                            fetchData();
                        }
                        break;
                    case 4:
                        if (canUseSpinner) {
                            eventsType = "online";
//                            eventsType = "上线";
                            fetchData();
                        }
                        break;
                    case 5:
                        if (canUseSpinner) {
                            eventsType = "offline";
//                            eventsType = "离线";
                            fetchData();
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gridView = findViewById(R.id.gridview_all_events);
        containerNoData = findViewById(R.id.container_no_data_workbench);
        tvNodata = findViewById(R.id.tv_nodata_layout);
        tvNodata.setText(getString(R.string.no_events));
        leaderNames = new ArrayList<>();
        leaderMobiles = new ArrayList<>();
        eventStationIds = new ArrayList<>();
        eventStations = new ArrayList<>();
        eventContents = new ArrayList<>();
        eventDates = new ArrayList<>();
        eventScenes = new ArrayList<>();
        adapter = new EventsGridViewAdapter(this, eventStationIds, eventStations, eventContents, eventDates, eventScenes);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((adapterView, view, position, id) -> {
            if (eventsBean.getData().get(position).getCategory().equals("alarm")) {
                Intent detailAlertIntent = new Intent(this, DetailAlertActivity.class);
                detailAlertIntent.putExtra("stationId", eventsBean.getData().get(position).getStationId());
                detailAlertIntent.putExtra("parameterId", eventsBean.getData().get(position).getParameterId());
                startActivity(detailAlertIntent);
            } else {
                String stationId = eventStationIds.get(position);
                String stationName = eventStations.get(position);
                Intent detailIntent = new Intent(AllEventsActivity.this, DetailStationActivity.class);
                detailIntent.putExtra("type", 1);
                detailIntent.putExtra("id", stationId);
                detailIntent.putExtra("name", stationName);
//                detailIntent.putExtra("alarmCount", eventsBean.getData().get(position))
//            detailIntent.putExtra("leaderName", eventsBean.getData().get(position).getLeaderName());
//            detailIntent.putExtra("leaderPhone", eventsBean.getData().get(position).getLeaderMobile());
                detailIntent.putExtra("leaderName", leaderNames.get(position));
                detailIntent.putExtra("leaderPhone", leaderMobiles.get(position));
//            if (eventsBean.getData().get(position).getCategory().equals("alarm") || eventsBean.getData().get(position).getCategory().equals("alarm_clear")) {
//                detailIntent.putExtra("showShowAlarm", true);
//            }
                startActivity(detailIntent);
            }
        });
        fetchStations();
//        fetchData();
    }

    private void fetchStations() {
        stationTypeService.getStations(App.userid, 0, itemsPerPage, "all", 3, 0, 0)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StationTypeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        canUseSpinner = false;
                    }

                    @Override
                    public void onNext(StationTypeBean bean) {
                        if (bean.isSuccess() && bean.getData().size() > 0) {
                            stationTypeBean = bean;
                            setStations();
                            canUseSpinner = true;
                        }
                    }
                });
    }

    private void setStations() {
        stationNames.clear();
        stationIds.clear();
        for (int i = 0; i < stationTypeBean.getData().size(); i++) {
            stationNames.add(stationTypeBean.getData().get(i).getName());
            stationIds.add(String.valueOf(stationTypeBean.getData().get(i).getId()));
        }
        queryStationId = stationIds.get(0);
        spinnerAdapter.notifyDataSetChanged();
        fetchData();
    }

    private void fetchData() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
        workbenchService.getEventsMessageByPage(queryStationId, eventsType, pageNumber, itemsPerPage)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EventsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        leaderNames.clear();
                        leaderMobiles.clear();
                        eventStationIds.clear();
                        eventStations.clear();
                        eventContents.clear();
                        eventDates.clear();
                        eventScenes.clear();
                        adapter.notifyDataSetChanged();

                        containerNoData.setVisibility(View.VISIBLE);
                        CommonUtils.showToast(getString(R.string.network_error));
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
                    public void onNext(EventsBean bean) {
                        CommonUtils.hideProgressDialog(dialog);
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadMore();

                        eventsBean = bean;
                        if (isLoadmore && bean.getData().isEmpty()) {
                            pageNumber -= 1;
                        }
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            setEvents();
                            containerNoData.setVisibility(View.GONE);
                        } else {
                            if (isRefresh) {
                                leaderNames.clear();
                                leaderMobiles.clear();
                                eventStationIds.clear();
                                eventStations.clear();
                                eventContents.clear();
                                eventDates.clear();
                                eventScenes.clear();
                                adapter.notifyDataSetChanged();
                            }
                            containerNoData.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void setEvents() {
        if (isRefresh) {
            leaderNames.clear();
            leaderMobiles.clear();
            eventStationIds.clear();
            eventStations.clear();
            eventContents.clear();
            eventDates.clear();
            eventScenes.clear();

            for (int i = 0; i < eventsBean.getData().size(); i++) {
                leaderNames.add(eventsBean.getData().get(i).getLeaderName());
                leaderMobiles.add(eventsBean.getData().get(i).getLeaderMobile());
                eventStationIds.add(eventsBean.getData().get(i).getStationId());
                eventStations.add(eventsBean.getData().get(i).getStationName());
                eventContents.add(eventsBean.getData().get(i).getContent());
                eventDates.add(eventsBean.getData().get(i).getTime());
                eventScenes.add(eventsBean.getData().get(i).getCategory());
            }
            adapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < eventsBean.getData().size(); i++) {
                leaderNames.add(eventScenes.size(), eventsBean.getData().get(i).getLeaderName());
                leaderMobiles.add(eventScenes.size(), eventsBean.getData().get(i).getLeaderMobile());
                eventStationIds.add(eventScenes.size(), eventsBean.getData().get(i).getStationId());
                eventStations.add(eventScenes.size(), eventsBean.getData().get(i).getStationName());
                eventContents.add(eventScenes.size(), eventsBean.getData().get(i).getContent());
                eventDates.add(eventScenes.size(), eventsBean.getData().get(i).getTime());
                eventScenes.add(eventScenes.size(), eventsBean.getData().get(i).getCategory());
            }
            adapter.notifyDataSetChanged();
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
    protected int getLayoutId() {
        return R.layout.activity_all_events;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }
}
