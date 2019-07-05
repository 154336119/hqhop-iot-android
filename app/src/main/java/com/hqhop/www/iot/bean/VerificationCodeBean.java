package com.hqhop.www.iot.bean;

/**
 * Created by allen on 2017/7/6.
 */

public class VerificationCodeBean {
    /**
     * success : true
     * success_msg : 验证码发送至186****4606请尽快完成认证
     */

    private boolean success;
    private String message;

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
        return "VerificationCodeBean{" +
                "success=" + success +
                ", success_msg='" + message + '\'' +
                '}';
    }
}
