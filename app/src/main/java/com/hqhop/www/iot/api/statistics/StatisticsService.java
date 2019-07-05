package com.hqhop.www.iot.api.statistics;

import com.hqhop.www.iot.bean.TechnologyFlowBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by allen on 10/23/2017.
 */

public interface StatisticsService {
    /**
     * 获取运营统计url
     *
     * @return
     */
    @GET("api/managementController/findManagement")
    Observable<TechnologyFlowBean> getBusinessData();
}
