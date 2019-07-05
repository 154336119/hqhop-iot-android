package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.BuildConfig;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.api.workbench.detail.StationDetailService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.WebViewUtils;
import com.hqhop.www.iot.bean.TechnologyFlowBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TechnologyFlowActivity extends Activity {

    private String TAG = "TechnologyFlowActivity";

    private LinearLayout rootView;

    private SmartRefreshLayout refreshLayout;

//    private TextView titleToolbar;

    private WebView webView;

//    private Dialog dialog;

    private StationDetailService stationDetailService;

    private TechnologyFlowBean technologyFlowBean;

    private String url = Common.HQHP_SITE_URL;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_flow);

//        WindowManager wm = this.getWindowManager();
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();

        rootView = findViewById(R.id.root_view_technology_flow);
//        titleToolbar = findViewById(R.id.title_toolbar);

        refreshLayout = findViewById(R.id.refresh_layout_technology_flow);
        refreshLayout.setOnRefreshListener(refreshLayout -> fetchData());

        webView = findViewById(R.id.web_technology_flow);
        WebViewUtils.setWebViewStyles(webView);
        stationDetailService = RetrofitManager.getInstance(this).createService(StationDetailService.class);

        fetchData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    private void fetchData() {
//        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
        stationDetailService.getTechnologyFlow()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TechnologyFlowBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        CommonUtils.hideProgressDialog(dialog);
                        CommonUtils.showToast(getString(R.string.network_error));
                        WebViewUtils.loadNoDataPage(webView);
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.finishRefresh(true);
                        }
                    }

                    @Override
                    public void onNext(TechnologyFlowBean bean) {
//                        CommonUtils.hideProgressDialog(dialog);
                        technologyFlowBean = bean;
                        if (bean.isSuccess()) {
                            setData();
                        }
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.finishRefresh(true);
                        }
                    }
                });
    }

    private void setData() {
        if (technologyFlowBean.getData().getUrl().length() > 0) {
            url = Common.BASE_URL + technologyFlowBean.getData().getUrl() + "?stationId=" + DetailStationActivity.stationId + "&access_token=" + App.token;
            webView.loadUrl(url);
        } else {
            WebViewUtils.loadNoDataPage(webView);
        }
        if (BuildConfig.DEBUG) Log.d("Allen", "url=" + url);
    }
}

