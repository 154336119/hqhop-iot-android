package com.hqhop.www.iot.base.util;

import android.annotation.SuppressLint;
import android.content.Context;

import com.hqhop.www.iot.R;

import java.util.Date;

/**
 * Created by allen on 10/09/2017.
 */

public class TimeUtil {
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static String getTimeFormatText(Context context, Date date) {
        if (mContext == null) {
            mContext = context;
        }
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        int r = 0;
        if (diff > year) {
            r = (int) (diff / year);
            if (r > 1) {
                return r + mContext.getString(R.string.years_ago);
            } else {
                return r + mContext.getString(R.string.year_ago);
            }
        }
        if (diff > month) {
            r = (int) (diff / month);
            if (r > 1) {
                return r + mContext.getString(R.string.months_ago);
            } else {
                return r + mContext.getString(R.string.month_ago);
            }
        }
        if (diff > day) {
            r = (int) (diff / day);
            if (r > 1) {
                return r + mContext.getString(R.string.days_ago);
            } else {
                return r + mContext.getString(R.string.day_ago);
            }
        }
        if (diff > hour) {
            r = (int) (diff / hour);
            if (r > 1) {
                return r + mContext.getString(R.string.hours_ago);
            } else {
                return r + mContext.getString(R.string.hour_ago);
            }
        }
        if (diff > minute) {
            r = (int) (diff / minute);
            if (r > 1) {
                return r + mContext.getString(R.string.minutes_ago);
            } else {
                return r + mContext.getString(R.string.minute_ago);
            }
        }
        return mContext.getString(R.string.just_now);
    }
}
