package com.hqhop.www.iot.activities.main.workbench.message;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.api.workbench.MessageService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.WebViewUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.TechnologyFlowBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessageWebActivity extends BaseAppCompatActivity {

    private MessageService messageService;

    private SmartRefreshLayout refreshLayout;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_web;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        setToolBarTitle("类型列表");

        refreshLayout = findViewById(R.id.refresh_layout_message_web);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(this));
        // 刷新监听
        refreshLayout.setOnRefreshListener(refreshLayout -> getUrl());

        webView = findViewById(R.id.web_view_message_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("test", "start: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    setToolBarTitle(title);
                    Log.d("test", "finish: " + url);
                }
            }
        });
        WebViewUtils.setWebViewStyles(webView);
//        webView.loadUrl("file:///android_asset/test.html");

        messageService = RetrofitManager.getInstance(this).createService(MessageService.class);

        refreshLayout.autoRefresh();
    }

    private void getUrl() {
        messageService.getMessageUrl()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TechnologyFlowBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.finishRefresh();
//                        finishFetchingData = true;
                        CommonUtils.showToast(getString(R.string.network_error));
//                        finishRefresh();
                        WebViewUtils.loadNoDataPage(webView);
                    }

                    @Override
                    public void onNext(TechnologyFlowBean bean) {
                        refreshLayout.finishRefresh();
//                        finishFetchingData = true;
//                        finishRefresh();
//                        technologyFlowBean = bean;
                        if (bean.isSuccess()) {
                            if (bean.getData().getUrl().length() > 0) {
                                webView.loadUrl(Common.BASE_URL + bean.getData().getUrl() + "?userid=" + App.userid + "&access_token=" + App.token);
                            } else {
                                WebViewUtils.loadNoDataPage(webView);
                            }
                        } else {
                            WebViewUtils.loadNoDataPage(webView);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}
