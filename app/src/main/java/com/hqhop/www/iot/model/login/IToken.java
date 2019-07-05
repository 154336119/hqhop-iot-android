package com.hqhop.www.iot.model.login;

import android.content.Context;

import com.hqhop.www.iot.api.login.LoginService;

/**
 * Created by allen on 2017/7/13.
 */

public interface IToken {
    /**
     * 获取token
     *
     * @param context       Context
     * @param service       LoginService
     * @param username      ic_account
     * @param password      ic_password
     * @param tokenListener 事件回调
     */
    void getToken(Context context, LoginService service, String username, String password, OnGetTokenListener tokenListener);
}
