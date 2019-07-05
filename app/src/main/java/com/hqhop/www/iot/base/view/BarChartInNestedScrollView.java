package com.hqhop.www.iot.base.view;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.blankj.utilcode.util.LogUtils;
import com.github.mikephil.charting.charts.BarChart;

/**
 * 解决NestedScrollView中嵌套BarChart不能上下滑动的问题
 * Created by allen on 2017/7/19.
 */

public class BarChartInNestedScrollView extends BarChart {
    PointF downPoint = new PointF();

    public BarChartInNestedScrollView(Context context) {
        super(context);
    }

    public BarChartInNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChartInNestedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPoint.x = event.getX();
                downPoint.y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i("getScrollX ", getScrollX() + "");
                if (getScaleY() > 1 && Math.abs(event.getY() - downPoint.y) > 5) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
