package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.polling.PollingContentActivity;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.umeng.analytics.MobclickAgent;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * 站点详情页-巡检记录Fragment
 * Created by allen on 2017/7/24.
 */

public class PollingRecordFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private String TAG = "PollingRecordFragment";

    private View rootView;

    private SegmentedGroup segmentedGroup;

    private RadioButton radioToday, radioWeek, radioMonth, radioQuarter, radioYear, radioHistory;

    private Button test;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_polling_record, container, false);

        segmentedGroup = rootView.findViewById(R.id.segmented_group_polling_record);
        radioToday = rootView.findViewById(R.id.radio_today_polling_record);
        radioWeek = rootView.findViewById(R.id.radio_week_polling_record);
        radioMonth = rootView.findViewById(R.id.radio_month_polling_record);
        radioQuarter = rootView.findViewById(R.id.radio_quarter_polling_record);
        radioYear = rootView.findViewById(R.id.radio_year_polling_record);
        radioHistory = rootView.findViewById(R.id.radio_history_polling_record);
        test = rootView.findViewById(R.id.btn_test_polling_record);
        test.setOnClickListener(view -> {
            Intent pollingIntent = new Intent(getActivity(), PollingContentActivity.class);
            startActivity(pollingIntent);
        });

        segmentedGroup.setOnCheckedChangeListener(this);

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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.radio_today_polling_record:
                CommonUtils.showToast("今日计划");
                break;
            case R.id.radio_week_polling_record:
                CommonUtils.showToast("周");
                break;
            case R.id.radio_month_polling_record:
                CommonUtils.showToast("月");
                break;
            case R.id.radio_quarter_polling_record:
                CommonUtils.showToast("季");
                break;
            case R.id.radio_year_polling_record:
                CommonUtils.showToast("年");
                break;
            case R.id.radio_history_polling_record:
                CommonUtils.showToast("历史");
                break;
            default:
                break;
        }
    }
}
