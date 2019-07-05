package com.hqhop.www.iot.bean;

/**
 * Created by allenchiang on 2017/10/31.
 */

public class GatewayBean {

    /**
     * data : {"address":"","memory":"10","latitude":"40.48736","cpu":"50","validityPeriod":"2017-07-14","disk":"1","simId":"22222","simSn":"2","hardwareVersion":null,"protocolVersion":"01","stationName":"艾官营气化站","sn":"BJRQYQTH00040000","flow":"N/A","softwareVersion":"2.0.0","longitude":"116.101452","stationId":727,"status":1}
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
         * address :
         * memory : 10
         * deviceCount : 0
         * latitude : 40.48736
         * cpu : 50
         * validityPeriod : 2017-07-14
         * disk : 1
         * simId : 22222
         * simSn : 2
         * hardwareVersion : null
         * protocolVersion : 01
         * stationName : 艾官营气化站
         * sn : BJRQYQTH00040000
         * flow : N/A
         * softwareVersion : 2.0.0
         * longitude : 116.101452
         * stationId : 727
         * status : 1
         */

        private String address;
        private String memory;
        private int deviceCount;
        private String latitude;
        private String cpu;
        private String validityPeriod;
        private String  disk;
        private String simId;
        private String simSn;
        private String hardwareVersion;
        private String protocolVersion;
        private String stationName;
        private String sn;
        private String flow;
        private String softwareVersion;
        private String longitude;
        private int stationId;
        private int status; // 0 -> 激活, 1 -> 未激活

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMemory() {
            return memory;
        }

        public void setMemory(String memory) {
            this.memory = memory;
        }

        public int getDeviceCount() {
            return deviceCount;
        }

        public void setDeviceCount(int deviceCount) {
            this.deviceCount = deviceCount;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getCpu() {
            return cpu;
        }

        public void setCpu(String cpu) {
            this.cpu = cpu;
        }

        public String getValidityPeriod() {
            return validityPeriod;
        }

        public void setValidityPeriod(String validityPeriod) {
            this.validityPeriod = validityPeriod;
        }

        public String getDisk() {
            return disk;
        }

        public void setDisk(String disk) {
            this.disk = disk;
        }

        public String getSimId() {
            return simId;
        }

        public void setSimId(String simId) {
            this.simId = simId;
        }

        public String getSimSn() {
            return simSn;
        }

        public void setSimSn(String simSn) {
            this.simSn = simSn;
        }

        public String getHardwareVersion() {
            return hardwareVersion;
        }

        public void setHardwareVersion(String hardwareVersion) {
            this.hardwareVersion = hardwareVersion;
        }

        public String getProtocolVersion() {
            return protocolVersion;
        }

        public void setProtocolVersion(String protocolVersion) {
            this.protocolVersion = protocolVersion;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getFlow() {
            return flow;
        }

        public void setFlow(String flow) {
            this.flow = flow;
        }

        public String getSoftwareVersion() {
            return softwareVersion;
        }

        public void setSoftwareVersion(String softwareVersion) {
            this.softwareVersion = softwareVersion;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public int getStationId() {
            return stationId;
        }

        public void setStationId(int stationId) {
            this.stationId = stationId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
