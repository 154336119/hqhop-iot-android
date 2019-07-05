package com.hqhop.www.iot.bean;

import java.util.List;

/**
 * Created by allen on 2017/9/10.
 */

public class EventsBean {

    /**
     * data : [{"storageTime":"2017-11-02 23:17:29","parameterId":"4028fe815e98345b015e9954fcd90006","level":"","latitude":"22.732343","stationName":"佛山均荷LNG站","time":"2017-11-02 23:17:29","id":"0384450f-ee08-440e-9da4-0fe9d3b4af3d","category":"alarm_clear","value":"0.79","content":"设备:1#储罐 参数:压力 值:0.84MPa->0.79MPa","longitude":"113.116062","stationId":"18"},{"storageTime":"2017-11-02 20:02:36","parameterId":"4028fe815e98345b015e9954fcd90006","level":"2","latitude":"22.732343","stationName":"佛山均荷LNG站","time":"2017-11-02 20:02:36","id":"1908146d-74ac-4c53-a3c6-b7c9ab379c50","category":"alarm","value":"0.80","content":"设备:1#储罐 参数:压力 值:0.79MPa->0.80MPa","longitude":"113.116062","stationId":"18"},{"leaderMobile":"18011751111","storageTime":"2017-10-31 11:34:44","parameterId":"624ac424-8d35-11e7-828f-f1bfe84dc7fe","level":"1","latitude":"40.48736","content":"设备:燃气探头1 参数:浓度 值:0.00%->95.00%","leaderName":"张三","stationName":"艾官营气化站","time":"2017-10-31 11:34:44","id":"92004530-a9e6-4896-83d9-d328ce09f511","category":"alarm","value":"95.00","longitude":"116.101452","stationId":"727"},{"leaderMobile":"18011751111","storageTime":"2017-10-31 11:34:44","parameterId":"","level":"","latitude":"40.48736","content":"卸液中,运行中->泄漏事故,运行中,","leaderName":"张三","stationName":"艾官营气化站","time":"2017-10-31 11:34:44","id":"de0c9bf8-2292-461d-830a-9aede0d6268b","category":"scene","value":"泄漏事故,运行中,","longitude":"116.101452","stationId":"727"},{"leaderMobile":"18011751111","storageTime":"2017-10-31 11:34:37","parameterId":"6248d8da-8d35-11e7-828f-f1bfe84dc7fe","level":"","latitude":"40.48736","content":"设备:2#瓶组 参数:液位 值:0.00mm->90.00mm","leaderName":"张三","stationName":"艾官营气化站","time":"2017-10-31 11:34:37","id":"5e33c3d0-b372-4f4d-bb8a-9007ef9176c8","category":"alarm_clear","value":"90.00","longitude":"116.101452","stationId":"727"},{"leaderMobile":"18011751111","storageTime":"2017-10-31 11:26:07","parameterId":"63b2fcc6-8d46-11e7-828f-f1bfe84dc7fe","level":"1","latitude":"40.48736","content":"设备:1#瓶组 参数:压力 值:0.20Kpa->0.00Kpa","leaderName":"张三","stationName":"艾官营气化站","time":"2017-10-31 11:26:07","id":"2410184b-ec39-4eb9-8303-81327621b53f","category":"alarm","value":"0.00","longitude":"116.101452","stationId":"727"},{"leaderMobile":"18011751111","storageTime":"2017-10-31 11:26:07","parameterId":"624ac424-8d35-11e7-828f-f1bfe84dc7fe","level":"","latitude":"40.48736","content":"设备:燃气探头1 参数:浓度 值:90.00%->0.00%","leaderName":"张三","stationName":"艾官营气化站","time":"2017-10-31 11:26:07","id":"744ca9c6-63ad-4fad-9d5f-7308e29d2760","category":"alarm_clear","value":"0.00","longitude":"116.101452","stationId":"727"},{"leaderMobile":"18011751111","storageTime":"2017-10-31 11:26:07","parameterId":"6248f770-8d35-11e7-828f-f1bfe84dc7fe","level":"1","latitude":"40.48736","content":"设备:2#瓶组 参数:压力 值:0.58MPa->0.00MPa","leaderName":"张三","stationName":"艾官营气化站","time":"2017-10-31 11:26:07","id":"6c47e270-2338-41df-a910-19259aae125e","category":"alarm","value":"0.00","longitude":"116.101452","stationId":"727"},{"leaderMobile":"18011751111","storageTime":"2017-10-31 11:26:07","parameterId":"","level":"","latitude":"40.48736","content":"泄漏事故,运行中->卸液中,运行中,","leaderName":"张三","stationName":"艾官营气化站","time":"2017-10-31 11:26:07","id":"e8f15bdc-9bc3-465c-a470-4b93e06858bb","category":"scene","value":"卸液中,运行中,","longitude":"116.101452","stationId":"727"},{"storageTime":"2017-10-30 23:20:45","parameterId":"4028fe815e98345b015e9954fcd90006","level":"","latitude":"22.732343","stationName":"佛山均荷LNG站","time":"2017-10-30 23:20:45","id":"73068d3f-cfde-425d-a4d4-4134aad62861","category":"alarm_clear","value":"0.77","content":"设备:1#储罐 参数:压力 值:0.81MPa->0.77MPa","longitude":"113.116062","stationId":"18"},{"storageTime":"2017-10-30 22:37:14","parameterId":"4028fe815e98345b015e9954fcd90006","level":"2","latitude":"22.732343","stationName":"佛山均荷LNG站","time":"2017-10-30 22:37:14","id":"78df1f60-f966-4993-bcfb-34f6421a9537","category":"alarm","value":"0.80","content":"设备:1#储罐 参数:压力 值:0.79MPa->0.80MPa","longitude":"113.116062","stationId":"18"},{"storageTime":"2017-10-30 22:36:39","parameterId":"4028fe815e98345b015e9954fcd90006","level":"","latitude":"22.732343","stationName":"佛山均荷LNG站","time":"2017-10-30 22:36:39","id":"a4c8a891-a862-4fa0-b0f6-5bd4a65283e1","category":"alarm_clear","value":"0.79","content":"设备:1#储罐 参数:压力 值:0.80MPa->0.79MPa","longitude":"113.116062","stationId":"18"},{"storageTime":"2017-10-30 22:36:16","parameterId":"4028fe815e98345b015e9954fcd90006","level":"2","latitude":"22.732343","stationName":"佛山均荷LNG站","time":"2017-10-30 22:36:16","id":"b62b821e-66d3-43ff-867f-83e77816bd71","category":"alarm","value":"0.80","content":"设备:1#储罐 参数:压力 值:0.79MPa->0.80MPa","longitude":"113.116062","stationId":"18"},{"storageTime":"2017-10-30 22:36:04","parameterId":"4028fe815e98345b015e9954fcd90006","level":"","latitude":"22.732343","stationName":"佛山均荷LNG站","time":"2017-10-30 22:36:04","id":"11388404-45ed-41d4-8283-dbf6884cfe37","category":"alarm_clear","value":"0.79","content":"设备:1#储罐 参数:压力 值:0.80MPa->0.79MPa","longitude":"113.116062","stationId":"18"},{"storageTime":"2017-10-24 13:35:51","parameterId":"4028fe815e98345b015e9954fcd90006","level":"2","latitude":"22.732343","stationName":"佛山均荷LNG站","time":"2017-10-24 13:35:51","id":"30c5cedf-faaf-413c-8b35-6b3103a9de6a","category":"alarm","value":"0.80","content":"设备:1#储罐 参数:压力 值:0.79MPa->0.80MPa","longitude":"113.116062","stationId":"18"},{"leaderMobile":"18011751111","storageTime":"2017-10-23 18:31:15","parameterId":"63b2fcc6-8d46-11e7-828f-f1bfe84dc7fe","level":"","latitude":"40.48736","content":"设备:1#瓶组 参数:压力 值:1.80Kpa->0.20Kpa","leaderName":"张三","stationName":"艾官营气化站","time":"2017-10-23 18:31:15","id":"d88021dc-c927-498c-b919-6a644b43e54d","category":"alarm_clear","value":"0.20","longitude":"116.101452","stationId":"727"},{"leaderMobile":"18011751111","storageTime":"2017-10-23 18:30:22","parameterId":"6248d8da-8d35-11e7-828f-f1bfe84dc7fe","level":"1","latitude":"40.48736","content":"设备:2#瓶组 参数:液位 值:99.00mm->0.50mm","leaderName":"张三","stationName":"艾官营气化站","time":"2017-10-23 18:30:22","id":"58cba92f-3565-4386-a3b9-e1c82dc2ae78","category":"alarm","value":"0.50","longitude":"116.101452","stationId":"727"},{"leaderMobile":"18011751111","storageTime":"2017-10-23 18:29:22","parameterId":"63b2fcc6-8d46-11e7-828f-f1bfe84dc7fe","level":"1","latitude":"40.48736","content":"设备:1#瓶组 参数:压力 值:0.43Kpa->1.80Kpa","leaderName":"张三","stationName":"艾官营气化站","time":"2017-10-23 18:29:22","id":"1f33f86d-ae13-4655-b49a-55e2cb0a2902","category":"alarm","value":"1.80","longitude":"116.101452","stationId":"727"}]
     * success : true
     * message : 成功
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
         * storageTime : 2017-11-02 23:17:29
         * parameterId : 4028fe815e98345b015e9954fcd90006
         * level :
         * latitude : 22.732343
         * stationName : 佛山均荷LNG站
         * time : 2017-11-02 23:17:29
         * id : 0384450f-ee08-440e-9da4-0fe9d3b4af3d
         * category : alarm_clear
         * value : 0.79
         * content : 设备:1#储罐 参数:压力 值:0.84MPa->0.79MPa
         * longitude : 113.116062
         * stationId : 18
         * leaderMobile : 18011751111
         * leaderName : 张三
         */

        private String storageTime;
        private String parameterId;
        private String level;
        private String latitude;
        private String stationName;
        private String time;
        private String id;
        private String category;
        private String value;
        private String content;
        private String longitude;
        private String stationId;
        private String leaderMobile;
        private String leaderName;

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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getStationId() {
            return stationId;
        }

        public void setStationId(String stationId) {
            this.stationId = stationId;
        }

        public String getLeaderMobile() {
            return leaderMobile;
        }

        public void setLeaderMobile(String leaderMobile) {
            this.leaderMobile = leaderMobile;
        }

        public String getLeaderName() {
            return leaderName;
        }

        public void setLeaderName(String leaderName) {
            this.leaderName = leaderName;
        }
    }
}
