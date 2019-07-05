package com.hqhop.www.iot.model.login.forget;

import android.content.Context;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.bean.VerificationCodeBean;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allen on 2017/7/13.
 */

public class GetVeriCodeImpl implements IGetVeriCode {

    @Override
    public void getVeriCode(Context context, LoginService service, String account, final OnGetVeriCodeListener getVeriCodeListener) {
        service.getVerificationCode(account)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VerificationCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getVeriCodeListener.getVeriCodeFailed(context.getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(VerificationCodeBean bean) {
                        if (bean.isSuccess()) {
                            getVeriCodeListener.getVeriCodeSucceed(bean.getMessage());
                        } else {
                            getVeriCodeListener.getVeriCodeFailed(bean.getMessage());
                        }
                    }
                });
    }
}
