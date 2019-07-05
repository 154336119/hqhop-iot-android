package com.hqhop.www.iot.model.login.forget;

import android.content.Context;

import com.hqhop.www.iot.api.login.LoginService;

/**
 * Created by allen on 2017/7/13.
 */

public interface IUpdatePassword {

    void updatePassword(Context context, LoginService service, String verificationCode, String loginName, String password, OnUpdatePasswordListener updatePasswordListener);
}
