package com.hqhop.www.iot.activities.main.workbench.maintain.all;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.maintain.detail.DetailMaintainActivity;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.MaintainGridViewAdapter;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.MaintainTestBean;

import java.util.ArrayList;
import java.util.List;

public class AllMaintainActivity extends BaseAppCompatActivity {

    private GridView gridViewMaintain;

    private MaintainGridViewAdapter adapterMaintain;

    private List<String> levels;

    private List<String> stations;

    private List<String> deadlines;

    private List<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setToolBarTitle("维保信息");

        gridViewMaintain = (GridView) findViewById(R.id.gridview_maintain_all_maintain);
        levels = new ArrayList<>();
        stations = new ArrayList<>();
        deadlines = new ArrayList<>();
        dates = new ArrayList<>();
        adapterMaintain = new MaintainGridViewAdapter(this, levels, stations, deadlines, dates);
        gridViewMaintain.setAdapter(adapterMaintain);

        gridViewMaintain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Intent detailMaintainIntent = new Intent(AllMaintainActivity.this, DetailMaintainActivity.class);
                Bundle bundle = new Bundle();
                if (position % 2 == 0) {
                    MaintainTestBean maintainTestBean = new MaintainTestBean("0", "成都市龙泉CNG站", "秦中正", "18696914606",
                            "No any reason here", "危急", "已保修", "2017-06-30", "2017-06-29", "2017-06-29", "苟利国家生死以");
                    bundle.putParcelable(Common.BUNDLE_MAINTAIN, maintainTestBean);
                } else {
                    MaintainTestBean maintainTestBean = new MaintainTestBean("1", "成都市龙泉CNG站", "秦中正", "18696914606",
                            "No any reason here", "高危", "需保修", "2017-06-30", "", "", "苟利国家生死以");
                    bundle.putParcelable(Common.BUNDLE_MAINTAIN, maintainTestBean);

                }
                detailMaintainIntent.putExtras(bundle);
                startActivity(detailMaintainIntent);
            }
        });

        getData();
    }

    private void getData() {
        for (int i = 0; i < 10; i++) {
            levels.add("1");
            stations.add("龙泉站");
            deadlines.add("2017-7-11");
            dates.add("2017-06-09 13:28");
        }
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_maintain;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_all_maintain, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_submit_all_setting:
                CommonUtils.showToast("全部报修");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
