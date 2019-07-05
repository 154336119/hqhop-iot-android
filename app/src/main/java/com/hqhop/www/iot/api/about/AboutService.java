package com.hqhop.www.iot.api.about;

import com.hqhop.www.iot.bean.CheckInvitePermissionBean;
import com.hqhop.www.iot.bean.FeedbackBean;
import com.hqhop.www.iot.bean.UpdateDataBean;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by allen on 09/26/2017.
 */

public interface AboutService {
    @GET("api/versionController/findVersionAndroid")
    Observable<UpdateDataBean> checkForUpdate(@Query("appVersion") int appVersion);

    @POST("api/mobile/appSuggest")
    Observable<FeedbackBean> feedback(@Query("username") String username,
                                      @Query("code") String code,
                                      @Query("type") String type,
                                      @Query("remark") String remark);
}
