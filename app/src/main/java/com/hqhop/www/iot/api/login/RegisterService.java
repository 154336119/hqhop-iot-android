package com.hqhop.www.iot.api.login;

import com.hqhop.www.iot.bean.RegisterBean;
import com.hqhop.www.iot.bean.VerificationCodeBean;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by allenchiang on 2017/11/1.
 */

public interface RegisterService {

    /**
     * 获取验证码
     *
     * @param tel 手机号
     * @return
     */
    @GET("api/invitation/tel/{tel}")
    Observable<VerificationCodeBean> getVerificationCode(@Path("tel") String tel);

    /**
     * 注册
     *
     * @param tel              手机号
     * @param verificationCode 验证码
     * @param password         密码(需加密)
     * @param invitationCode   邀请码
     * @return
     */
    @POST("api/invitation/addUser/{tel}/{verificationCode}/{password}/{invitationCode}/{choose}/{mobileID}")
    Observable<RegisterBean> register(@Path("tel") String tel,
                                      @Path("verificationCode") String verificationCode,
                                      @Path("password") String password,
                                      @Path("invitationCode") String invitationCode,
                                      @Path("choose") int choose,
                                      @Path("mobileID") String mobileID);
}
