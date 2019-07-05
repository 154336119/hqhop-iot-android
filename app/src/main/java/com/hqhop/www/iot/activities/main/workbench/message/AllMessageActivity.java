package com.hqhop.www.iot.activities.main.workbench.message;

import android.os.Bundle;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;

public class AllMessageActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setToolBarTitle("全部系统消息");
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_message;
    }
}
