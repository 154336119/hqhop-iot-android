package com.hqhop.www.iot.model.login;

/**
 * Created by allen on 2017/7/13.
 */

public interface OnGetTokenListener {
    void getTokenSucceed();

    void getTokenFailed(String message);
}
