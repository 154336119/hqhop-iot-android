package com.hqhop.www.iot.activities.login.forget;

/**
 * Created by allen on 2017/7/13.
 */

public interface IGetVeriCodeView {

    /**
     * 获取账号
     */
    String getAccount();

    /**
     * 获取验证码成功
     */
    void getCodeSucceed(String message);

    /**
     * 获取验证码失败
     */
    void getCodeFailed(String message);

    void showGetCodeLoading();

    void hideGetCodeLoading();
}
