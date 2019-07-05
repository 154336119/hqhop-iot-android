package com.hqhop.www.iot.api.workbench.type;

import com.hqhop.www.iot.bean.AllAlertBean;
import com.hqhop.www.iot.bean.AllLiquidBean;
import com.hqhop.www.iot.bean.AllPressureBean;
import com.hqhop.www.iot.bean.BusinessInWorkbenchBean;
import com.hqhop.www.iot.bean.SearchStationBean;
import com.hqhop.www.iot.bean.StationTypeBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by allen on 2017/8/18.
 */

public interface StationTypeService {

    /**
     * 获取分类/全部站点列表
     *
     * @param userId      用户id
     * @param page        页数
     * @param pageNumber  每页条数
     * @param stationType 站点类型
     * @param sortType    分类
     * @param regionId    区域id，默认为0
     * @param status      站点状态: 0全部 1在线 2离线
     * @return
     */
    @GET("allStation/userId/{userId}/page/{page}/pageNumber/{pageNumber}/stationType/{stationType}/sortType/{sortType}/regionId/{regionId}/status/{status}")
    Observable<StationTypeBean> getStations(@Path("userId") String userId,
                                            @Path("page") int page,
                                            @Path("pageNumber") int pageNumber,
                                            @Path("stationType") String stationType,
                                            @Path("sortType") int sortType,
                                            @Path("regionId") int regionId,
                                            @Path("status") int status);

    /**
     * 获取全部报警信息
     *
     * @param userId      用户id
     * @param page        页数
     * @param pageNumber  每页显示数量
     * @param stationType 站点类型:0全部
     * @param sortType    排序类型:0全部 1时间由近到远 2时间由远到近 3最轻微到最紧急 4 最紧急到最轻微
     * @return
     */
    @GET("app/allAlarms/userId/{userId}/page/{page}/pageNumber/{pageNumber}/stationType/{stationType}/sortType/{sortType}")
    Observable<AllAlertBean> getAllAlertMessage(@Path("userId") String userId,
                                                @Path("page") int page,
                                                @Path("pageNumber") int pageNumber,
                                                @Path("stationType") String stationType,
                                                @Path("sortType") int sortType);

    /**
     * 获取全部液位信息
     *
     * @param userId        用户id
     * @param stationType   站点类型
     * @param sortTypeValue 排序类型:0全部 1时间由近到远 2时间由远到近 3压力小到大排序 4压力大到小排序
     * @return
     */
    @GET("app/workbench/pressureOrLiquid/userId/{userId}/configType/{configType}/page/{page}/pageNumber/{pageNumber}/stationType/{stationType}/sortTypeValue/{sortTypeValue}")
    Observable<AllLiquidBean> getAllLiquidMessage(@Path("userId") String userId,
                                                  @Path("configType") String configType,
                                                  @Path("page") int page,
                                                  @Path("pageNumber") int pageNumber,
                                                  @Path("stationType") String stationType,
                                                  @Path("sortTypeValue") int sortTypeValue);

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

    /**
     * 获取工作台经营信息
     *
     * @return
     */
    @GET("app/workbench/sale")
    Observable<BusinessInWorkbenchBean> getWorkbenchBusinessMessage();

    /**
     * 根据站点名称搜索
     */
    @GET("allStation/userId/{userId}/stationType/{stationType}")
    Observable<SearchStationBean> searchStationByName(@Path("userId") String userId,
                                                      @Path("stationType") String stationType,
                                                      @Query("stationName") String stationName);
}
