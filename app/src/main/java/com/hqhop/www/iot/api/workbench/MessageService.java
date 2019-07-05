package com.hqhop.www.iot.api.workbench;

import com.hqhop.www.iot.bean.TechnologyFlowBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by allenchiang on 2018/1/30.
 */

public interface MessageService {

    @GET("api/messageCenter/findMessageCenter")
    Observable<TechnologyFlowBean> getMessageUrl();
}
