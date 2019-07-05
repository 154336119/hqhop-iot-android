package com.hqhop.www.iot.model.login.forget;

/**
 * Created by allen on 2017/7/13.
 */

public interface OnUpdatePasswordListener {
    void updateSucceed(String message);

    void updateFailed(String message);
}
