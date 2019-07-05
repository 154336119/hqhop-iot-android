package com.hqhop.www.iot.activities.main.workbench.maintain.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.MaintainTestBean;

public class DetailMaintainActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private RelativeLayout containerSubmit, containerUrge;

    private TextView tvStation, tvPerson, tvPhone, tvReason, tvEmergency, tvStatus, tvDateMaintain, tvDateSubmit, tvDateUrge, tvRemark;

    private ImageView ivDial;

    private Button btnFunc;

    private Intent gotIntent;

    private MaintainTestBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setToolBarTitle("维保详情");

        containerSubmit = (RelativeLayout) findViewById(R.id.container_date_submit_detail_maintain);
        containerUrge = (RelativeLayout) findViewById(R.id.container_date_urge_detail_maintain);
        tvStation = (TextView) findViewById(R.id.tv_station_detail_maintain);
        tvPerson = (TextView) findViewById(R.id.tv_person_detail_maintain);
        tvPhone = (TextView) findViewById(R.id.tv_phone_detail_maintain);
        tvReason = (TextView) findViewById(R.id.tv_reason_detail_maintain);
        tvEmergency = (TextView) findViewById(R.id.tv_emergency_detail_maintain);
        tvStatus = (TextView) findViewById(R.id.tv_status_detail_maintain);
        tvDateMaintain = (TextView) findViewById(R.id.tv_date_maintain_detail_maintain);
        tvDateSubmit = (TextView) findViewById(R.id.tv_date_submit_detail_maintain);
        tvDateUrge = (TextView) findViewById(R.id.tv_date_urge_detail_maintain);
        tvRemark = (TextView) findViewById(R.id.tv_remark_detail_maintain);
        ivDial = (ImageView) findViewById(R.id.iv_dial_detail_maintain);
        btnFunc = (Button) findViewById(R.id.btn_func_detail_maintain);

        gotIntent = getIntent();
        dataBean = gotIntent.getParcelableExtra(Common.BUNDLE_MAINTAIN);
        if (dataBean.getType().equals("0")) {
            // 已保修状态
            containerSubmit.setVisibility(View.VISIBLE);
            containerUrge.setVisibility(View.VISIBLE);
            btnFunc.setText("取消保修");
        }

        ivDial.setOnClickListener(this);
        btnFunc.setOnClickListener(this);

        setData();
    }

    private void setData() {
        tvStation.setText(dataBean.getStation());
        tvPerson.setText(dataBean.getPerson());
        tvPhone.setText(dataBean.getPhone());
        tvReason.setText(dataBean.getReason());
        tvEmergency.setText(dataBean.getEmergency());
        tvStatus.setText(dataBean.getStatus());
        tvDateMaintain.setText(dataBean.getDateMaintain());
        tvDateSubmit.setText(dataBean.getDateSubmit());
        tvDateUrge.setText(dataBean.getDateUrge());
        tvRemark.setText(dataBean.getRemark());
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_maintain;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_dial_detail_maintain:
                CommonUtils.dial(this, dataBean.getPhone());
                break;
            case R.id.btn_func_detail_maintain:
                if (dataBean.getType().equals("0")) {
                    // 已保修状态，点击取消保修
                    CommonUtils.showToast("取消报修");
                } else {
                    // 为保修状态，点击报修
                    CommonUtils.showToast("报修");
                }
                break;
            default:
                break;
        }
    }
}
