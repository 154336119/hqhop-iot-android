package com.hqhop.www.iot.api.follow;

import com.hqhop.www.iot.bean.ModuleBean;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by allen on 2017/8/18.
 */

public interface ModuleService {
    /**
     * 获取可关注的模块
     *
     * @param userId    用户id
     * @param stationId 站点id
     * @return
     */
    @GET("app/focus/userId/{userId}/stationId/{stationId}")
    Observable<ModuleBean> getMudulesData(@Path("userId") String userId,
                                          @Path("stationId") String stationId);

    /**
     * 配置关注参数信息
     *
     * @param userId       用户id
     * @param stationId    站点id
     * @param parameterIds 参数id
     * @return
     */
    @POST("app/focus/userId/{userId}/stationId/{stationId}/parameterIds/{parameterIds}")
    Observable<ModuleBean> setMudules(@Path("userId") String userId,
                                         @Path("stationId") String stationId,
                                         @Path("parameterIds") String parameterIds);
}
