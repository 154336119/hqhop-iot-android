package com.hqhop.www.iot.activities.main.about;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.view.PointsOverlayView;
import com.umeng.analytics.MobclickAgent;

public class NewScanQRCodeActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    private String TAG = "ScanQRCodeActivity";

    private ViewGroup mainLayout;

    //    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;
    private CheckBox flashlightCheckBox;
    //    private CheckBox enableDecodingCheckBox;
    private PointsOverlayView pointsOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_scan_qrcode);

        initQRCodeReaderView();
    }

    private void initQRCodeReaderView() {
        mainLayout = findViewById(R.id.main_layout);
        View content = getLayoutInflater().inflate(R.layout.content_decoder, mainLayout, true);

        qrCodeReaderView = content.findViewById(R.id.qrdecoderview);
//        resultTextView = (TextView) content.findViewById(R.id.result_text_view);
        flashlightCheckBox = content.findViewById(R.id.flashlight_checkbox);
//        enableDecodingCheckBox = (CheckBox) content.findViewById(R.id.enable_decoding_checkbox);
        pointsOverlayView = content.findViewById(R.id.points_overlay_view);

        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        flashlightCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> qrCodeReaderView.setTorchEnabled(isChecked));
//        enableDecodingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                qrCodeReaderView.setQRDecodingEnabled(isChecked);
//            }
//        });
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (qrCodeReaderView != null) {
            qrCodeReaderView.startCamera();
        }
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
//        resultTextView.setText(text);
        vibrate();
        pointsOverlayView.setPoints(points);
        Intent dataIntent = new Intent();
        dataIntent.putExtra("result", text);
        setResult(RESULT_OK, dataIntent);
        finish();
    }

    /**
     * 扫描成功后震动一下
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(150);
    }
}
