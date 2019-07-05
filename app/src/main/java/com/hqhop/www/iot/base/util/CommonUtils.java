package com.hqhop.www.iot.base.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.Common;
import com.umeng.analytics.MobclickAgent;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.ComparableComparator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by allen on 2017/7/5.
 */

public class CommonUtils {

    private static TextView tvLoading;

    private static final String TAG = "CommonUtils";

    public static void showSnackbar(View rootView, String message) {
        SnackbarUtils.with(rootView)
                .setMessage(message)
                .setMessageColor(Color.WHITE)
                .setDuration(SnackbarUtils.LENGTH_SHORT)
                .show();
    }

    public static void showProgressDialogWithMessage(Context context, Dialog dialog, @Nullable String message) {
        if (dialog == null) {
            dialog = createLoadingDialog(context);
        }
//        dialog.setMessage(message);
        if (TextUtils.isEmpty(message)) {
            tvLoading.setVisibility(View.GONE);
        } else {
            tvLoading.setText(message);
            tvLoading.setVisibility(View.VISIBLE);
        }
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog(Dialog dialog) {
        if (dialog != null) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void requestPermission(Activity activity, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> temps = new ArrayList<>();
            for (String permission : permissions) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    temps.add(permission);
                }
            }
            if (temps.size() > 0) {
                activity.requestPermissions(temps.toArray(new String[temps.size()]), Common.REQUEST_CODE_PERMISSION);
            }
        }
    }

    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String metaValue = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                metaValue = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "error " + e.getMessage());
        }
        return metaValue;
    }

    public static void setTranslucentStatusBar(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((AppCompatActivity) context).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(context.getResources().getColor(R.color.colorPrimary));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = ((AppCompatActivity) context).getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static int getMaxFromStringList(List<String> list) {
        float max = Float.parseFloat(list.get(0));
        for (int i = 0; i < list.size(); i++) {
            float temp = Float.parseFloat(list.get(i));
            if (max < temp) {
                max = temp;
            }
        }
        return Math.round(max);
    }

    /**
     * 跳转到拨号界面
     */
    public static void dial(Context context, String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        // 直接拨号
//        Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));
//        context.startActivity(intent);
//        <uses-permission android:name="android.permission.CALL_PHONE" />
    }

    public static int getScreenWidth() {
        return ScreenUtils.getScreenWidth();
    }

    public static int getScreenHeight() {
        return ScreenUtils.getScreenHeight();
    }

    public static void showToast(final @StringRes int resId) {
//        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort(resId);
    }

    public static void showToast(final CharSequence text) {
        ToastUtils.showShort(text);
    }

    public static void hideKeyboard(Context context) {
        KeyboardUtils.hideSoftInput((AppCompatActivity) context);
    }

    public static Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_loading, null);
        LinearLayout container = view.findViewById(R.id.container_loading);
        ImageView ivLoading = view.findViewById(R.id.iv_loading);
        tvLoading = view.findViewById(R.id.tv_loading);
        Glide.with(context).load(R.drawable.loading).asGif().into(ivLoading);
        Dialog loadingDialog = new Dialog(context, R.style.Dialog);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(container, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        return loadingDialog;
    }

    /**
     * 将时间戳转换为日期格式
     *
     * @param timestamp 时间戳
     * @return
     */
    public static String timestampToDate(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(timestamp);
    }

    public static String timestampToDate(long str, String formatStyle) {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStyle);
        Date date = new Date(str);
        result = simpleDateFormat.format(date);
        return result;
    }

    public static String timestampToDateWithHour(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(timestamp);
    }

    public static String dateToTimestamp(String str) {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(str);
            long ts = date.getTime();
            result = String.valueOf(ts);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String dateToTimestamp(String str, String format) {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date date = simpleDateFormat.parse(str);
            long ts = date.getTime();
            result = String.valueOf(ts);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static float getMaxValue(List<Float> list) {
        float max = 0.0f;
        for (float temp : list) {
            if (max < temp) {
                max = temp;
            }
        }
        return max;
    }

    public static float getMinValue(List<Float> list) {
        float min = 0.0f;
        for (float temp : list) {
            if (min > temp) {
                min = temp;
            }
        }
        return min;
    }

    /**
     * 获取进程名称
     *
     * @param pid 进程id
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static void initUMeng(Context context) {
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }
}
