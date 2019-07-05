package com.hqhop.www.iot.activities.main.map;

import android.content.Intent;
import android.os.Bundle;

import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.view.NaviBaseActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class AMapNavigationActivity extends NaviBaseActivity {

    private String TAG = "NaviActivity";

    private List<NaviLatLng> startList, endList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap_navigation);

        mAMapNaviView = findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);

        Intent gotIntent = getIntent();
//        mStartLatlng = new NaviLatLng(gotIntent.getDoubleExtra("lat1", 30.6728178), gotIntent.getDoubleExtra("lon1", 103.889792));
//        mEndLatlng = new NaviLatLng(gotIntent.getDoubleExtra("lat2", 29.5551377), gotIntent.getDoubleExtra("lon2", 106.4084699));
        startList = new ArrayList<>();
        endList = new ArrayList<>();
        startList.add(new NaviLatLng(gotIntent.getDoubleExtra("lat1", 30.6728178), gotIntent.getDoubleExtra("lon1", 103.889792)));
        endList.add(new NaviLatLng(gotIntent.getDoubleExtra("lat2", 29.5551377), gotIntent.getDoubleExtra("lon2", 106.4084699)));
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

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        int strategy = 0;
        try {
            // 最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mAMapNavi.setCarNumber("京", "DFZ588");
//        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
        mAMapNavi.calculateDriveRoute(startList, endList, mWayPointList, strategy);
    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
