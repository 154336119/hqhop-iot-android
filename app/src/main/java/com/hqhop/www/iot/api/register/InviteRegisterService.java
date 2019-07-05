package com.hqhop.www.iot.api.register;

import com.hqhop.www.iot.bean.CheckInvitePermissionBean;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by allenchiang on 2018/1/2.
 */

public interface InviteRegisterService {

    /**
     * 检查是否有邀请用户注册的权限
     */
    @GET("scan/register/check/userId/{userId}")
    Observable<CheckInvitePermissionBean> checkInvitePermission(@Path("userId") String userId);

    /**
     * 检查二维码状态
     */
    @GET("scan/register/userId/{userId}")
    Observable<CheckInvitePermissionBean> checkQRCodeStatus(@Path("userId") String userId);

    /**
     * 授权数据保存(注册时调用)
     */
    @POST("/scan/register/scan/userId/{userId}/phone/{phone}/password/{password}/choose/{choose}/mobileId/{mobileId}/verificationCode/{verificationCode}")
    Observable<CheckInvitePermissionBean> authData(@Path("userId") String userId,
                                                   @Path("phone") String phone,
                                                   @Path("password") String password,
                                                   @Path("choose") String choose,
                                                   @Path("mobileId") String mobileId,
                                                   @Path("verificationCode") String verificationCode);
}
