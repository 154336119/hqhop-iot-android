package com.hqhop.www.iot.activities.main.follow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.MainActivity;
import com.hqhop.www.iot.activities.main.follow.station.AllFollowStationActivity;
import com.hqhop.www.iot.api.follow.FollowService;
import com.hqhop.www.iot.base.adapter.FollowStationListViewAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.ToolbarUtils;
import com.hqhop.www.iot.bean.FollowDataBean;
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
 * Created by allen on 2017/7/3.
 */

@SuppressLint("ValidFragment")
public class FollowFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private String TAG = "FollowFragment";

    private Context mContext;

    private SmartRefreshLayout refreshLayout;

    private RelativeLayout containerNoData;

    private TextView tvNodata;

    private View rootView;

    private Toolbar toolbar;

    private TextView titleToolbar;

    private ExpandableListView listView;

    private FollowStationListViewAdapter adapter;

    /**
     * 站点id
     */
    private List<String> ids;

    /**
     * 站点名
     */
    private List<String> stations;

    /**
     * 站点状态
     */
    private List<String> statuses;

    /**
     * 标题
     */
    private List<ArrayList<String>> titles;

    /**
     * 数值
     */
    private List<ArrayList<String>> values;

    private List<ArrayList<String>> units;

    private List<ArrayList<String>> alarms;

    private List<ArrayList<String>> parameterNames;

    private List<ArrayList<String>> parameterIds;

    /**
     * 设备的configId
     */
    private List<ArrayList<String>> equipmentIds;

    private List<Integer> stationIds;

    private List<FollowDataBean.DataBean> beanList;

    private FollowService followService;

    private FollowDataBean followDataBean;

    private boolean finishedFetchingData = true;

    private static FollowFragment followFragment;

    public FollowFragment() {
    }

    private FollowFragment(Context context) {
        mContext = context;
    }

    public static FollowFragment getInstance(Context context) {
        if (followFragment == null) {
            followFragment = new FollowFragment(context);
        }
        return followFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (rootView == null) {
        rootView = inflater.inflate(R.layout.fragment_follow, container, false);
//        }

        CommonUtils.setTranslucentStatusBar(mContext);

        followService = RetrofitManager.getInstance(getActivity()).createService(FollowService.class);

        refreshLayout = rootView.findViewById(R.id.refresh_layout_follow);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()));
        // 刷新监听
        refreshLayout.setOnRefreshListener(refreshLayout -> fetchData());

        toolbar = rootView.findViewById(R.id.toolbar);
        titleToolbar = rootView.findViewById(R.id.title_toolbar);
        setupToolbar();

        containerNoData = rootView.findViewById(R.id.container_no_data_workbench);
        tvNodata = rootView.findViewById(R.id.tv_nodata_layout);
        tvNodata.setText(getString(R.string.no_follow));
        listView = rootView.findViewById(R.id.listview_station_follow);
        ids = new ArrayList<>();
        stations = new ArrayList<>();
        statuses = new ArrayList<>();
        titles = new ArrayList<>();
        values = new ArrayList<>();
        units = new ArrayList<>();
        alarms = new ArrayList<>();
        parameterNames = new ArrayList<>();
        parameterIds = new ArrayList<>();
        equipmentIds = new ArrayList<>();
        stationIds = new ArrayList<>();
        adapter = new FollowStationListViewAdapter(mContext, ids, stations, statuses, titles, values, units, alarms, parameterNames, parameterIds, equipmentIds, stationIds);

        listView.setAdapter(adapter);
//        listView.setOnGroupClickListener(this);
        beanList = new ArrayList<>();
        // 设置只能展开一项
        listView.setOnGroupExpandListener(groupPosition -> {
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                if (groupPosition != i) {
                    listView.collapseGroup(i);
                }
            }
        });
        fetchData();

        return rootView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onPageStart(TAG);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPageEnd(TAG);
