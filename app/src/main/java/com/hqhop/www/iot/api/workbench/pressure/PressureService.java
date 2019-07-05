package com.hqhop.www.iot.api.workbench.pressure;

import com.hqhop.www.iot.bean.AllPressureBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by allen on 2017/8/16.
 */

public interface PressureService {

    /**
     * 获取全部调压出口压力信息
     *
     * @param userId        用户id
     * @param stationType   站点类型:0全部
     * @param sortTypeValue 排序类型:0全部 1时间由近到远 2时间由远到近 3压力小到大排序 4压力大到小排序
     * @return
     */
    @GET("app/workbench/pressureOrLiquid/userId/{userId}/configType/{configType}/page/{page}/pageNumber/{pageNumber}/stationType/{stationType}/sortTypeValue/{sortTypeValue}")
    Observable<AllPressureBean> getAllPressureMessage(@Path("userId") String userId,
                                                      @Path("configType") String configType,
                                                      @Path("page") int page,
                                                      @Path("pageNumber") int pageNumber,
                                                      @Path("stationType") String stationType,
                                                      @Path("sortTypeValue") int sortTypeValue);

}
