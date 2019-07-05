package com.hqhop.www.iot.bean;

/**
 * Created by allen on 2017/9/14.
 */

public class PushMessageBean {
    /**
     * title : 10002
     * subtitle : 2018-01-30 16:06:47
     * body : 对不起！您上传的驾驶证不符合要求，点击查看详情...
     */

    private String title;
    private String subtitle;
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
