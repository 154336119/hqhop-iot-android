package com.hqhop.www.iot.activities.main.workbench.station.detail.modules.polling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;

public class PollingContentActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private MapView mapView;

    private TextView tvDate, tvStation, tvStation2, tvChangeLocation, tvContent, tvTime;

    private Spinner spinnerContent;

    private RelativeLayout containerComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        setToolBarTitle("巡检内容");

        mapView = (MapView) findViewById(R.id.map_polling_content);
        mapView.onCreate(savedInstanceState);

        tvDate = (TextView) findViewById(R.id.tv_date_polling_content);
        tvStation = (TextView) findViewById(R.id.tv_station_polling_content);
        tvStation2 = (TextView) findViewById(R.id.tv_station2_polling_content);
        tvChangeLocation = (TextView) findViewById(R.id.tv_change_location_polling_content);
        tvContent = (TextView) findViewById(R.id.tv_content_polling_content);
        tvTime = (TextView) findViewById(R.id.tv_time_polling_content);
        spinnerContent = (Spinner) findViewById(R.id.spinner_content_polling_content);
        containerComplete = (RelativeLayout) findViewById(R.id.container_complete_polling_content);

        tvChangeLocation.setOnClickListener(this);
        containerComplete.setOnClickListener(this);

        setData();
    }

    private void setData() {
        tvDate.setText("2017年07月10日");
        tvStation.setText("成都市龙泉驿CNG站");
        tvStation2.setText("成都市龙泉驿CNG站");
        tvContent.setText("具体巡检内容");
        tvTime.setText("14:07");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_polling_content;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_change_location_polling_content:
                Intent tuneIntent = new Intent(this, LocationTuneActivity.class);
                startActivity(tuneIntent);
                break;
            case R.id.container_complete_polling_content:
                Intent recordIntent = new Intent(this, PollingRecordActivity.class);
                recordIntent.putExtra(Common.POLLING_TIME, "20:33");
                recordIntent.putExtra(Common.POLLING_ADDRESS, "成都市龙泉驿区大面铺");
                startActivity(recordIntent);
                break;
            default:
                break;
        }
    }
}
