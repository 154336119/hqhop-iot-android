package com.hqhop.www.iot.bean;

/**
 * Created by allen on 2017/9/22.
 */

public class StationInforBean {
    /**
     * data : {"address":"北京市延庆县","leaderMobile":"18011751111","regionMap":"","latitude":"40.48736","operateTime":"","delFlag":"1","type":"gas","operator":"","stationImg":"cng.jpg","sale":null,"leaderName":"张三","name":"艾官营气化站","checked":false,"id":727,"region":null,"longitude":"116.101452"}
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
         * address : 北京市延庆县
         * leaderMobile : 18011751111
         * regionMap :
         * latitude : 40.48736
         * operateTime :
         * delFlag : 1
         * type : gas
         * operator :
         * stationImg : cng.jpg
         * sale : null
         * leaderName : 张三
         * name : 艾官营气化站
         * checked : false
         * id : 727
         * region : null
         * longitude : 116.101452
         */

        private String address;
        private String leaderMobile;
        private String regionMap;
        private String latitude;
        private String operateTime;
        private String delFlag;
        private String type;
        private String operator;
        private String stationImg;
        private String sale;
        private String leaderName;
        private String name;
        private boolean checked;
        private int id;
        private Object region;
        private String longitude;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLeaderMobile() {
            return leaderMobile;
        }

        public void setLeaderMobile(String leaderMobile) {
            this.leaderMobile = leaderMobile;
        }

        public String getRegionMap() {
            return regionMap;
        }

        public void setRegionMap(String regionMap) {
            this.regionMap = regionMap;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(String operateTime) {
            this.operateTime = operateTime;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getStationImg() {
            return stationImg;
        }

        public void setStationImg(String stationImg) {
            this.stationImg = stationImg;
        }

        public String getSale() {
            return sale;
        }

        public void setSale(String sale) {
            this.sale = sale;
        }

        public String getLeaderName() {
            return leaderName;
        }

        public void setLeaderName(String leaderName) {
            this.leaderName = leaderName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getRegion() {
            return region;
        }

        public void setRegion(Object region) {
            this.region = region;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
