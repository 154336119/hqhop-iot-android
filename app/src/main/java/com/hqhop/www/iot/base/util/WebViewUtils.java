package com.hqhop.www.iot.base.util;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by allen on 10/30/2017.
 */

public class WebViewUtils {
    @SuppressLint("SetJavaScriptEnabled")
    public static void setWebViewStyles(WebView webView) {
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);// 允许js运行
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportZoom(true);// 允许缩放
        webView.getSettings().setBuiltInZoomControls(false);// 出现缩放按钮
        webView.getSettings().setUseWideViewPort(true);// 允许扩大比例的缩放
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 自适应
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    public static void loadNoDataPage(WebView webView) {
        webView.loadUrl("file:///android_asset/nodata.html");
    }
}
