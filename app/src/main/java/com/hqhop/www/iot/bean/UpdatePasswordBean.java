package com.hqhop.www.iot.bean;

/**
 * Created by allen on 2017/7/6.
 */

public class UpdatePasswordBean {
    /**
     * data :
     * success : false
     * message : 验证码过期
     */

    private String data;
    private boolean success;
    private String message;

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

    @Override
    public String toString() {
        return "UpdatePasswordBean{" +
                "data='" + data + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
