package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqhop.www.iot.R;
import com.umeng.analytics.MobclickAgent;

/**
 * 站点详情页-调度记录Fragment
 * Created by allen on 2017/7/24.
 */

public class DispatchRecordFragment extends Fragment {

    private String TAG = "DispatchRecordFragment";

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dispatch_record, container, false);
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
}
