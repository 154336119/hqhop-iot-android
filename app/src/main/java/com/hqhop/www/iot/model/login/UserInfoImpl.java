package com.hqhop.www.iot.model.login;

import android.content.Context;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.bean.UserInfoBean;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allen on 2017/7/13.
 */

public class UserInfoImpl implements IUserInfo {
    @Override
    public void getToken(Context context, LoginService service, String username, final OnInitUserInfoListener initUserInfoListener) {
        service.initInfo(username)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        initUserInfoListener.initInfoFailed(context.getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        if (userInfoBean.isSuccess()) {
                            initUserInfoListener.initInfoSucceed();
                            App.userInfoBean = userInfoBean;
                            App.userid = userInfoBean.getData().getId();
                            App.userName = userInfoBean.getData().getUsername();
                        } else {
                            initUserInfoListener.initInfoFailed(userInfoBean.getMessage());
                        }
                    }
                });
    }
}
