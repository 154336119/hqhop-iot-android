package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.WebViewUtils;
import com.hqhop.www.iot.base.view.WebViewWithLoading;
import com.hqhop.www.iot.bean.EquipmentMonitorBean;
import com.hqhop.www.iot.bean.MaintainUrlBean;
import com.hqhop.www.iot.bean.PartsBean;
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
 * 站点详情页-设备维保Fragment
 * Created by allen on 2017/7/24.
 */

public class EquipmentMaintainFragment extends Fragment {

    private String TAG = "EquipmentMaintainFragment";

    private View rootView;

    private boolean isFirst = true;

    private DetailStationActivity context;

    private LinearLayout containerSpinner;

    private Spinner spinnerEquipments, spinnerParts;

    private ArrayAdapter<String> spinnerEquipmentAdapter, spinnerPartAdapter;

    private List<String> equipmentNames = new ArrayList<>();

    private List<String> equipmentIds = new ArrayList<>();

    private List<String> partNames = new ArrayList<>();

    private List<String> partIds = new ArrayList<>();

    private SmartRefreshLayout refreshLayout;

    private WebViewWithLoading webView;

    private Button btnMore, btnEmit;

    private String baseUrl = "", moreUrl = "";

    private boolean finishFetchingData = true, canUseSpinner = false;

    private MaintainUrlBean maintainUrlBean;

