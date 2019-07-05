package com.hqhop.www.iot.activities.main.workbench.station.detail.modules.polling;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.amap.api.maps.MapView;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.adapter.LocationTuneGridViewAdapter;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class LocationTuneActivity extends BaseAppCompatActivity implements MaterialSearchBar.OnSearchActionListener, AdapterView.OnItemClickListener {

    private MaterialSearchBar searchBar;

    private MapView mapView;

    private GridView gridView;

    private LocationTuneGridViewAdapter adapter;

    private List<String> stations;

    private List<String> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        setToolBarTitle("地点微调");

        searchBar = (MaterialSearchBar) findViewById(R.id.search_location_tune);
        searchBar.setOnSearchActionListener(this);

        mapView = (MapView) findViewById(R.id.map_location_tune);
        mapView.onCreate(savedInstanceState);

        gridView = (GridView) findViewById(R.id.gridview_location_tune);
        stations = new ArrayList<>();
        addresses = new ArrayList<>();
        adapter = new LocationTuneGridViewAdapter(this, stations, addresses);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private void getData() {
//        if (stations.size() != 0 && addresses.size() != 0) {
//            gridView.removeAllViews();
//        }
        stations.clear();
        addresses.clear();
        for (int i = 0; i < 10; i++) {
            stations.add("成都市龙泉CNG站");
            addresses.add("成都市龙泉驿区" + i + "路" + i + "号");
        }
        adapter.notifyDataSetChanged();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_location_tune, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_confirm_location_tune:
                CommonUtils.showToast("确定");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location_tune;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        Log.d(TAG, "enabled: " + enabled);
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        CommonUtils.showToast(text);
        getData();
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    /**
     * GridView点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        adapter.setSelected(position);
    }
}
