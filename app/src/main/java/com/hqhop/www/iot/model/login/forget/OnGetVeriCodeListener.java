package com.hqhop.www.iot.model.login.forget;

/**
 * Created by allen on 2017/7/13.
 */

public interface OnGetVeriCodeListener {
    void getVeriCodeSucceed(String message);

    void getVeriCodeFailed(String message);
}
