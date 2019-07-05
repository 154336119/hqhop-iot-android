package com.hqhop.www.iot.activities.login;

/**
 * Created by allen on 2017/7/13.
 */

public interface IUserInfoView {

    /**
     * 获取用户名
     */
    String getUsernameForInit();

    /**
     * 初始化信息成功
     */
    void initSucceed();

    /**
     * 初始化信息失败
     */
    void initFailed(String message);

    /**
     * 显示初始化信息loading
     */
    void showInitLoading();

    /**
     * 隐藏初始化信息loading
     */
    void hideInitLoading();
}
