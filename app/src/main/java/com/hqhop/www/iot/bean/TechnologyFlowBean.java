package com.hqhop.www.iot.bean;

/**
 * 工艺流程Bean
 * Created by allen on 2017/9/13.
 */

public class TechnologyFlowBean {
    /**
     * data : {"name":"","id":null,"url":"/url/Technological.html"}
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
         * name :
         * id : null
         * url : /url/Technological.html
         */

        private String name;
        private Object id;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
