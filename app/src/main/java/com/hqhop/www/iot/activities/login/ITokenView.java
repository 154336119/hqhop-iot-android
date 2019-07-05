package com.hqhop.www.iot.activities.login;

/**
 * Created by allen on 2017/7/13.
 */

public interface ITokenView {

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取密码
     *
     * @return ic_password
     */
    String getPassword();

    /**
     * 获取授权类型
     *
     * @return 授权类型
     */
    String getGrantType();

    /**
     * 获取token成功
     */
    void tokenSucceed();

    /**
     * 获取token成功
     */
    void tokenFailed(String message);

    /**
     * 显示loading
     */
    void showTokenLoading();

    /**
     * 隐藏loading
     */
    void hideTokenLoading();
}
