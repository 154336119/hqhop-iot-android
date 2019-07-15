package com.hqhop.www.iot.activities.main.statistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.api.statistics.StatisticsService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.ToolbarUtils;
import com.hqhop.www.iot.base.util.WebViewUtils;
import com.hqhop.www.iot.bean.TechnologyFlowBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.umeng.analytics.MobclickAgent;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allen on 10/23/2017.
 */

public class StatisticsFragment extends Fragment {
    private String TAG = "StatisticsFragment";

    private Context mContext;

    private boolean isFirst = true;

    private View rootView;

    private Toolbar toolbar;

    private TextView titleToolbar;

    private SmartRefreshLayout refreshLayout;

    private WebView webView;

    private String url = "";

    private StatisticsService statisticsService;

    private TechnologyFlowBean technologyFlowBean;

    private boolean finishFetchingData = true;

    @SuppressLint("StaticFieldLeak")
    private static StatisticsFragment statisticsFragment;

    public StatisticsFragment() {

    }

    @SuppressLint("ValidFragment")
    private StatisticsFragment(Context context) {
        mContext = context;
    }

    public static StatisticsFragment getInstance(Context context) {
        if (statisticsFragment == null) {
            statisticsFragment = new StatisticsFragment(context);
        }
        return statisticsFragment;
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (rootView == null) {
        rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
//        }

        toolbar = rootView.findViewById(R.id.toolbar);
        titleToolbar = rootView.findViewById(R.id.title_toolbar);
        setupToolbar();

        refreshLayout = rootView.findViewById(R.id.refresh_layout_fragment_statistics);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(mContext).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(mContext));
        // 刷新监听
        refreshLayout.setOnRefreshListener(refreshLayout -> fetchData());

        webView = rootView.findViewById(R.id.webview_fragment_statistics);
        WebViewUtils.setWebViewStyles(webView);

        statisticsService = RetrofitManager.getInstance(mContext).createService(StatisticsService.class);

        return rootView;
    }

    private void fetchData() {
        finishFetchingData = false;
        statisticsService.getBusinessData()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TechnologyFlowBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        finishFetchingData = true;
                        CommonUtils.showToast(getString(R.string.network_error));
                        finishRefresh();
                        WebViewUtils.loadNoDataPage(webView);
                    }

                    @Override
                    public void onNext(TechnologyFlowBean bean) {
                        finishFetchingData = true;
                        finishRefresh();
                        technologyFlowBean = bean;
                        if (bean.isSuccess()) {
                            setData();
                        }
                    }
                });
    }

    private void setData() {
        if (technologyFlowBean.getData().getUrl().length() > 0) {
            url = Common.BASE_URL + technologyFlowBean.getData().getUrl() + "?userid=" + App.userid + "&access_token=" + App.token;
            webView.loadUrl(url);
        } else {
            WebViewUtils.loadNoDataPage(webView);
        }
    }

    private void finishRefresh() {
        if (refreshLayout != null && refreshLayout.isRefreshing() && finishFetchingData) {
            refreshLayout.finishRefresh();
        }
    }
    private void setupToolbar() {
        ToolbarUtils.setCustomToolbar(mContext, toolbar);
        ToolbarUtils.setTitle(titleToolbar, getString(R.string.title_statistics));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            fetchData();
//            webView.loadUrl("file:///android_asset/test.html");
        }
        if (isVisibleToUser) {
            MobclickAgent.onPageStart(TAG);
        } else {
            MobclickAgent.onPageEnd(TAG);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        statisticsFragment = null;
    }

    public void destroySelf() {
        statisticsFragment = null;
    }
}