//    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            MobclickAgent.onPageStart(TAG);
        } else {
            MobclickAgent.onPageEnd(TAG);
        }
    }

    /**
     * 获取关注页面数据
     */
    private void fetchData() {
        CommonUtils.showProgressDialogWithMessage(getActivity(), MainActivity.dialog, null);
        finishedFetchingData = false;
//        followService.getFollowData(App.userid)
//        followService.getFollowData("40288e81567c9e7f01567cc223440007")
        followService.getFollowData(App.userid)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FollowDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        containerNoData.setVisibility(View.VISIBLE);
                        CommonUtils.showToast(getString(R.string.network_error));
                        finishedFetchingData = true;
                        finishRefresh();
                    }

                    @Override
                    public void onNext(FollowDataBean bean) {
                        finishedFetchingData = true;
                        finishRefresh();
                        beanList.clear();
                        ids.clear();
                        stations.clear();
                        statuses.clear();
                        titles.clear();
                        values.clear();
                        units.clear();
                        alarms.clear();
                        parameterNames.clear();
                        parameterIds.clear();
                        equipmentIds.clear();
                        stationIds.clear();
                        followDataBean = bean;
//                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                        if (bean.isSuccess()) {
                            setFollowData();
                            containerNoData.setVisibility(View.GONE);
                        } else {
                            containerNoData.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void setFollowData() {
        for (int i = 0; i < followDataBean.getData().size(); i++) {
            beanList.add(followDataBean.getData().get(i));
            ids.add(String.valueOf(followDataBean.getData().get(i).getStationId()));
            stations.add(followDataBean.getData().get(i).getStationName());
            statuses.add("");
            ArrayList<String> tempTitles = new ArrayList<>();
            ArrayList<String> tempValues = new ArrayList<>();
            ArrayList<String> tempUnits = new ArrayList<>();
            ArrayList<String> tempAlarms = new ArrayList<>();
            ArrayList<String> tempParamaterName = new ArrayList<>();
            ArrayList<String> tempParamaterId = new ArrayList<>();
            ArrayList<String> tempEquipmentId = new ArrayList<>();
            for (int j = 0; j < followDataBean.getData().get(i).getParameters().size(); j++) {
                tempTitles.add(followDataBean.getData().get(i).getParameters().get(j).getConfigName());
                tempValues.add(TextUtils.isEmpty(followDataBean.getData().get(i).getParameters().get(j).getValue()) ? " " : followDataBean.getData().get(i).getParameters().get(j).getValue());
                tempUnits.add(TextUtils.isEmpty(followDataBean.getData().get(i).getParameters().get(j).getUnit()) ? " " : followDataBean.getData().get(i).getParameters().get(j).getUnit());
                tempAlarms.add(TextUtils.isEmpty(followDataBean.getData().get(i).getParameters().get(j).getAlarm()) ? "" : followDataBean.getData().get(i).getParameters().get(j).getAlarm());
                tempParamaterName.add(followDataBean.getData().get(i).getParameters().get(j).getConfigName());
                tempParamaterId.add(followDataBean.getData().get(i).getParameters().get(j).getConfigId());
                tempEquipmentId.add(followDataBean.getData().get(i).getParameters().get(j).getEquipmentId());
            }
            titles.add(tempTitles);
            values.add(tempValues);
            units.add(tempUnits);
            alarms.add(tempAlarms);
            parameterNames.add(tempParamaterName);
            parameterIds.add(tempParamaterId);
            equipmentIds.add(tempEquipmentId);
            stationIds.add(followDataBean.getData().get(i).getStationId());
        }
        adapter.notifyDataSetChanged();
        if (followDataBean.getData().size() == 0) {
            containerNoData.setVisibility(View.VISIBLE);
        }
        int count = listView.getAdapter().getCount();
        if (count > 0) {
            listView.expandGroup(0);
        }
//        if (count > 0) {
//            listView.expandGroup(0);
//            if (count == 1) {
//                listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//                    @Override
//                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                        return true;
//                    }
//                });
//            } else {
//                listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//                    @Override
//                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                        adapter.setSelected(i);
//                        return false;
//                    }
//                });
//            }
//        }
    }

    /**
     * 关闭刷新动画
     */
    private void finishRefresh() {
        if (finishedFetchingData) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh(true);
            }
            CommonUtils.hideProgressDialog(MainActivity.dialog);
        }
    }

    private void setupToolbar() {
        ToolbarUtils.setCustomToolbar(mContext, toolbar);
        ToolbarUtils.setTitle(titleToolbar, getString(R.string.title_follow));
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.toolbar_follow);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_follow:
                Intent allFollowIntent = new Intent(mContext, AllFollowStationActivity.class);
                mContext.startActivity(allFollowIntent);
                break;
            default:
                break;
        }
        return false;
    }

//    @Override
//    public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long l) {
//        adapter.setSelected(position);
//        return false;
//    }

    public void refresh() {
        fetchData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        followFragment = null;
    }

    public void destroySelf() {
        followFragment = null;
    }
}
