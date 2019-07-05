package com.hqhop.www.iot.activities.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.hqhop.www.iot.App;
import com.hqhop.www.iot.BuildConfig;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.about.AboutFragment;
import com.hqhop.www.iot.activities.main.follow.FollowFragment;
import com.hqhop.www.iot.activities.main.map.MapFragment;
import com.hqhop.www.iot.activities.main.statistics.StatisticsFragment;
import com.hqhop.www.iot.activities.main.workbench.NewWorkbenchFragment;
import com.hqhop.www.iot.api.AppService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.ViewPagerAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.receiver.NetworkConnectChangedReceiver;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.bean.OperationCountBean;
import com.hqhop.www.iot.bean.UpdateDataBean;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private String TAG = "MainActivity";

    private ViewPager viewPager;

    private BottomNavigationBar navigation;

    private List<Fragment> fragmentList;

    private NewWorkbenchFragment workbenchFragment;

    private FollowFragment followFragment;

    private StatisticsFragment statisticsFragment;

    private MapFragment mapFragment;

    private AboutFragment aboutFragment;

    public static Dialog dialog;

    private final long DURATION_DOUBLE_CLICK_EXIT = 2000L;

    private boolean clickedBack;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listenNetwordStatus();
    }

    @SuppressLint("HardwareIds")
    private void init() {
        // 判断系统语言
        String locale = Locale.getDefault().getLanguage();
        switch (locale.toLowerCase()) {
            case "zh":
                App.LANGUAGE = "zh";
                break;
            default:
                App.LANGUAGE = "en";
                break;
        }

        CommonUtils.requestPermission(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
//                        Manifest.permission.WRITE_SETTINGS,
                        Manifest.permission.CAMERA});

        appService = RetrofitManager.getInstance(this).createService(AppService.class);

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
//                            appService.checkForUpdate(1)
//                                    .subscribeOn(Schedulers.io())
//                                    .subscribeOn(AndroidSchedulers.mainThread())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribe(new Subscriber<UpdateDataBean>() {
//                                        @Override
//                                        public void onCompleted() {
//
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//                                        }
//
//                                        @Override
//                                        public void onNext(UpdateDataBean bean) {
//                                            if (bean.isSuccess()) {
//                                                if (bean.getData().getIsUpgrade() == 1) {
////                                                                                    showUpdateDialog(bean.getData().getExplain(), bean.getData().getUrl());
//                                                    apk_path = Common.BASE_URL + bean.getData().getUrl();
//                                                    showPromptDialog();
//                                                } else {
//                                                    CommonUtils.showToast(getString(R.string.no_update_available));
//                                                }
//                                            } else {
//                                                CommonUtils.showToast(bean.getMessage());
//                                            }
//                                        }
//                                    });
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
//                                                                    } else {
////                                                                        CommonUtils.showToast(getString(R.string.no_update_available));
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

        viewPager = findViewById(R.id.viewpager);
        navigation = findViewById(R.id.navigation);

        workbenchFragment = NewWorkbenchFragment.getInstance(this);
        followFragment = FollowFragment.getInstance(this);
        statisticsFragment = StatisticsFragment.getInstance(this);
        mapFragment = MapFragment.getInstance(this);
        aboutFragment = AboutFragment.getInstance(this);

        fragmentList = new ArrayList<>();
        fragmentList.add(workbenchFragment);
        fragmentList.add(followFragment);
        fragmentList.add(statisticsFragment);
        fragmentList.add(mapFragment);
        fragmentList.add(aboutFragment);

        dialog = CommonUtils.createLoadingDialog(this);

        setupNavigation();

        // 获取唯一识别码
//        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (tm != null) {
//            App.DEVICE_ID = tm.getDeviceId();
//        }
//        String simSerialNumber = tm.getSimSerialNumber();// 有sim卡的设备可以获取sim卡序列号
//        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);// 在设备首次启动时，系统会随机生成一个64位的数字，wipe后会被重置
//        Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        String serialNumber = Build.SERIAL;// 2.3以上可以得到Serial Number

