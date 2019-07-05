package com.hqhop.www.iot.api;

import com.hqhop.www.iot.bean.WeatherBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by allen on 09/28/2017.
 */

public interface WeatherService {
    /**
     * @param city 104.06,30.67
     * @param key  xpugeiq2cqjbskgy
     * @return
     */
    @GET("v5/weather")
    Call<WeatherBean> getWeather(@Query("city") String city,
                                 @Query("key") String key);
}
