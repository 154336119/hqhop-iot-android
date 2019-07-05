package com.hqhop.www.iot.bean;

/**
 * Created by allenchiang on 2018/1/2.
 */

public class CheckInvitePermissionBean {

    /**
     * data : {"phone":"","userId":"40288e81567c9e7f01567cc223440007","status":"new"}
     * success : true
     * message : 检验成功
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
