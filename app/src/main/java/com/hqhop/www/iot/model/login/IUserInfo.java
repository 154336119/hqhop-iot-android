package com.hqhop.www.iot.model.login;

import android.content.Context;

import com.hqhop.www.iot.api.login.LoginService;

/**
 * Created by allen on 2017/7/13.
 */

public interface IUserInfo {

    /**
     * 初始化信息
     *
     * @param context              Context
     * @param service              LoginService
     * @param username             ic_account
     * @param initUserInfoListener 事件回调
     */
    void getToken(Context context, LoginService service, String username, OnInitUserInfoListener initUserInfoListener);
}
