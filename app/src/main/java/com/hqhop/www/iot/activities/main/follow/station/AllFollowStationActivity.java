package com.hqhop.www.iot.activities.main.follow.station;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.follow.FollowFragment;
import com.hqhop.www.iot.activities.main.follow.module.ModuleSettingActivity;
import com.hqhop.www.iot.api.follow.FollowService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.AllStationGridViewAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.StationTestBean;
import com.hqhop.www.iot.bean.StationTypeBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllFollowStationActivity extends BaseAppCompatActivity {

    private SmartRefreshLayout refreshLayout;

    private RelativeLayout containerNoData;

    private TextView tvNodata;

    private GridView gridviewAllStation;

    private AllStationGridViewAdapter stationListAdapter;

    private Spinner spinnerSort;

    private Dialog dialog;

    private int sortType = 3;

    private int pageNumber = 1;

    private int itemsPerPage = 10;

    private String stationType = "all";

    private int regionId = 0;

    private int status = 0;

    private boolean isLoadmore = false, isRefresh = false, finishedRefresh = true, finishedLoadmore = true, canUseSpinner = false;

    private FollowService followService;

    private List<String> stationListTypes;

    private List<String> stationListStations;

    private List<String> stationListAddresses;

    private List<Integer> stationIds;

    private StationTestBean bean1, bean2, bean3;

    private StationTypeBean stationTypeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        setToolBarTitle(getString(R.string.all_station));

        refreshLayout = findViewById(R.id.refresh_layout_all_station_follow);
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

        followService = RetrofitManager.getInstance(this).createService(FollowService.class);

        dialog = CommonUtils.createLoadingDialog(this);

        containerNoData = findViewById(R.id.container_no_data_workbench);
        tvNodata = findViewById(R.id.tv_nodata_layout);
        tvNodata.setText("暂无站点数据");
        gridviewAllStation = findViewById(R.id.gridview_station_all_station_follow);
        stationListTypes = new ArrayList<>();
        stationListStations = new ArrayList<>();
        stationListAddresses = new ArrayList<>();
        stationIds = new ArrayList<>();
        stationListAdapter = new AllStationGridViewAdapter(this, stationListTypes, stationListStations, stationListAddresses);
        gridviewAllStation.setAdapter(stationListAdapter);
        gridviewAllStation.setOnItemClickListener((adapterView, view, position, id) -> {
            //            Intent detailIntent = new Intent(this, DetailFollowStationActivity.class);
//            Bundle bundle = new Bundle();
//            if (position == 0)
//                bundle.putParcelable(Common.BUNDLE_FOLLOW_STATION, bean1);
//            else if (position == 1)
//                bundle.putParcelable(Common.BUNDLE_FOLLOW_STATION, bean2);
//            else if (position == 2)
//                bundle.putParcelable(Common.BUNDLE_FOLLOW_STATION, bean3);
//            detailIntent.putExtras(bundle);
//            startActivity(detailIntent);
            Intent moduleSettingIntent = new Intent(AllFollowStationActivity.this, ModuleSettingActivity.class);
//                if (position == 0)
//                    moduleSettingIntent.putExtra("id", String.valueOf(stationIds.get(position)));
//                else if (position == 1)
//                    moduleSettingIntent.putExtra("id", String.valueOf(2L));
//                else if (position == 2)
//                    moduleSettingIntent.putExtra("id", String.valueOf(3L));
            moduleSettingIntent.putExtra("id", String.valueOf(stationIds.get(position)));
            startActivityForResult(moduleSettingIntent, Common.REQUEST_CODE_MODULE_SETTING);
        });

        spinnerSort = findViewById(R.id.spinner_type_all_station_follow);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (canUseSpinner) {
                    if (position == 0) {
                        sortType = 3;
                    } else if (position == 1) {
                        sortType = 4;
                    } else {
                        sortType = 5;
                    }
                    isRefresh = true;
                    fetchData();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fetchData();
    }

    private void fetchData() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
        followService.getAllStations(App.userid, pageNumber, itemsPerPage, stationType, sortType, regionId, status)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StationTypeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        canUseSpinner = true;
                        containerNoData.setVisibility(View.VISIBLE);
                        CommonUtils.showToast(getString(R.string.network_error));
                        CommonUtils.hideProgressDialog(dialog);
//                        finishedFetchingStations = true;
//                        finishRefresh();
//                        CommonUtils.hideProgressDialog(dialog);
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
                        CommonUtils.hideProgressDialog(dialog);
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadMore();
                        Log.d("Allen", bean.getData().size() + "");

                        stationTypeBean = bean;
                        if (isLoadmore && bean.getData().isEmpty()) {
                            pageNumber -= 1;
                        }
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            setStationListData();
                            containerNoData.setVisibility(View.GONE);
                        } else {
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
            stationIds.clear();

            int total = stationTypeBean.getData().size();
            for (int i = 0; i < total; i++) {
                stationListTypes.add(stationTypeBean.getData().get(i).getType());
                stationListStations.add(stationTypeBean.getData().get(i).getName());
                stationListAddresses.add(stationTypeBean.getData().get(i).getAddress());
                stationIds.add(stationTypeBean.getData().get(i).getId());
            }
            stationListAdapter.notifyDataSetChanged();
        } else {
            int total = stationTypeBean.getData().size();
            for (int i = 0; i < total; i++) {
                stationListTypes.add(stationIds.size(), stationTypeBean.getData().get(i).getType());
                stationListStations.add(stationIds.size(), stationTypeBean.getData().get(i).getName());
                stationListAddresses.add(stationIds.size(), stationTypeBean.getData().get(i).getAddress());
                stationIds.add(stationIds.size(), stationTypeBean.getData().get(i).getId());
            }
            stationListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 关闭刷新动画
     */
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
        return R.layout.activity_all_follow_station;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_all_follow_station, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_item_add_all_follow_station:
//                CommonUtils.showToast("添加站点");
//                break;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        FollowFragment.getInstance(this).refresh();
        finish();
    }
}
