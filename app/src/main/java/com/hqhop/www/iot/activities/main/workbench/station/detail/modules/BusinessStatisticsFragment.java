package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.bean.TechnologyFlowBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.umeng.analytics.MobclickAgent;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 站点详情页-运营统计Fragment
 * Created by allen on 2017/7/24.
 */

public class BusinessStatisticsFragment extends Fragment {

    private String TAG = "BusinessStatisticsFragment";

    private View rootView;

    private DetailStationActivity context;

    private SmartRefreshLayout refreshLayout;

    private WebView webView;

    private String url = "";

    private TechnologyFlowBean technologyFlowBean;

    private boolean finishFetchingData = true;

    private String stationId;
//    private GridView gridView;
//
//    private BusinessGridViewAdapter adapter;
//
//    private List<String> titles;
//
//    private List<String> values;

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_business_statistics, container, false);
        context = ((DetailStationActivity) getActivity());
        stationId = getArguments().getString("stationId");
        refreshLayout = rootView.findViewById(R.id.refresh_layout_business_statistics);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(context).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(context));
        // 刷新监听
        refreshLayout.setOnRefreshListener(refreshLayout -> fetchData());

        webView = rootView.findViewById(R.id.webview_business_statistics);
        webView.getSettings().setJavaScriptEnabled(true);// 允许js运行
        webView.getSettings().setSupportZoom(true);// 允许缩放
        webView.getSettings().setBuiltInZoomControls(true);// 出现缩放按钮
        webView.getSettings().setUseWideViewPort(true);// 允许扩大比例的缩放
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 自适应
        webView.getSettings().setLoadWithOverviewMode(true);

//        gridView = (GridView) rootView.findViewById(R.id.gridview_business_statistics_detail_station);
//        titles = new ArrayList<>();
//        values = new ArrayList<>();
//        adapter = new BusinessGridViewAdapter(getActivity(), titles, values);
//        gridView.setAdapter(adapter);

        fetchData();

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

    private void fetchData() {
        finishFetchingData = false;
        context.stationDetailService.getBusinessData()
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
                        webView.loadUrl(Common.HQHP_SITE_URL);
                        finishRefresh();
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
            url = Common.BASE_URL + technologyFlowBean.getData().getUrl() + "?stationId=" + stationId + "&access_token=" + App.token;
        }
        webView.loadUrl(url);
    }

    private void finishRefresh() {
        if (refreshLayout != null && refreshLayout.isRefreshing() && finishFetchingData) {
            refreshLayout.finishRefresh();
        }
    }
}
