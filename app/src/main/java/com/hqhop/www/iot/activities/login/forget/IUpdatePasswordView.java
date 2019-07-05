package com.hqhop.www.iot.activities.login.forget;

/**
 * Created by allen on 2017/7/13.
 */

public interface IUpdatePasswordView {

    String getVerificationCode();

    String getLoginName();

    String getPassword();

    void updatePasswordSucceed(String message);

    void updatePasswordFailed(String message);

    void showUpdatePasswordLoading();

    void hideUpdatePasswordLoading();
}
