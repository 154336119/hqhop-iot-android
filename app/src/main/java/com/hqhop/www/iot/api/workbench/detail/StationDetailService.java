package com.hqhop.www.iot.api.workbench.detail;

import com.hqhop.www.iot.bean.AllAlertBean;
import com.hqhop.www.iot.bean.AllEquipmentsBean;
import com.hqhop.www.iot.bean.EquipmentMonitorBean;
import com.hqhop.www.iot.bean.EquipmentMonitorDetailBean;
import com.hqhop.www.iot.bean.EquipmentRealData;
import com.hqhop.www.iot.bean.GatewayBean;
import com.hqhop.www.iot.bean.MaintainUrlBean;
import com.hqhop.www.iot.bean.ParameterDetailBean;
import com.hqhop.www.iot.bean.PartsBean;
import com.hqhop.www.iot.bean.ReportBean;
import com.hqhop.www.iot.bean.ShownEquipmentButtonBean;
import com.hqhop.www.iot.bean.StationAlarmInfoBean;
import com.hqhop.www.iot.bean.StationInforBean;
import com.hqhop.www.iot.bean.TechnologyFlowBean;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by allen on 2017/8/23.
 */

public interface StationDetailService {

    /**
     * 根据站点id获取该站点报警信息
     *
     * @param stationId
     * @return
     */
    @GET("app/station/status/stationId/{stationId}")
    Observable<StationAlarmInfoBean> getStationAlarmInformation(@Path("stationId") String stationId);

    /**
     * 根据id获取站点信息
     *
     * @param stationId 站点id
     * @return
     */
    @GET("app/station/stationId/{stationId}")
    Observable<StationInforBean> getStationInformation(@Path("stationId") String stationId);

    /**
     * 获取要显示的设备按钮
     *
     * @param status 1->启用
     * @return
     */
    @GET("app/equipmentButton/findEquipmentButtonByStatus/{status}/{userId}")
    Observable<ShownEquipmentButtonBean> getShownItems(@Path("status") String status,
                                                       @Path("userId") String userId);

    /**
     * 获取设备监控列表
     *
     * @param stationId 站点id
     * @return
     */
    @POST("api/monitor/findEquipmentMonitor/{stationId}")
    Observable<EquipmentMonitorBean> getEquipmentMonitorList(@Path("stationId") String stationId);

    /**
     * 获取设备监控详情
     *
     * @param id 设备id
     * @return
     */
    @POST("api/monitorDetails/findEquipmentMonitorDetails/{id}")
    Observable<EquipmentMonitorDetailBean> getEquipmentMonitorDetail(@Path("id") String id);

    /**
     * 获取实时数据
     *
     * @param parameterId 63b2fcc6-8d46-11e7-828f-f1bfe84dc7fe
     * @return
     */
    @GET("api/parameterDetail/realData/parameterId/{parameterId}")
    Observable<EquipmentRealData> getRealData(@Path("parameterId") String parameterId);

    /**
     * 获取档案信息
     *
     * @param equipmentId 41
     * @return
     */
    @GET("api/parameterDetail/equipmentProfile/equipmentId/{equipmentId}")
    Observable<ParameterDetailBean> getParameterDetail(@Path("equipmentId") String equipmentId);

    /**
     * 获取报表数据
     *
     * @param type        day|week|month
     * @param stationId   站点id
     * @param date        日期
     * @param parameterId
     * @return
     */
    @GET("api/parameterDetail/report/type/{type}/stationId/{stationId}/date/{date}/parameterId/{parameterId}")
    Observable<ReportBean> getReport(@Path("type") String type,
                                     @Path("stationId") String stationId,
                                     @Path("date") String date,
                                     @Path("parameterId") String parameterId);

    /**
     * 获取工艺流程url
     *
     * @return
     */
    @GET("app/technological/findTechnological")
    Observable<TechnologyFlowBean> getTechnologyFlow();

    /**
     * 获取运营统计url
     *
     * @return
     */
//    @GET("api/managementController/findManagement")
//    Observable<TechnologyFlowBean> getBusinessData();
    @GET("api/operateController/findOperate")
    Observable<TechnologyFlowBean> getBusinessData();

    /**
     * 获取维保信息url
     *
     * @return
     */
    @GET("api/equipmentRealTimeController/findMaintenance")
    Observable<MaintainUrlBean> getMaintainUrl();


    // 报表url
//    /api/monitor/findEquipment  <GET>  ?stationId=&dateStr=&parameterId=&access_token

    /**
     * 获取设备报表信息url
     *
     * @return
     */
    @GET("api/monitor/findEquipment")
    Observable<TechnologyFlowBean> getEquipmentChartUrl();

    /**
     * 获取全部报警信息
     *
     * @param stationId     站点id
     * @param page          页数
     * @param pageNumber    每页显示数量
     * @param sortTypeValue 排序类型:0全部 1时间由近到远 2时间由远到近 3最轻微到最紧急 4 最紧急到最轻微
     * @return
     */
    @GET("app/allAlarms/stationId/{stationId}/page/{page}/pageNumber/{pageNumber}/sortType/{sortTypeValue}")
    Observable<AllAlertBean> getWorkbenchAlertMessage(@Path("stationId") String stationId,
                                                      @Path("page") int page,
                                                      @Path("pageNumber") int pageNumber,
                                                      @Path("sortTypeValue") int sortTypeValue);

    @GET("app/allAlarms/stationId/{stationId}/page/{page}/pageNumber/{pageNumber}/dayCount/{dayCount}/equipmentId/{equipmentId}/timeStamp/{timeStamp}")
    Observable<AllAlertBean> getWorkbenchAlertMessage(@Path("stationId") String stationId,
                                                      @Path("page") int page,
                                                      @Path("pageNumber") int pageNumber,
                                                      @Path("dayCount") int dayCount,
                                                      @Path("equipmentId") String equipmentId,
                                                      @Path("timeStamp") String timeStamp);

    /**
     * 根据站点id获取该站点的设备
     *
     * @param stationId 站点id
     * @return
     */
    @GET("api/parameterDetail/equipments/stationId/{stationId}")
    Observable<AllEquipmentsBean> getEquipments(@Path("stationId") String stationId);

    /**
     * 获取网关信息
     *
     * @param stationId 站点id
     * @return
     */
    @GET("app/collector/status/stationId/{stationId}")
    Observable<GatewayBean> getGatewayInfo(@Path("stationId") String stationId);

    /**
     * 根据设备id获取部件
     *
     * @param equipmentId 设备id
     */
    @GET("api/parameterDetail/equipmentParts/{equipmentId}")
    Observable<PartsBean> getParts(@Path("equipmentId") String equipmentId);
}
