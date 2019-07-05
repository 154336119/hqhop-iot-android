package com.hqhop.www.iot.activities.login.register;

import android.app.Activity;
import android.os.Bundle;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.view.WebViewWithLoading;

public class WebActivity extends Activity {

    private WebViewWithLoading webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.webview_web);

        String url = "template/knowledge/Knowledge.html";
        webView.loadUrl(Common.BASE_URL + url + "&access_token=" + App.token);
    }
}
