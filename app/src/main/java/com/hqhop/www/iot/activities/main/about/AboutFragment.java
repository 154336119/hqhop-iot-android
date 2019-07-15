package com.hqhop.www.iot.activities.main.about;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.hqhop.www.iot.App;
import com.hqhop.www.iot.BuildConfig;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.login.LoginActivity;
import com.hqhop.www.iot.api.about.AboutService;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.AboutGridViewAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.ToolbarUtils;
import com.hqhop.www.iot.bean.AfterScanQRCodeBean;
import com.hqhop.www.iot.bean.AuthCodeBean;
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

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allen on 2017/7/3.
 */

@SuppressLint("ValidFragment")
public class AboutFragment extends Fragment {

    private String TAG = "AboutFragment";

    private Context mContext;

    private View rootView;

    private Toolbar toolbar;

    private TextView titleToolbar;

    private Button btnLogout;

    private GridView gridView;

    private Switch mSwitch;

    private AboutGridViewAdapter adapter;

    private List<String> titles;

    private List<String> contents;

    private List<String> types;

    private List<Boolean> isShowMores;

    private String qrcodeId;

    private ProgressDialog progressDialog;

    private AboutService aboutService;

    // 存放路径
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/";

    // 下载应用存放全路径
    private static final String FILE_NAME = FILE_PATH + "update.apk";

    // apk下载url
    private String apk_path;

    // 准备安装新版本应用标记
    private static final int INSTALL_TOKEN = 1;

    private static AboutFragment aboutFragment;

    public AboutFragment() {
    }

    private AboutFragment(Context context) {
        mContext = context;
    }

    public static AboutFragment getInstance(Context context) {
        if (aboutFragment == null) {
            aboutFragment = new AboutFragment(context);
        }
        return aboutFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (rootView == null) {
        rootView = inflater.inflate(R.layout.fragment_about, container, false);
//        }

        CommonUtils.setTranslucentStatusBar(mContext);

        toolbar = rootView.findViewById(R.id.toolbar);
        titleToolbar = rootView.findViewById(R.id.title_toolbar);
        setupToolbar();

        btnLogout = rootView.findViewById(R.id.btn_logout_about);
        btnLogout.setOnClickListener(view -> {
            // clear data
            App.token = "";
            App.userid = "";
            App.userName = "";
            App.hasLogin = false;

            SharedPreferences sp = mContext.getSharedPreferences(Common.SP_USER_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("b", "");
            editor.apply();

            // go back
//            LoginActivity.clearData();
            ((Activity) mContext).finish();
            startActivity(new Intent(mContext, LoginActivity.class));
        });

        mSwitch = rootView.findViewById(R.id.switch_push_about);
        SharedPreferences sp = mContext.getSharedPreferences(Common.SP_USER_INFO, Context.MODE_PRIVATE);
        boolean useNotification = sp.getBoolean("useNotification", true);
        mSwitch.setChecked(useNotification);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("useNotification", isChecked);
                editor.apply();
                if (isChecked) {
                    PushManager.startWork(mContext.getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, CommonUtils.getMetaValue(mContext, "api_key"));
                    List<String> tags = new ArrayList<>();
                    tags.add(String.valueOf(App.userid));
                    PushManager.setTags(mContext, tags);
                    Log.d("Allen", "启动推送服务成功");
                } else {
                    PushManager.stopWork(mContext.getApplicationContext());
                    Log.d("Allen", "关闭推送服务成功");
                }
            }
        });

        gridView = rootView.findViewById(R.id.gridview_about);
        titles = new ArrayList<>();
        contents = new ArrayList<>();
        types = new ArrayList<>();
        isShowMores = new ArrayList<>();
        adapter = new AboutGridViewAdapter(mContext, titles, contents, types, isShowMores);
        gridView.setAdapter(adapter);
        setData();

