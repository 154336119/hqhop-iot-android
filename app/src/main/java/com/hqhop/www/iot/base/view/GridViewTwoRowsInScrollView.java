package com.hqhop.www.iot.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.hqhop.www.iot.R;

/**
 * 1. 解决GridView嵌套在ScrollView中的冲突问题
 * 2. 添加分割线
 * Created by allen on 2017/7/7.
 */

public class GridViewTwoRowsInScrollView extends GridView {

    private boolean isShowDividerLine;

    private float dividerLineWidth;

    public GridViewTwoRowsInScrollView(Context context) {
        this(context, null);
    }

    public GridViewTwoRowsInScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridViewTwoRowsInScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.allen);
        isShowDividerLine = ta.getBoolean(R.styleable.allen_showDividerLine, true);
        dividerLineWidth = ta.getFloat(R.styleable.allen_dividerLineWidth, 3f);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.EXACTLY);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isShowDividerLine) {
            View localView1 = getChildAt(0);
            if (localView1 != null) {
                int column = getWidth() / localView1.getWidth();
                int childCount = getChildCount();
                Paint localPaint;
                localPaint = new Paint();
                localPaint.setStrokeWidth(dividerLineWidth);
                localPaint.setStyle(Paint.Style.STROKE);
                localPaint.setColor(getContext().getResources().getColor(R.color.grayDividerLine));
                for (int i = 0; i < childCount; i++) {
                    View cellView = getChildAt(i);
                    if ((i + 1) % column == 0) {
                        canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
                    } else if ((i + 1) > (childCount - (childCount % column))) {
                        canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
                    } else {
                        canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
                        canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
                    }
                }
                if (childCount % column != 0) {
                    for (int j = 0; j < (column - childCount % column); j++) {
                        View lastView = getChildAt(childCount - 1);
                        canvas.drawLine(lastView.getRight() + lastView.getWidth() * j, lastView.getTop(), lastView.getRight() + lastView.getWidth() * j, lastView.getBottom(), localPaint);
                    }
                }
            }
        }
    }
}
