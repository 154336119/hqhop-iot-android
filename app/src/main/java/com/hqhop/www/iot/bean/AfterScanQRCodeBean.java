package com.hqhop.www.iot.bean;

/**
 * Created by allen on 2017/8/25.
 */

public class AfterScanQRCodeBean {

    /**
     * data : {"oauthzCode":"","qrCodeId":"1bd52501509241b389b38b53abdff4bf","userId":"","status":"scan"}
     * success : true
     * message : 成功
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