        gridView.setOnItemClickListener((adapterView, view, position, id) -> {
            switch (position) {
                case 0:
                    CommonUtils.dial(mContext, contents.get(0));
                    break;
                case 1:
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(contents.get(position));
                    CommonUtils.showToast(getString(R.string.copy_email_to_clipboard));
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{contents.get(position)});
                    i.putExtra(Intent.EXTRA_SUBJECT, "");
                    i.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(i, "Select email application."));
                    break;
                case 2:
                    checkForUpdate(BuildConfig.VERSION_CODE);
                    break;
                case 3:
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        CommonUtils.showToast(getString(R.string.no_camera_permission));
                    } else {
                        Intent scanIntent = new Intent(mContext, NewScanQRCodeActivity.class);
                        startActivityForResult(scanIntent, Common.REQUEST_CODE_QRCODE);
                    }
                    break;
                case 4:
                    startActivity(new Intent(mContext, FeedbackActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(mContext, ProfileActivity.class));
                    break;
                default:
                    break;
            }
        });

        aboutService = RetrofitManager.getInstance(mContext).createService(AboutService.class);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            MobclickAgent.onPageStart(TAG);
        } else {
            MobclickAgent.onPageEnd(TAG);
        }
    }

    /**
     * 检查更新
     */
    private void checkForUpdate(int version) {
        CommonUtils.showToast(getString(R.string.checking_for_update));
        aboutService.checkForUpdate(version)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UpdateDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.showToast(getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(UpdateDataBean bean) {
                        if (bean.isSuccess()) {
                            if (bean.getData().getIsUpgrade() == 1) {
                                showUpdateDialog(bean.getData().getExplain(), bean.getData().getUrl());
                            } else {
                                CommonUtils.showToast(getString(R.string.no_update_available));
                            }
                        } else {
                            CommonUtils.showToast(bean.getMessage());
                        }
                    }
                });
    }

    /**
     * 显示更新对话框
     */
    @SuppressLint("SetTextI18n")
    private void showUpdateDialog(@Nullable String description, @Nullable String url) {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.show();
//        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.layout_dialog_update);
        TextView tvDescription = window.findViewById(R.id.tv_description_update);
        Button btnUpdate = window.findViewById(R.id.btn_download_update);
        Button btnIgnore = window.findViewById(R.id.btn_ignore_update);
        if (TextUtils.isEmpty(description)) {
            tvDescription.setVisibility(View.GONE);
        } else {
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(getString(R.string.update_description) + description);
        }
        btnUpdate.setOnClickListener(view -> {
            if (TextUtils.isEmpty(url)) {
                CommonUtils.showToast(getString(R.string.file_path_error_to_update));
            } else {
                alertDialog.dismiss();
                apk_path = Common.BASE_URL + url;
                showDownloadDialog();
            }
        });
        btnIgnore.setOnClickListener(view -> alertDialog.dismiss());
    }

    private void showDownloadDialog() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle(getString(R.string.downloading));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
        downloadAsyncTask.execute();

        progressDialog.setOnCancelListener(dialogInterface -> {
            dialogInterface.dismiss();
            downloadAsyncTask.cancel(true);
        });
    }

    private void setData() {
        titles.add(getString(R.string.about_telephone));
        titles.add(getString(R.string.about_email));
        titles.add(getString(R.string.about_app));
        titles.add(getString(R.string.about_scan_qrcode));
        titles.add(getString(R.string.about_feedback));
        titles.add(getString(R.string.about_profile));
        contents.add("0532-83785165");
        contents.add("397632969@qq,com");
        contents.add("V" + BuildConfig.VERSION_NAME);
        contents.add("");
        contents.add("");
        contents.add("");
        contents.add("");
        isShowMores.add(true);
        isShowMores.add(true);
        isShowMores.add(true);
        isShowMores.add(true);
        isShowMores.add(true);
        isShowMores.add(true);
        types.add("tel");
        types.add("email");
        types.add("app");
        types.add("scan");
        types.add("feedback");
        types.add("profile");
        adapter.notifyDataSetChanged();
    }

    private void setupToolbar() {
        ToolbarUtils.setCustomToolbar(mContext, toolbar);
        ToolbarUtils.setTitle(titleToolbar, getString(R.string.title_about));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Common.REQUEST_CODE_QRCODE) {
            if (resultCode == Activity.RESULT_OK) {
                qrcodeId = data.getStringExtra("result");
                afterScanQRCode(qrcodeId);
            }
        }
    }

    /**
     * 扫描二维码后调用，验证二维码有效性
     */
    private void afterScanQRCode(String qrCodeId) {
        RetrofitManager.getInstance(mContext).createService(LoginService.class).afterScanQRCode(qrCodeId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AfterScanQRCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.showToast(getString(R.string.qrcode_newwork_error_scan_again));
                        Log.d("Allen", e.toString());
                    }

                    @Override
                    public void onNext(AfterScanQRCodeBean bean) {
                        if (bean.isSuccess()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setMessage(getString(R.string.authorization_to_login_in_web));
                            builder.setTitle(getString(R.string.attention));
                            builder.setPositiveButton(getString(R.string.confirm), (dialogInterface, id) -> {
                                dialogInterface.dismiss();
                                confirmLogin();
                            });
                            builder.setNegativeButton(getString(R.string.cancel), (dialogInterface, id) -> dialogInterface.dismiss());
                            builder.create().show();
                        } else {
                            CommonUtils.showToast(bean.getMessage());
                        }
                    }
                });
    }

    private void confirmLogin() {
        RetrofitManager.getInstance(mContext).createService(LoginService.class).authLogin(App.userid, qrcodeId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Allen", e.toString());
                        CommonUtils.showToast(getString(R.string.authorization_failed));
                    }

                    @Override
                    public void onNext(AuthCodeBean bean) {
                        if (bean.isSuccess()) {
                            CommonUtils.showToast(getString(R.string.authorization_successfully));
                        } else {
                            CommonUtils.showToast(getString(R.string.authorization_failed));
                        }
                    }
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
        File appFile = new File(FILE_NAME);
        if (!appFile.exists()) {
            return;
        }
        // 跳转到新版本应用安装页面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + appFile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    public void destroySelf() {
        aboutFragment = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        aboutFragment = null;
    }
}
