package com.hqhop.www.iot.activities.main.about;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.util.CommonUtils;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanQRCodeActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private ZXingView zXingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        zXingView = findViewById(R.id.zxing_view);
        zXingView.setDelegate(this);
        zXingView.startSpot();
    }

    //震动器
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();//震动
        zXingView.stopSpot();

        if (!TextUtils.isEmpty(result)) {
            zXingView.stopCamera();
            zXingView.onDestroy();

//            Intent intent = new Intent(ScanCodeActivity.this, ServiceManagerDianZhuNewActivity.class);
//            intent.putExtra("url", result);
            //intent.setData(Uri.parse(result));
//            startActivity(intent);
//            CommonUtils.showToast(result);
            Intent dataIntent = new Intent();
            dataIntent.putExtra("result", result);
            setResult(RESULT_OK, dataIntent);
            finish();
        } else {
            CommonUtils.showToast("链接无效,请重新扫描");
            zXingView.startSpot();
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        CommonUtils.showToast("打开相机失败，请前往设置页面授权");
    }

    protected void onStart() {
        super.onStart();
        zXingView.startCamera();
        zXingView.startSpot();
    }

    @Override
    protected void onStop() {
        super.onStop();
        zXingView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zXingView.onDestroy();
    }
}
