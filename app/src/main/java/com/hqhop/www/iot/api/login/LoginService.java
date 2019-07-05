package com.hqhop.www.iot.api.login;

import com.hqhop.www.iot.bean.AfterScanQRCodeBean;
import com.hqhop.www.iot.bean.AuthCodeBean;
import com.hqhop.www.iot.bean.TokenBean;
import com.hqhop.www.iot.bean.UpdatePasswordBean;
import com.hqhop.www.iot.bean.UserInfoBean;
import com.hqhop.www.iot.bean.VerificationCodeBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by allen on 2017/7/6.
 */

public interface LoginService {

    /**
     * 获取Token
     *
     * @param username 登录名
     * @param password 密码
     * @return
     */
//    @POST("oauth/token")
//    @FormUrlEncoded
//    Observable<TokenBean> getToken(@Field("username") String username,
//                                   @Field("password") String password,
//                                   @Field("grant_type") String grantType);
    @POST("oauth2/client")
    @FormUrlEncoded
    Observable<TokenBean> getToken(@Field("username") String username,
                                   @Field("password") String password);

    /**
     * 获取用户信息(废弃)
     *
     * @param username 登录名
     * @return
     */
    @GET("api/initinfo")
    Observable<UserInfoBean> initInfo(@Query("username") String username);

    /**
     * 获取验证码
     *
     * @param username 登录名
     * @return
     */
    @GET("api/mobile/getVerificationCode/{username}")
    Observable<VerificationCodeBean> getVerificationCode(@Path("username") String username);

    /**
     * 找回密码
     *
     * @param verificationCode 验证码
     * @param loginName        登录名
     * @param password         新密码
     * @return
     */
    @PUT("api/mobile/updatePWD/{verificationCode}/{loginName}/{newPWD}")
    Observable<UpdatePasswordBean> updatePassword(@Path("verificationCode") String verificationCode,
                                                  @Path("loginName") String loginName,
                                                  @Path("newPWD") String password);

    /**
     * 验证二维码有效性
     *
     * @param qrCodeId 二维码id
     * @return
     */
    @POST("scan/qrCodeId/{qrCodeId}")
    Observable<AfterScanQRCodeBean> afterScanQRCode(@Path("qrCodeId") String qrCodeId);

    /**
     * 授权登陆
     *
     * @param userId
     * @param qrCodeId
     * @return
     */
    @GET("oauth2/authCode")
//    Call<AuthCodeBean> authLogin(@Query("userId") String userId,
//                                 @Query("qrCodeId") String qrCodeId);
    Observable<AuthCodeBean> authLogin(@Query("userId") String userId,
                                       @Query("qrCodeId") String qrCodeId);
}
