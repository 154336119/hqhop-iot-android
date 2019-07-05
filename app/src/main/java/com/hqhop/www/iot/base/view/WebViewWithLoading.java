package com.hqhop.www.iot.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hqhop.www.iot.activities.main.workbench.alert.detail.DetailAlertActivity;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.base.util.CommonUtils;

/**
 * Created by allen on 10/30/2017.
 */

public class WebViewWithLoading extends WebView {

    private ProgressBar progressBar;

    private JsInvokeAndroid jsInvokeAndroid;

    private Context context;

    private OnLoadFinishListener onLoadFinishListener;

    public WebViewWithLoading(Context context) {
        this(context, null);
    }

    public WebViewWithLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public WebViewWithLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        // 获取屏幕宽高
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        if (progressBar == null) {
            progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(width / 2, height / 2, width / 2, height / 2);
            progressBar.setLayoutParams(layoutParams);
            addView(progressBar);
        }
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setBlockNetworkImage(false);
        setWebChromeClient(new CustonWebChromeClient());
        setWebViewClient(new WebViewClient());
        jsInvokeAndroid = new JsInvokeAndroid(context);
        addJavascriptInterface(jsInvokeAndroid, "JavaScriptInterface");
    }

    public class CustonWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
                if (onLoadFinishListener != null) {
                    onLoadFinishListener.onLoadFinish();
                }
            } else {
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    /**
     * js调用java方法
     */
    private class JsInvokeAndroid {
        private Context context;

        JsInvokeAndroid(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void invokeNative(String type, String content) {
            switch (type) {
                case "alert":
                    new MaterialDialog.Builder(context)
                            .title("提示")
                            .content(content)
                            .positiveText("Ok")
                            .onPositive((dialog, which) -> dialog.dismiss())
                            .show();
                    break;
                case "toast":
                    CommonUtils.showToast(content);
                    break;
                case "station":
                    gotoDetailStation(content);
                    break;
                case "alarm":
                    gotoDetailAlarm(content);
                    break;
                case "finish":
                    finish();
                    break;
                default:
                    break;
            }
        }

        private void gotoDetailStation(String stationId) {
            if (!TextUtils.isEmpty(stationId.trim())) {
                try {
                    Intent intent = new Intent(context, DetailStationActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra("id", stationId.trim());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (Exception e) {
                    CommonUtils.showToast("参数有误");
                    e.printStackTrace();
                    Log.e("Allen", "exception: " + e.getMessage());
                }
            }
        }

        private void gotoDetailAlarm(String content) {
            if (!TextUtils.isEmpty(content.trim())) {
                try {
                    String[] args = content.split(",");
                    String stationId = args[0].trim();
                    String parameterId = args[1].trim();

                    Intent detailAlertIntent = new Intent(context, DetailAlertActivity.class);
                    detailAlertIntent.putExtra("stationId", stationId);
                    detailAlertIntent.putExtra("parameterId", parameterId);
                    detailAlertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(detailAlertIntent);
                } catch (Exception e) {
                    CommonUtils.showToast("参数有误");
                    e.printStackTrace();
                    Log.e("Allen", "exception: " + e.getMessage());
                }
            }
        }

        private void finish() {
            ((Activity) context).finish();
        }
    }

    public void setOnLoadFinishListener(OnLoadFinishListener onLoadFinishListener) {
        this.onLoadFinishListener = onLoadFinishListener;
    }

    public interface OnLoadFinishListener {
        void onLoadFinish();
    }
}
