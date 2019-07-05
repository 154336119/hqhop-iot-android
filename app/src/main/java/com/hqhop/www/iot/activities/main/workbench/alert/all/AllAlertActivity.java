package com.hqhop.www.iot.activities.main.workbench.alert.all;

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
import com.hqhop.www.iot.activities.main.workbench.alert.detail.DetailAlertActivity;
import com.hqhop.www.iot.api.workbench.alert.AlertService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.AlertGridViewAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.AllAlertBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllAlertActivity extends BaseAppCompatActivity {

    private SmartRefreshLayout refreshLayout;

    private Spinner spinnerSortType;

    private RelativeLayout containerNoData;

    private TextView tvNodata;

    /**
     * 报警信息GridView
     */
    private GridView alertGridView;

    private AlertGridViewAdapter alertAdapter;

    private List<Integer> alertLevels;

    private List<String> alertStations;

    private List<String> alertReasons;

    private List<String> alertDates;

    private Dialog dialog;

    private AlertService alertService;

    private AllAlertBean allAlertBean;

    private boolean finishedFetchingData = true;

    private boolean isLoadmore = false, isRefresh = false, finishedRefresh = true, finishedLoadmore = true, canUseSpinner = false;

    private int currentPage = 1, itemsPerPage = 8;

    /**
     * 排序规则:
     * 默认
     * 时间由近到远
     * 时间由远到近
     * 轻微到紧急
     * 紧急到轻微
     */
    private int sortType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_alert;
    }

    private void init() {
        setToolBarTitle("全部报警信息");

        dialog = CommonUtils.createLoadingDialog(this);
        alertService = RetrofitManager.getInstance(this).createService(AlertService.class);

        containerNoData = findViewById(R.id.container_no_data_workbench);
        tvNodata = findViewById(R.id.tv_nodata_layout);
        tvNodata.setText("暂无报警数据");

        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout_all_alert);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(this));
        // 刷新监听
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                isRefresh = true;
                isLoadmore = false;
                finishedRefresh = false;
                fetchData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentPage += 1;
                isRefresh = false;
                isLoadmore = true;
                finishedLoadmore = false;
                fetchData();
            }
        });

        spinnerSortType = (Spinner) findViewById(R.id.spinner_type_all_alert);
        spinnerSortType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                isRefresh = true;
                currentPage = 1;
                if (canUseSpinner) {
                    sortType = position;
                    fetchData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        alertGridView = (GridView) findViewById(R.id.gridview_all_alert);
        alertLevels = new ArrayList<>();
        alertStations = new ArrayList<>();
        alertReasons = new ArrayList<>();
        alertDates = new ArrayList<>();
        alertAdapter = new AlertGridViewAdapter(this, alertLevels, alertStations, alertReasons, alertDates);
        alertGridView.setAdapter(alertAdapter);
        alertGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent detailAlertIntent = new Intent(AllAlertActivity.this, DetailAlertActivity.class);
                Bundle bundle = new Bundle();
                AllAlertBean.DataBean dataBean = allAlertBean.getData().get(position);
//                AlertTestBean alertTestBean = new AlertTestBean();
//                alertTestBean.setEmergency("紧急");
//                alertTestBean.setStation("成都市龙泉CNG站");
//                alertTestBean.setPerson("秦中正");
//                alertTestBean.setPhone("18696914606");
//                alertTestBean.setReason("储罐压力过大");
//                alertTestBean.setValue("1000MPa");
//                alertTestBean.setDate("2017-06-30");
//                alertTestBean.setDevice("储罐");
                bundle.putParcelable(Common.BUNDLE_ALERT, dataBean);
                detailAlertIntent.putExtras(bundle);
                startActivity(detailAlertIntent);
            }
        });

        fetchData();
    }

    /**
     * 获取、刷新数据
     */
    private void fetchData() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
        finishedFetchingData = false;
        alertService.getAllAlertMessage(App.userid, currentPage, itemsPerPage, "all", sortType)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AllAlertBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        containerNoData.setVisibility(View.VISIBLE);
                        CommonUtils.showToast("网络错误");
                        canUseSpinner = true;
                        CommonUtils.hideProgressDialog(dialog);
                        if (isLoadmore) {
                            currentPage -= 1;
                        }
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadmore();
                    }

                    @Override
                    public void onNext(AllAlertBean bean) {
                        canUseSpinner = true;
                        CommonUtils.hideProgressDialog(dialog);
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadmore();

                        allAlertBean = bean;
                        if (isLoadmore && bean.getData().isEmpty()) {
                            currentPage -= 1;
                        }
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            setAlertData();
                            containerNoData.setVisibility(View.GONE);
                        } else {
                            containerNoData.setVisibility(View.VISIBLE);
                        }
                    }
                });
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

    private void finishLoadmore() {
        if (finishedLoadmore) {
            if (refreshLayout.isLoading()) {
                refreshLayout.finishLoadmore(true);
            }
            CommonUtils.hideProgressDialog(dialog);
        }
    }

    private void setAlertData() {
        if (isRefresh) {
            alertLevels.clear();
            alertStations.clear();
            alertReasons.clear();
            alertDates.clear();

            for (int i = 0; i < allAlertBean.getData().size(); i++) {
                alertLevels.add(1);
                alertStations.add(allAlertBean.getData().get(i).getStationName());
                alertReasons.add(allAlertBean.getData().get(i).getComments());
                alertDates.add(allAlertBean.getData().get(i).getStorageTime());
            }
            alertAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < allAlertBean.getData().size(); i++) {
                alertLevels.add(1);
                alertStations.add(alertDates.size(), allAlertBean.getData().get(i).getStationName());
                alertReasons.add(alertDates.size(), allAlertBean.getData().get(i).getComments());
                alertDates.add(alertDates.size(), allAlertBean.getData().get(i).getStorageTime());
            }
            alertAdapter.notifyDataSetChanged();
        }
    }
}
