package com.hqhop.www.iot.activities.main.follow.station;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.adapter.FollowExpandableChildGridViewAdapter;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;

import java.util.ArrayList;

public class DetailFollowStationActivity extends BaseAppCompatActivity implements AdapterView.OnItemClickListener {

    private Intent gotIntent;

    private String stationName;

//    private StationTestBean stationTestBean;

//    private String id;

    private GridView gridView;

    private FollowExpandableChildGridViewAdapter adapter;

    private ArrayList<String> titles;

    private ArrayList<String> values;

    private ArrayList<String> units;

    private ArrayList<String> alarms;

//    private ArrayList<String> configIds;

//    private ModulesDetailBeanDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        gotIntent = getIntent();
        stationName = gotIntent.getStringExtra("station");
//        configIds = gotIntent.getStringArrayListExtra("equipmentIds");
        titles = gotIntent.getStringArrayListExtra("titles");
        values = gotIntent.getStringArrayListExtra("values");
        units = gotIntent.getStringArrayListExtra("units");

        setToolBarTitle(stationName);

        gridView = (GridView) findViewById(R.id.gridview_station_detail_follow);
        titles = new ArrayList<>();
        values = new ArrayList<>();
        alarms = new ArrayList<>();
        adapter = new FollowExpandableChildGridViewAdapter(this, titles, values, units, alarms);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        getData();
    }

    private void getData() {
        titles = gotIntent.getStringArrayListExtra("titles");
        values = gotIntent.getStringArrayListExtra("values");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_follow_station;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        Intent oneIntent = new Intent(this, DetailItemActivity.class);
//        oneIntent.putExtra("station", stationName);
//        oneIntent.putExtra("title", titles.get(position));
//        oneIntent.putExtra("value", values.get(position));
//        oneIntent.putExtra("unit", units.get(position));
//        oneIntent.putExtra("equipmentId", configIds.get(position));
//        startActivity(oneIntent);
    }
}
