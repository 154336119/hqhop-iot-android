package com.hqhop.www.iot.bean;

/**
 * Created by allen on 2017/8/18.
 */

public class UpdateDataBean  {

    /**
     * data : {"explain":"系统优化，定期版本升级","serverVersion":"2.0","appVersion":"1.0","isUpgrade":"1","url":"/apk/app-debug.apk"}
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
         * explain : 系统优化，定期版本升级
         * serverVersion : 2.0
         * appVersion : 1.0
         * isUpgrade : 1
         * url : /apk/app-debug.apk
         */

        private String explain;
        private String serverVersion;
        private String appVersion;
        private int isUpgrade;
        private String url;

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getServerVersion() {
            return serverVersion;
        }

        public void setServerVersion(String serverVersion) {
            this.serverVersion = serverVersion;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public int getIsUpgrade() {
            return isUpgrade;
        }

        public void setIsUpgrade(int isUpgrade) {
            this.isUpgrade = isUpgrade;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