    private int currentEquipmentIndex = -1, currentPartIndex = -1;

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_equipment_maintain, container, false);

        context = ((DetailStationActivity) getActivity());

        containerSpinner = rootView.findViewById(R.id.container_spinner_equipment_maintain);
        containerSpinner.setVisibility(View.GONE);
        spinnerEquipments = rootView.findViewById(R.id.spinner_equipments_equipment_maintain);
        spinnerParts = rootView.findViewById(R.id.spinner_parts_equipment_maintain);

        spinnerEquipmentAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, equipmentNames);
        spinnerEquipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEquipments.setAdapter(spinnerEquipmentAdapter);

        spinnerPartAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, partNames);
        spinnerPartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParts.setAdapter(spinnerPartAdapter);

        spinnerEquipments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (canUseSpinner) {
                    if (position == 0) {
                        currentEquipmentIndex = -1;
                        // 获取全部设备的url
                        webView.pauseTimers();
                        webView.loadUrl(moreUrl + "&stationId=" + DetailStationActivity.stationId + "&access_token=" + App.token);
                        Log.d("test", "加载" + moreUrl + "&stationId=" + DetailStationActivity.stationId);
                    } else {
                        currentEquipmentIndex = position - 1;
                        fetchParts(currentEquipmentIndex);
                        // 获取单个设备的url
                        webView.pauseTimers();
//                        if (currentEquipmentIndex != -1) {
                        webView.loadUrl(moreUrl + "&equipmentId=" + equipmentIds.get(currentEquipmentIndex) + "&access_token=" + App.token);
//                        } else {
//                            webView.loadUrl(moreUrl + "&stationId=" + DetailStationActivity.stationId);
//                        }
                        Log.d("test", "加载" + moreUrl + "&equipmentId=" + equipmentIds.get(currentEquipmentIndex));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerParts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (canUseSpinner) {
                    if (position == 0) {
                        currentPartIndex = -1;
                        // 获取全部部件的url
                        webView.pauseTimers();
                        if (currentEquipmentIndex != -1) {
                            webView.loadUrl(moreUrl + "&equipmentId=" + equipmentIds.get(currentEquipmentIndex) + "&access_token=" + App.token);
                            Log.d("test", "加载" + moreUrl + "&equipmentId=" + equipmentIds.get(currentEquipmentIndex));
                        } else {
                            webView.loadUrl(moreUrl + "&stationId=" + DetailStationActivity.stationId + "&access_token=" + App.token);
                            Log.d("test", "加载" + moreUrl + "&stationId=" + DetailStationActivity.stationId);
                        }
                    } else {
                        currentPartIndex = position - 1;
                        // 获取单个部件的url
                        webView.pauseTimers();
                        webView.loadUrl(moreUrl + "&partId=" + partIds.get(currentPartIndex) + "&access_token=" + App.token);
                        Log.d("test", "加载" + moreUrl + "&partId=" + partIds.get(currentPartIndex));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        refreshLayout = rootView.findViewById(R.id.refresh_layout_equipment_maintain);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(context).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(context));
        // 刷新监听
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            fetchURL();
            equipmentNames.clear();
            equipmentIds.clear();
            partNames.clear();
            partIds.clear();
            spinnerEquipmentAdapter.notifyDataSetChanged();
            spinnerPartAdapter.notifyDataSetChanged();
            containerSpinner.setVisibility(View.GONE);
            btnMore.setVisibility(View.VISIBLE);
        });

        webView = rootView.findViewById(R.id.webview_equipment_maintain);
        WebViewUtils.setWebViewStyles(webView);

        btnMore = rootView.findViewById(R.id.btn_more_equipment_maintain);
        btnEmit = rootView.findViewById(R.id.btn_emit_equipment_maintain);
        btnMore.setVisibility(View.VISIBLE);
        btnEmit.setVisibility(View.VISIBLE);
        btnMore.setOnClickListener(view -> {
            String url = moreUrl + "&stationId=" + DetailStationActivity.stationId + "&access_token=" + App.token;
            webView.loadUrl(url);
            fetchEquipments();
        });
        btnEmit.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), MaintainSubmitActivity.class));
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

    private void fetchURL() {
        finishFetchingData = false;
        context.stationDetailService.getMaintainUrl()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MaintainUrlBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        finishFetchingData = true;
//                        CommonUtils.showToast("网络错误");
                        WebViewUtils.loadNoDataPage(webView);
                        finishRefresh();
                    }

                    @Override
                    public void onNext(MaintainUrlBean bean) {
                        finishFetchingData = true;
                        finishRefresh();
                        maintainUrlBean = bean;
                        if (bean.isSuccess()) {
                            loadURL();
                        }
                    }
                });
    }

    private void loadURL() {
        if (maintainUrlBean.getData().size() > 0) {
            baseUrl = Common.BASE_URL + maintainUrlBean.getData().get(0).getUrl() + "?access_token=" + App.token;
            moreUrl = Common.BASE_URL + maintainUrlBean.getData().get(1).getUrl() + "?access_token=" + App.token;
            webView.loadUrl(baseUrl + "&stationId=" + DetailStationActivity.stationId);
            Log.d("test", "加载" + baseUrl + "&stationId=" + DetailStationActivity.stationId);
        } else {
            WebViewUtils.loadNoDataPage(webView);
        }
    }

    /**
     * 获取站点所有设备
     */
    private void fetchEquipments() {
        CommonUtils.showProgressDialogWithMessage(context, context.dialog, null);
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
                        canUseSpinner = true;
                        finishRefresh();
                    }

                    @Override
                    public void onNext(EquipmentMonitorBean bean) {
                        finishRefresh();
                        if (bean.isSuccess() && bean.getData().size() > 0) {
                            canUseSpinner = true;

                            equipmentNames.clear();
                            equipmentIds.clear();
                            equipmentNames.add(getString(R.string.all));

                            for (int i = 0; i < bean.getData().size(); i++) {
                                equipmentNames.add(bean.getData().get(i).getEquipName());
                                equipmentIds.add(bean.getData().get(i).getEquipId());
                            }
                            spinnerEquipmentAdapter.notifyDataSetChanged();
                            containerSpinner.setVisibility(View.VISIBLE);
                            btnMore.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void fetchParts(int position) {
        canUseSpinner = false;
        CommonUtils.showProgressDialogWithMessage(context, context.dialog, null);
        context.stationDetailService.getParts(equipmentIds.get(position))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PartsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        canUseSpinner = true;
                        finishRefresh();
                    }

                    @Override
                    public void onNext(PartsBean bean) {
                        finishRefresh();
                        if (bean.isSuccess() && bean.getData().size() > 0) {
                            canUseSpinner = true;

                            partNames.clear();
                            partIds.clear();
                            partNames.add(getString(R.string.all));

                            for (int i = 0; i < bean.getData().size(); i++) {
                                partNames.add(bean.getData().get(i).getName());
                                partIds.add(bean.getData().get(i).getId());
                            }
                            spinnerPartAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            fetchURL();
        }
    }

    private void finishRefresh() {
        if (refreshLayout != null && refreshLayout.isRefreshing() && finishFetchingData) {
            refreshLayout.finishRefresh();
        }
        CommonUtils.hideProgressDialog(context.dialog);
    }
}
