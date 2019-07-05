package com.hqhop.www.iot.activities.main.workbench.notification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.alert.all.AllAlertActivity;
import com.hqhop.www.iot.activities.main.workbench.message.AllMessageActivity;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends BaseAppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notification;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    private void init() {
        setToolBarTitle(getString(R.string.menu_item_notification_text_workbench));
        listView = (ListView) findViewById(R.id.listview_notification);
        titles = new ArrayList<>();
        titles.add("系统消息");
        titles.add("报警消息");
        titles.add("巡检消息");
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.item_notification_list_view, R.id.tv_title_overview, titles));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
//                Intent allMessageIntent = new Intent(this, AllMessageActivity.class);
//                startActivity(allMessageIntent);
                CommonUtils.showToast("系统消息");
                break;
            case 1:
                Intent allAlertIntent = new Intent(this, AllAlertActivity.class);
                startActivity(allAlertIntent);
                break;
            case 2:
                CommonUtils.showToast("巡检消息");
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        super.onBackPressed();
    }
}
