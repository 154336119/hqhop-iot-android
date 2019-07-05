package com.hqhop.www.iot.bean;

import java.util.List;

/**
 * Created by allenchiang on 2018/1/4.
 */

public class AlertInformationBean {

    /**
     * data : [{"unit":"Kpa","min":"0.0","comments":"压力超高","storageTime":"2017-11-03 15:15:01","parameterId":"63b2fcc6-8d46-11e7-828f-f1bfe84dc7fe","max":"2.0","level":"1","equipmentName":"1#瓶组","stationName":"艾官营气化站","value":"1.83","equipmentId":"41"}]
     * success : true
     * message : 操作成功
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
         * unit : Kpa
         * min : 0.0
         * comments : 压力超高
         * storageTime : 2017-11-03 15:15:01
         * parameterId : 63b2fcc6-8d46-11e7-828f-f1bfe84dc7fe
         * max : 2.0
         * level : 1
         * equipmentName : 1#瓶组
         * stationName : 艾官营气化站
         * value : 1.83
         * equipmentId : 41
         */

        private String unit;
        private float min;
        private String comments;
        private String storageTime;
        private String parameterId;
        private float max;
        private String level;
        private String equipmentName;
        private String stationName;
        private String value;
        private String equipmentId;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public float getMin() {
            return min;
        }

        public void setMin(float min) {
            this.min = min;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getStorageTime() {
            return storageTime;
        }

        public void setStorageTime(String storageTime) {
            this.storageTime = storageTime;
        }

        public String getParameterId() {
            return parameterId;
        }

        public void setParameterId(String parameterId) {
            this.parameterId = parameterId;
        }

        public float getMax() {
            return max;
        }

        public void setMax(float max) {
            this.max = max;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
        }
    }
}
