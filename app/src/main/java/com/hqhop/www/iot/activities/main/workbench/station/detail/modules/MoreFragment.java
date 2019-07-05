package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alipay.sdk.app.PayTask;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.bean.PayResult;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * 站点详情页-更多Fragment
 * Created by allen on 2017/8/4.
 */

public class MoreFragment extends Fragment implements View.OnClickListener, IWXAPIEventHandler {

    private String TAG = "MoreFragment";

    private View rootView;

    private Button btnAlipay, btnWechat;

    final String orderInfo = "info";   // 订单信息

    private IWXAPI msgApi;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultStatus = payResult.getResultStatus();
                CommonUtils.showToast(payResult.getResult());
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                    updateAlipayAndWechatPayStatus();
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    //Utils.showToastRemind(UserPayActivity.this, payResult.getMemo());
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_more, container, false);

        btnAlipay = (Button) rootView.findViewById(R.id.btn_alipay_more_detail_station);
        btnWechat = (Button) rootView.findViewById(R.id.btn_wechat_more_detail_station);
        btnAlipay.setOnClickListener(this);
        btnWechat.setOnClickListener(this);

        msgApi = WXAPIFactory.createWXAPI(getActivity(), null);
        msgApi.registerApp("APPID");

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_alipay_more_detail_station:
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(getActivity());
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
                break;
            case R.id.btn_wechat_more_detail_station:
                // 发起支付
                PayReq request = new PayReq();
                request.appId = "wxd930ea5d5a258f4f";
                request.partnerId = "1900000109";
                request.prepayId = "1101000000140415649af9fc314aa427";
                request.packageValue = "Sign=WXPay";
                request.nonceStr = "1101000000140429eb40476f8896f4c9";
                request.timeStamp = "1398746574";
                request.sign = "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
                msgApi.sendReq(request);
                break;
            default:
                break;
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.d("Allen", "onPayFinish,errCode=" + resp.errCode);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示");
        }
    }
}
