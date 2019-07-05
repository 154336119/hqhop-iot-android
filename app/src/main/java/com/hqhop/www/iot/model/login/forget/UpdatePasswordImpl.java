package com.hqhop.www.iot.model.login.forget;

import android.content.Context;
import android.util.Log;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.bean.UpdatePasswordBean;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allen on 2017/7/13.
 */

public class UpdatePasswordImpl implements IUpdatePassword {
    @Override
    public void updatePassword(final Context context, LoginService service, String verificationCode, String loginName, String password, final OnUpdatePasswordListener updatePasswordListener) {
        service.updatePassword(verificationCode, loginName, password)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UpdatePasswordBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        updatePasswordListener.updateFailed(context.getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(UpdatePasswordBean bean) {
                        Log.d(context.getClass().getSimpleName(), bean.toString());
                        if (bean.isSuccess()) {
                            updatePasswordListener.updateSucceed(bean.getMessage());
                        } else {
                            updatePasswordListener.updateFailed(bean.getMessage());
                        }
                    }
                });
    }
}
