package com.hqhop.www.iot.bean;

/**
 * Created by allenchiang on 2017/12/14.
 */

public class AlertImageBean {

    /**
     * data : tank_liquid_level@3x.png
     * success : true
     * message : 查询成功
     * version : 1
     */

    private String data;
    private boolean success;
    private String message;
    private int version;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
}
