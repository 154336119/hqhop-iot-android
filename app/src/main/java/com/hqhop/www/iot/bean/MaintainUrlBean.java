package com.hqhop.www.iot.bean;

import java.util.List;

/**
 * Created by allenchiang on 2017/12/15.
 */

public class MaintainUrlBean {
    /**
     * data : [{"name":"default","id":null,"url":"/template/equipmentMaintenance/circle.html"},{"name":"more","id":null,"url":"/template/equipmentMaintenance/realTime.html"}]
     * success : true
     * message : 查询成功
     * version : 1
     */

    private boolean success;
    private String message;
    private int version;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : default
         * id : null
         * url : /template/equipmentMaintenance/circle.html
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
