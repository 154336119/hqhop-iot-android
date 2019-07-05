package com.hqhop.www.iot.activities.main.workbench.alert.detail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.api.workbench.alert.AlertService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.base.view.dashboard.view.DashboardView;
import com.hqhop.www.iot.bean.AlertImageBean;
import com.hqhop.www.iot.bean.AlertInformationBean;
import com.hqhop.www.iot.bean.AlertTestBean;
import com.hqhop.www.iot.bean.AllAlertBean;
import com.hqhop.www.iot.bean.WorkbenchBean;

import java.io.File;

import retrofit2.http.GET;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailAlertActivity extends BaseAppCompatActivity {

    private ImageView ivAlert;

    private DashboardView dashboardView;

    private TextView tvStation, tvReason, tvValue, tvDate, tvDevice;

    private Intent gotIntent;

    private AllAlertBean.DataBean dataBean;

    private AlertService alertService;

    private String stationId, parameterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setToolBarTitle("报警详情");

        alertService = RetrofitManager.getInstance(this).createService(AlertService.class);

        ivAlert = findViewById(R.id.iv_alert_detail_alert);
        dashboardView = findViewById(R.id.dashboard_detail_alert);
        tvStation = findViewById(R.id.tv_station_detail_alert);
        tvReason = findViewById(R.id.tv_reason_detail_alert);
        tvValue = findViewById(R.id.tv_value_detail_alert);
        tvDate = findViewById(R.id.tv_date_detail_alert);
        tvDevice = findViewById(R.id.tv_device_detail_alert);

        gotIntent = getIntent();
        dataBean = gotIntent.getParcelableExtra(Common.BUNDLE_ALERT);
        stationId = gotIntent.getStringExtra("stationId");
        parameterId = gotIntent.getStringExtra("parameterId");
        if (TextUtils.isEmpty(stationId) || TextUtils.isEmpty(parameterId)) {
            if (dataBean != null) {
                tvStation.setText(dataBean.getStationName());
                tvReason.setText(dataBean.getComments());
                tvValue.setText("最大值：" + dataBean.getMax() + "，最小值：" + dataBean.getMin());
                tvDate.setText(dataBean.getStorageTime());
                tvDevice.setText(dataBean.getEquipmentName());
                fetchImage(dataBean.getParameterId());
            }
        } else {
            alertService.getAlertInformationByParameterId(stationId, parameterId)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<AlertInformationBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(AlertInformationBean bean) {
                            tvStation.setText(bean.getData().get(0).getStationName());
                            tvReason.setText(bean.getData().get(0).getComments());
                            tvValue.setText("最大值：" + bean.getData().get(0).getMax() + "，最小值：" + bean.getData().get(0).getMin());
                            tvDate.setText(bean.getData().get(0).getStorageTime());
                            tvDevice.setText(bean.getData().get(0).getEquipmentName());

                            setDashboardDataByParameterId(bean);
                        }
                    });
        }
    }

    private void setDashboardData() {
        dashboardView.setVisibility(View.VISIBLE);
        dashboardView.setUnit(dataBean.getUnit());
        dashboardView.setText(dataBean.getComments());
        dashboardView.setMaxNum(dataBean.getMax());
        dashboardView.setStartNum(dataBean.getMin());
        int diff = dataBean.getMax() - dataBean.getMin();
        float div = diff / 5.0F;
        String[] tikes = new String[6];
        for (int i = 0; i < 6; i++) {
            if (i != 5) {
                tikes[i] = String.format("%.1f", dataBean.getMin() * 1.0 + div * i);
            } else {
                tikes[i] = String.format("%.1f", dataBean.getMax() * 1.0);
            }
        }
        dashboardView.setTikeStrArray(tikes);
        dashboardView.setPercent(Float.parseFloat(dataBean.getValue()) / dataBean.getMax() * 100);
    }

    private void setDashboardDataByParameterId(AlertInformationBean bean) {
        dashboardView.setVisibility(View.VISIBLE);
        dashboardView.setUnit(bean.getData().get(0).getUnit());
        dashboardView.setText(bean.getData().get(0).getComments());
        dashboardView.setMaxNum(bean.getData().get(0).getMax());
        dashboardView.setStartNum(bean.getData().get(0).getMin());
        float diff = bean.getData().get(0).getMax() - bean.getData().get(0).getMin();
        float div = diff / 5.0F;
        String[] tikes = new String[6];
        for (int i = 0; i < 6; i++) {
            if (i != 5) {
                tikes[i] = String.format("%.1f", bean.getData().get(0).getMin() * 1.0 + div * i);
            } else {
                tikes[i] = String.format("%.1f", bean.getData().get(0).getMax() * 1.0);
            }
        }
        dashboardView.setTikeStrArray(tikes);
        dashboardView.setPercent(Float.parseFloat(bean.getData().get(0).getValue()) / (bean.getData().get(0).getMax() - bean.getData().get(0).getMin()) * 100);
    }

    private void fetchImage(String parameterId) {
        alertService.getAlertImage(parameterId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AlertImageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        setDashboardData();
                    }

                    @Override
                    public void onNext(AlertImageBean bean) {
                        if (bean.isSuccess() && bean.getData() != null && !TextUtils.isEmpty(bean.getData())) {
                            Log.d("test", Common.BASE_URL + "images/" + bean.getData());
                            ivAlert.setVisibility(View.VISIBLE);
                            // 加载图片
                            Glide.with(DetailAlertActivity.this)
                                    .load(Common.BASE_URL + "images/" + bean.getData())
                                    .into(ivAlert);
                        } else {
                            setDashboardData();
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_alert;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }
}
