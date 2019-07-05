package com.hqhop.www.iot.bean;

import java.util.List;

/**
 * Created by allen on 09/15/2017.
 */

public class ShownEquipmentButtonBean {

    /**
     * data : [{"code":"1001","name":"设备监控","id":1,"sort":"1","status":"1"},{"code":"1002","name":"报警信息","id":2,"sort":"2","status":"1"},{"code":"1004","name":"工艺流程图","id":4,"sort":"4","status":"1"},{"code":"1005","name":"设备维保","id":5,"sort":"5","status":"1"},{"code":"1008","name":"视屏监控","id":8,"sort":"8","status":"1"}]
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
         * code : 1001
         * name : 设备监控
         * id : 1
         * sort : 1
         * status : 1
         */

        private String code;
        private String name;
        private int id;
        private String sort;
        private String status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
