package com.hqhop.www.iot.activities.main.about;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.api.register.InviteRegisterService;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.CheckInvitePermissionBean;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileActivity extends BaseAppCompatActivity {

    private InviteRegisterService inviteRegisterService;

    private ImageView ivQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle(getString(R.string.about_profile));

        inviteRegisterService = RetrofitManager.getInstance(this).createService(InviteRegisterService.class);

        ivQRCode = findViewById(R.id.iv_qrcode_profile);

        checkPermission();
    }

    /**
     * 检查是否有权限邀请注册
     */
    private void checkPermission() {
        inviteRegisterService.checkInvitePermission(App.userid)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CheckInvitePermissionBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CheckInvitePermissionBean bean) {
                        if (bean.isSuccess()) {
                            generateQRCode();
                        }
                    }
                });
    }

    /**
     * 生成二维码
     */
    private void generateQRCode() {
        new Thread(() -> {
            Bitmap qrCode = QRCodeEncoder.syncEncodeQRCode(App.userid, 1024);
            runOnUiThread(() -> {
                if (qrCode != null) {
                    ivQRCode.setImageBitmap(qrCode);
                    ivQRCode.setVisibility(View.VISIBLE);
                }
            });
        }).run();
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }
}
