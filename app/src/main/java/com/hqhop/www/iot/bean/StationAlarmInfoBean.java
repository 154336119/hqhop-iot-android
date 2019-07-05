package com.hqhop.www.iot.bean;

/**
 * Created by allen on 10/22/2017.
 */

public class StationAlarmInfoBean {

    /**
     * data : {"content":"有3条报警数据","status":"报警"}
     * success : true
     * message : 查询成功
     * version : 1
     */

    private DataBean data;
    private boolean success;
    private String message;
    private int version;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * content : 有3条报警数据
         * status : 报警
         */

        private String content;
        private String status;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
