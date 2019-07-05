package com.hqhop.www.iot.bean;

import com.google.gson.JsonElement;

import java.util.List;

/**
 * Created by allen on 2017/9/12.
 */

public class ReportBean {

    /**
     * data : [{"2017-09-17":0},{"2017-09-16":0},{"2017-09-15":0},{"2017-09-14":0},{"2017-09-13":0},{"2017-09-12":0},{"2017-09-11":0}]
     * success : true
     * message : 成功
     * version : 1
     */

    private boolean success;
    private String message;
    private int version;
    private List<JsonElement> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<JsonElement> getData() {
        return data;
    }

    public void setData(List<JsonElement> data) {
        this.data = data;
    }
}
