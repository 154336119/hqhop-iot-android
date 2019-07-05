package com.hqhop.www.iot.api;

import com.hqhop.www.iot.bean.OperationCountBean;
import com.hqhop.www.iot.bean.UpdateDataBean;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by allen on 10/26/2017.
 */

public interface AppService {

    /**
     * 统计用户使用次数，或者从后台返回到前台时调用
     *
     * @param username  用户名
     * @param code      设备唯一编码
     * @param type      系统类型
     * @param longitude 经度
     * @param latitude  纬度
     * @param version   版本号
     */
    @POST("api/mobile/appOperationCount")
    Observable<OperationCountBean> appOperationCount(@Query("username") String username,
                                                     @Query("code") String code,
                                                     @Query("type") String type,
                                                     @Query("longitude") String longitude,
                                                     @Query("latitude") String latitude,
                                                     @Query("version") String version);

    /**
     * 检查升级
     *
     * @param appVersion 版本号
     */
    @GET("api/versionController/findVersionAndroid")
    Observable<UpdateDataBean> checkForUpdate(@Query("appVersion") int appVersion);
}
