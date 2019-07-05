package com.hqhop.www.iot.api.workbench.liquid;

import com.hqhop.www.iot.bean.AllLiquidBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by allen on 2017/8/16.
 */

public interface LiquidService {

    /**
     * 获取全部液位信息
     *
     * @param userId      用户id
     * @param stationType 站点类型
     * @param sortTypeValue    排序类型
     * @return
     */
    @GET("app/workbench/pressureOrLiquid/userId/{userId}/configType/{configType}/page/{page}/pageNumber/{pageNumber}/stationType/{stationType}/sortTypeValue/{sortTypeValue}")
    Observable<AllLiquidBean> getAllLiquidMessage(@Path("userId") String userId,
                                                  @Path("configType") String configType,
                                                  @Path("page") int page,
                                                  @Path("pageNumber") int pageNumber,
                                                  @Path("stationType") String stationType,
                                                  @Path("sortTypeValue") int sortTypeValue);
}
