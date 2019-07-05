package com.hqhop.www.iot.api.workbench.alert;

import com.hqhop.www.iot.bean.AlertImageBean;
import com.hqhop.www.iot.bean.AlertInformationBean;
import com.hqhop.www.iot.bean.AllAlertBean;
import com.hqhop.www.iot.bean.WorkbenchBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by allen on 2017/8/16.
 */

public interface AlertService {

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
     * 获取报警详情信息
     *
     * @param alarmId 报警id
     * @return
     */
    @GET("app/alarmDetail/alarmId/{alarmId}")
    Observable<WorkbenchBean> getDetailAlertMessage(@Path("alarmId") String alarmId);

    /**
     * 获取报警详情图片
     *
     * @param parameterId 设备id
     */
    @GET("app/alarmImg/getAlarmImg/{parameterId}")
    Observable<AlertImageBean> getAlertImage(@Path("parameterId") String parameterId);

    /**
     * 根据站点id和parameterid获取报警详情
     */
    @GET("app/allAlarms/alarmDetail/stationId/{stationId}/parameterId/{parameterId}")
    Observable<AlertInformationBean> getAlertInformationByParameterId(@Path("stationId") String stationId,
                                                                      @Path("parameterId") String parameterId);
}
