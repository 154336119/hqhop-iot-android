package com.hqhop.www.iot.model.login.forget;

import android.content.Context;

import com.hqhop.www.iot.api.login.LoginService;

/**
 * Created by allen on 2017/7/13.
 */

public interface IGetVeriCode {

    void getVeriCode(Context context, LoginService service, String account, OnGetVeriCodeListener getVeriCodeListener);
}
