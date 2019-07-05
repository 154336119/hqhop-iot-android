package com.hqhop.www.iot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.Utils;
import com.hqhop.www.iot.api.AppService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.bean.OperationCountBean;
import com.hqhop.www.iot.bean.UpdateDataBean;
import com.hqhop.www.iot.bean.UserInfoBean;
import com.huamaitel.api.HMDefines;
import com.huamaitel.api.HMJniInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allen on 2017/8/14.
 */

public class App extends MultiDexApplication {

    private int mFinalCount;

    public static String LANGUAGE = "en";

    /**
     * 设备唯一编号
     */
    public static String DEVICE_ID;

    /**
     * 登陆返回的access_token
     */
    public static String token;

    /**
     * 登陆返回的UserId
     */
    public static String userid;

    /**
     * 登陆返回的用户名
     */
    public static String userName;

    public static String longitude = "";

    public static String latitude = "";

    public static boolean hasLogin;

    public static UserInfoBean userInfoBean;

    public static int mUserId = 0;
    public static int mVideoHandle = 0;
    public static int mAudioHandle = 0;
    public static int mTalkHandle = 0;
    public static int mAlarmHandle = 0;
    public static int mRecordHandle = 0;
    public static byte[] mCapputureHandle = null;
    public static int mLanSearchHandle = 0;
    public static HMDefines.DeviceInfo mDeviceInfo = null;
    public static HMDefines.ChannelCapacity mChannelCapacity[] = null;
    public static int serverId = 0;
    public static int treeId = 0;
    public static int userId = 0;
    public static int curNodeHandle = 0;
    public static int curNodeChannel;
    public static HMDefines.DeviceInfo deviceInfo = null;
    public static List<Integer> rootList;
    private static HMJniInterface jni = null;
    public String mRecordPath = ""; // The path of video record file.
    public static String mCapturePath = ""; // The path of captured picture file.
    public static String mLoginServerError = ""; // The error message of login sever.
    public static boolean mIsUserLogin = true; // Is IsUserLogin from intent
    public static final String NODE_ID = "nodeId";
    public static final String CHANNEL = "channel";
    public static final String VIDEO_STREAM = "video_stream";

    private AppService appService;

    private ProgressDialog progressDialog;

    // 存放路径
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/";

    // 下载应用存放全路径
    private static final String FILE_NAME = FILE_PATH + "update.apk";

    // apk下载url
    private String apk_path;

    // 准备安装新版本应用标记
    private static final int INSTALL_TOKEN = 1;

    @SuppressLint("HardwareIds")
    @Override
    public void onCreate() {
        super.onCreate();
        rootList = new ArrayList<>();
        Utils.init(this);
        // 支付宝沙箱
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);

//        Stetho.initializeWithDefaults(this);

//        buglyConfiguration();

