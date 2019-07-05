package com.hqhop.www.iot.presenter.login;

import android.content.Context;

import com.hqhop.www.iot.activities.login.IUserInfoView;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.model.login.IUserInfo;
import com.hqhop.www.iot.model.login.OnInitUserInfoListener;
import com.hqhop.www.iot.model.login.UserInfoImpl;

/**
 * Created by allen on 2017/7/13.
 */

public class UserInfoPresenter {

    private IUserInfo userInfoBiz;

    private IUserInfoView userInfoView;

    public UserInfoPresenter(IUserInfoView userInfoView) {
        this.userInfoView = userInfoView;
        this.userInfoBiz = new UserInfoImpl();
    }

    public void initUserInfo(Context context, LoginService service) {
        userInfoView.showInitLoading();
        userInfoBiz.getToken(context, service, userInfoView.getUsernameForInit(), new OnInitUserInfoListener() {
            @Override
            public void initInfoSucceed() {
                userInfoView.hideInitLoading();
                userInfoView.initSucceed();
            }

            @Override
            public void initInfoFailed(String message) {
                userInfoView.hideInitLoading();
                userInfoView.initFailed(message);
            }
        });
    }

}
