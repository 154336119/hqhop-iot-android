package com.hqhop.www.iot.api.workbench;

import com.hqhop.www.iot.bean.AlertInWorkbenchBean;
import com.hqhop.www.iot.bean.BusinessInWorkbenchBean;
import com.hqhop.www.iot.bean.EventsBean;
import com.hqhop.www.iot.bean.LiquidOrPressureInWorkbenchBean;
import com.hqhop.www.iot.bean.MaintainInWorkbenchBean;
import com.hqhop.www.iot.bean.WorkbenchBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by allen on 2017/8/15.
 */

public interface WorkbenchService {

    /**
     * 工作台主页模块信息
     *
     * @param userId 用户id
     * @return
     */
    @GET("app/workbench/userId/{userId}")
    Observable<WorkbenchBean> getModuleType(@Path("userId") String userId);

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
    Observable<AlertInWorkbenchBean> getWorkbenchAlertMessage(@Path("userId") String userId,
                                                              @Path("page") int page,
                                                              @Path("pageNumber") int pageNumber,
                                                              @Path("stationType") String stationType,
                                                              @Path("sortType") int sortType);

    /**
     * 获取工作台液位或压力信息
     *
     * @param userId 用户id
     * @return
     */
    @GET("app/workbench/pressureOrLiquid/userId/{userId}/configType/{configType}/page/{page}/pageNumber/{pageNumber}/stationType/{stationType}/sortTypeValue/{sortTypeValue}")
    Observable<LiquidOrPressureInWorkbenchBean> getWorkbenchPressureOrLiquidMessage(@Path("userId") String userId,
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
     * 获取工作台维保信息
     *
     * @param userId 用户id
     * @return
     */
    @GET("app/workbench/maintain/userId/{userId}")
    Observable<MaintainInWorkbenchBean> getWorkbenchMaintainMessage(@Path("userId") String userId);

    /**
     * 获取事件列表
     *
     * @param userId 用户id
     * @param count  每个站点显示的事件条数
     * @return
     */
    @GET("app/workbench/event/userId/{userId}/count/{count}/type/{type}")
    Observable<EventsBean> getEventsMessage(@Path("userId") String userId,
                                            @Path("count") int count,
                                            @Path("type") String type);

    /**
     * 分页查询事件
     *
     * @param stationId  站点id
     * @param page       页数
     * @param pageNumber 每页条数
     * @return
     */
    @GET("app/workbench/events/stationId/{stationId}/type/{type}/page/{page}/pageNumber/{pageNumber}")
    Observable<EventsBean> getEventsMessageByPage(@Path("stationId") String stationId,
                                                  @Path("type") String type,
                                                  @Path("page") int page,
                                                  @Path("pageNumber") int pageNumber);
}
