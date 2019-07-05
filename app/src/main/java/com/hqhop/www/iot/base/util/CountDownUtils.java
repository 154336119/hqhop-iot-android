package com.hqhop.www.iot.base.util;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.hqhop.www.iot.R;

/**
 * Created by allen on 2017/7/5.
 */

public class CountDownUtils extends CountDownTimer {
    private TextView mTextView;

    public CountDownUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);
        mTextView.setText(millisUntilFinished / 1000 + "s");
//        mTextView.setBackgroundResource(R.drawable.shape_btn_verification_code_disable);

//        SpannableString spannableString = new SpannableString(mTextView.getText().toString());  //获取按钮上的文字
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.WHITE);
//        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
//        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText(R.string.get_verification_code_again);
//        mTextView.setTextColor(Color.WHITE);
        mTextView.setClickable(true);
//        mTextView.setBackgroundResource(R.drawable.shape_btn_verification_code);
    }
}
