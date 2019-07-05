package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.bean.TechnologyFlowBean;
import com.just.library.AgentWeb;
import com.umeng.analytics.MobclickAgent;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 站点详情页-工艺流程Fragment
 * Created by allen on 2017/7/24.
 */

public class TechnologyFlowFragment extends Fragment {

    private String TAG = "TechnologyFlowFragment";

    private View rootView;

    private TextView tvRefresh;

    private AgentWeb agentWeb;

    private AgentWeb.PreAgentWeb preAgentWeb;

    private DetailStationActivity context;

    private TechnologyFlowBean technologyFlowBean;

    private String url = Common.HQHP_SITE_URL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_technology_flow, container, false);
        tvRefresh = rootView.findViewById(R.id.tv_refresh_technology_flow);

        preAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((ViewGroup) rootView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .setIndicatorColorWithHeight(-1, 2)//
                .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready();
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData();
//                agentWeb = preAgentWeb.go("http://www.hqhop.com/");// test
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = (DetailStationActivity) getActivity();

//        agentWeb = AgentWeb.with(this)
//                .setAgentWebParent((ViewGroup) rootView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
//                .setIndicatorColorWithHeight(-1, 2)//
//                .setSecurityType(AgentWeb.SecurityType.strict)
//                .createAgentWeb()
//                .ready()
//                .go(url);

        fetchData();
    }

    @Override
    public void onPause() {
        if (agentWeb != null) {
            agentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onResume() {
        if (agentWeb != null) {
            agentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onDestroyView() {
        if (agentWeb != null) {
            agentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroyView();
    }

    private void fetchData() {
        context.stationDetailService.getTechnologyFlow()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TechnologyFlowBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.showToast("网络错误");
                    }

                    @Override
                    public void onNext(TechnologyFlowBean bean) {
                        technologyFlowBean = bean;
                        if (bean.isSuccess()) {
                            setData();
                        }
                    }
                });
    }

    private void setData() {
        if (technologyFlowBean.getData().getUrl().length() > 0) {
            url = Common.BASE_URL + technologyFlowBean.getData().getUrl();
        }
        agentWeb = preAgentWeb.go(url);
    }
}