        CommonUtils.initUMeng(this);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            DEVICE_ID = tm.getDeviceId();
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                mFinalCount += 1;
                if (mFinalCount == 1 && hasLogin) {
                    Log.d("test", "后台 -> 前台");
                    if (appService == null) {
                        appService = RetrofitManager.getInstance(getApplicationContext()).createService(AppService.class);
                    }
                    if (appService != null && App.userName != null) {
                        appService.appOperationCount(App.userName, App.DEVICE_ID, "android", App.longitude, App.latitude, String.valueOf(BuildConfig.VERSION_CODE))
                                .subscribeOn(Schedulers.io())
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<OperationCountBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("Allen", e.getMessage());
                                    }

                                    @Override
                                    public void onNext(OperationCountBean bean) {
                                        if (bean.getData().getData().size() > 0) {
                                            if (bean.getData().getData().get(0).getCategory().equals("notice")) {
                                                MaterialDialog.Builder builder = new MaterialDialog.Builder(getApplicationContext());
                                                builder.title(bean.getData().getData().get(0).getNoticeTitle())
                                                        .content(bean.getData().getData().get(0).getContent())
                                                        .positiveText("Ok")
                                                        .onPositive((dialog, which) -> dialog.dismiss());
                                                MaterialDialog dialog = builder.build();
                                                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                                dialog.show();
                                                progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                            } else if (bean.getData().getData().get(0).getCategory().equals("update")) {
                                                MaterialDialog.Builder builder = new MaterialDialog.Builder(getApplicationContext());
                                                builder.title(bean.getData().getData().get(0).getNoticeTitle())
                                                        .content(bean.getData().getData().get(0).getContent())
                                                        .positiveText("Ok")
                                                        .cancelable(false)
                                                        .canceledOnTouchOutside(false)
                                                        .onPositive((dialog, which) -> {
                                                            appService.checkForUpdate(BuildConfig.VERSION_CODE)
                                                                    .subscribeOn(Schedulers.io())
                                                                    .subscribeOn(AndroidSchedulers.mainThread())
                                                                    .observeOn(AndroidSchedulers.mainThread())
                                                                    .subscribe(new Subscriber<UpdateDataBean>() {
                                                                        @Override
                                                                        public void onCompleted() {

                                                                        }

                                                                        @Override
                                                                        public void onError(Throwable e) {
                                                                        }

                                                                        @Override
                                                                        public void onNext(UpdateDataBean bean) {
                                                                            if (bean.isSuccess()) {
                                                                                if (bean.getData().getIsUpgrade() == 1) {
//                                                                                    showUpdateDialog(bean.getData().getExplain(), bean.getData().getUrl());
                                                                                    apk_path = Common.BASE_URL + bean.getData().getUrl();
                                                                                    showPromptDialog();
                                                                                } else {
                                                                                    CommonUtils.showToast(getString(R.string.no_update_available));
                                                                                }
                                                                            } else {
                                                                                CommonUtils.showToast(bean.getMessage());
                                                                            }
                                                                        }
                                                                    });
                                                        });
                                                MaterialDialog dialog = builder.build();
                                                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                                dialog.show();
                                            }
                                        }
                                    }
                                });
                    }
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                mFinalCount -= 1;
                if (mFinalCount == 0) {
                    Log.d("test", "前台 -> 后台");
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static HMJniInterface getJni() {
        if (null == jni) {
            jni = new HMJniInterface();
        }
        return jni;
    }

    private void showPromptDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getApplicationContext());
        builder.title("新版本上线")
                .content("是否升级？")
                .positiveText("立即升级")
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .onPositive((dialog, which) -> showDownloadDialog());
        MaterialDialog dialog = builder.build();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    private void showDownloadDialog() {
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setTitle(getString(R.string.downloading));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
        downloadAsyncTask.execute();

        progressDialog.setOnCancelListener(dialogInterface -> {
            dialogInterface.dismiss();
            downloadAsyncTask.cancel(true);
        });
    }

    private class DownloadAsyncTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            URL url;
            HttpURLConnection connection = null;
            InputStream in = null;
            FileOutputStream out = null;
            try {
                url = new URL(apk_path);
                connection = (HttpURLConnection) url.openConnection();
                in = connection.getInputStream();
                long fileLength = connection.getContentLength();
                File file_path = new File(FILE_PATH);
                if (!file_path.exists()) {
                    file_path.mkdir();
                }
                out = new FileOutputStream(new File(FILE_NAME));//为指定的文件路径创建文件输出流
                byte[] buffer = new byte[1024 * 1024];
                int len = 0;
                long readLength = 0;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);//从buffer的第0位开始读取len长度的字节到输出流
                    readLength += len;
                    int curProgress = (int) (((float) readLength / fileLength) * 100);
                    publishProgress(curProgress);
                    if (readLength >= fileLength) {
                        break;
                    }
                }
                out.flush();
                return INSTALL_TOKEN;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
//            Log.e(TAG, "异步更新进度接收到的值：" + values[0]);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressDialog.dismiss();//关闭进度条
            //安装应用
            installApp();
        }
    }

    private void installApp() {
        File appFile = new File(Environment.getExternalStorageDirectory() + "/update.apk");
        if (!appFile.exists()) {
            return;
        }
        // 跳转到新版本应用安装页面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + appFile.toString()), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

//    /**
//     * 配置Bugly，增加上报进程控制
//     */
//    private void buglyConfiguration() {
//        Context context = getApplicationContext();
//        // 获取当前包名
//        String packageName = context.getPackageName();
//        // 获取当前进程名
//        String processName = CommonUtils.getProcessName(android.os.Process.myPid());
//        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        // 初始化Bugly
//        CrashReport.initCrashReport(context);
//    }
}
