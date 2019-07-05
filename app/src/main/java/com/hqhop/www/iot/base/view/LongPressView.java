package com.hqhop.www.iot.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by allen on 2017/9/11.
 */

@SuppressLint("AppCompatCustomView")
public class LongPressView extends ImageView {
    public LongPressView(Context context) {
        this(context, null);
    }

    public LongPressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LongPressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLongPressRunnable = () -> {
            mCounter--;
            //计数器大于0，说明当前执行的Runnable不是最后一次down产生的。
            if (mCounter > 0 || isReleased || isMoved) return;
            performLongClick();
        };
    }

    private int mLastMotionX, mLastMotionY;
    //是否移动了
    private boolean isMoved;
    //是否释放了
    private boolean isReleased;
    //计数器，防止多次点击导致最后一次形成longpress的时间变短
    private int mCounter;
    //长按的runnable
    private Runnable mLongPressRunnable;
    //移动的阈值
    private static final int TOUCH_SLOP = 20;

    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                mCounter++;
                isReleased = false;
                isMoved = false;
                postDelayed(mLongPressRunnable, 7000);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMoved) break;
                if (Math.abs(mLastMotionX - x) > TOUCH_SLOP
                        || Math.abs(mLastMotionY - y) > TOUCH_SLOP) {
                    //移动超过阈值，则表示移动了
                    isMoved = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //释放了
                isReleased = true;
                break;
        }
        return true;
    }
}