//        if (App.userName != null) {
//            appService.appOperationCount(App.userName, App.DEVICE_ID, "android")
//                    .subscribeOn(Schedulers.io())
//                    .subscribeOn(AndroidSchedulers.mainThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<OperationCountBean>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.e("Allen", e.getMessage());
//                        }
//
//                        @Override
//                        public void onNext(OperationCountBean bean) {
//                            new MaterialDialog.Builder(getApplicationContext())
//                                    .title(bean.getData().get(0).getNoticeTitle())
//                                    .content(bean.getData().get(0).getContent())
//                                    .positiveText("Ok")
//                                    .onPositive((dialog, which) -> dialog.dismiss())
//                                    .show();
//                        }
//                    });
//        }

        // 启动百度推送服务
        try {
            SharedPreferences sp = getSharedPreferences(Common.SP_USER_INFO, Context.MODE_PRIVATE);
            boolean useNotification = sp.getBoolean("useNotification", true);
            if (useNotification) {
                PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, CommonUtils.getMetaValue(this, "api_key"));
                List<String> tags = new ArrayList<>();
                tags.add(String.valueOf(App.userid));
                PushManager.setTags(this, tags);
                Log.d("Allen", "启动推送服务成功");
            } else {
                Log.d("Allen", "用户未开启推送服务");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Allen", "启动推送服务失败");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Common.REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 已授权
                } else {
                    // 未授权
                    new MaterialDialog.Builder(this)
                            .title(getString(R.string.attention))
                            .content(getString(R.string.authorization_to_receive_push_message))
                            .positiveText(getString(R.string.go_to_settings))
                            .negativeText(getString(R.string.cancel))
                            .onNegative((dialog, which) -> {

                            })
                            .onPositive((dialog, which) -> {
                                Intent intent = new Intent();
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", getPackageName(), null));
                                startActivity(intent);
                            })
                            .show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 监听网络状态
     */
    private void listenNetwordStatus() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new NetworkConnectChangedReceiver(), filter);
    }

    private void setupNavigation() {
        viewPager.setOffscreenPageLimit(5);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
//        navigation.setMode(BottomNavigationBar.MODE_FIXED);
        navigation.setMode(BottomNavigationBar.MODE_SHIFTING);
        navigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigation.setBarBackgroundColor(R.color.white);
        navigation
                .addItem(new BottomNavigationItem(R.drawable.ic_workbench, getString(R.string.title_workbench)))
                .addItem(new BottomNavigationItem(R.drawable.ic_follow, getString(R.string.title_follow)))
                .addItem(new BottomNavigationItem(R.drawable.ic_statistics, getString(R.string.title_statistics)))
                .addItem(new BottomNavigationItem(R.drawable.ic_map, getString(R.string.title_map)))
                .addItem(new BottomNavigationItem(R.drawable.ic_about, getString(R.string.title_about)))
                .setFirstSelectedPosition(0)
                .initialise();
        navigation.setTabSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        ActivityUtils.startHomeActivity();

//        Timer timer;
//        if (!clickedBack) {
//            clickedBack = !clickedBack;
//            CommonUtils.showToast(R.string.double_click_to_exit);
//            timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    clickedBack = !clickedBack;
//                }
//            }, DURATION_DOUBLE_CLICK_EXIT);
//        } else {
////            System.exit(0);
//            Process.killProcess(Process.myPid());
//        }
    }

    @Override
    public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.finishActivity(MainActivity.class);
        fragmentList.clear();
        fragmentList = null;
        workbenchFragment.destroySelf();
        workbenchFragment = null;
        followFragment.destroySelf();
        followFragment = null;
        statisticsFragment.destroySelf();
        statisticsFragment = null;
        mapFragment.destroySelf();
        mapFragment = null;
        aboutFragment.destroySelf();
        aboutFragment = null;
        navigation = null;
        try {
            PushManager.stopWork(getApplicationContext());
            Log.d("Allen", "关闭推送服务成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Allen", "关闭推送服务失败");
        }
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
}
