package com.hqhop.www.iot.api.map;

import com.hqhop.www.iot.bean.StationTypeBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by allen on 17-9-6.
 */

public interface MapService {
    /**
     * 获取分类/全部站点列表
     *
     * @param userId       用户id
     * @param page         页数
     * @param pageNumber 每页条数
     * @param stationType  站点类型
     * @param sortType     分类
     * @param regionId     区域id，默认为0
     * @param status       站点状态: 0全部 1在线 2离线
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
}
