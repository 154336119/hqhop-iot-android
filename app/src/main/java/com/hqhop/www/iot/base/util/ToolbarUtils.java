package com.hqhop.www.iot.base.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by allen on 2017/7/4.
 */

public class ToolbarUtils {
    /**
     * 设置自定义Toolbar
     *
     * @param context
     * @param toolbar
     */
    public static void setCustomToolbar(Context context, Toolbar toolbar) {
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
    }

    /**
     * 设置自定义Toolbar标题栏
     *
     * @param tvTitle
     * @param title
     */
    public static void setTitle(TextView tvTitle, String title) {
        tvTitle.setText(title);
    }
}
