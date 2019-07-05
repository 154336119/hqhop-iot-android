package com.hqhop.www.iot.bean;

import java.util.List;

/**
 * Created by allen on 2017/8/15.
 */

public class WorkbenchBean {

    /**
     * data : [{"stationsNumber":3,"stationsOnlineNumber":1,"stationType":"gas","stationImage":null,"stationsAlarmNumber":0},{"stationsNumber":1,"stationsOnlineNumber":0,"stationType":"oil","stationImage":null,"stationsAlarmNumber":0},{"stationsNumber":8,"stationsOnlineNumber":0,"stationType":"station","stationImage":null,"stationsAlarmNumber":0}]
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
         * stationsNumber : 3
         * stationsOnlineNumber : 1
         * stationType : gas
         * stationImage : null
         * stationsAlarmNumber : 0
         */

        private int stationsNumber;
        private int stationsOnlineNumber;
        private String stationType;
        private Object stationImage;
        private int stationsAlarmNumber;

        public int getStationsNumber() {
            return stationsNumber;
        }

        public void setStationsNumber(int stationsNumber) {
            this.stationsNumber = stationsNumber;
        }

        public int getStationsOnlineNumber() {
            return stationsOnlineNumber;
        }

        public void setStationsOnlineNumber(int stationsOnlineNumber) {
            this.stationsOnlineNumber = stationsOnlineNumber;
        }

        public String getStationType() {
            return stationType;
        }

        public void setStationType(String stationType) {
            this.stationType = stationType;
        }

        public Object getStationImage() {
            return stationImage;
        }

        public void setStationImage(Object stationImage) {
            this.stationImage = stationImage;
        }

        public int getStationsAlarmNumber() {
            return stationsAlarmNumber;
        }

        public void setStationsAlarmNumber(int stationsAlarmNumber) {
            this.stationsAlarmNumber = stationsAlarmNumber;
        }
    }
}
