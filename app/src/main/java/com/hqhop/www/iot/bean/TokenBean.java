package com.hqhop.www.iot.bean;

/**
 * Created by allen on 2017/7/6.
 */

public class TokenBean {

    /**
     * data : {"access_token":"353eaa3e69179649e5e12afeb5a06b87","refresh_token":"ef2e51323cce71d08001219ef2f9067b","orgName":"华气厚普能源互联网平台","userName":"hptest","expires_in":30,"userId":"40288e81567c9e7f01567cc223440007"}
     * success : true
     * message : success
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
         * access_token : 353eaa3e69179649e5e12afeb5a06b87
         * refresh_token : ef2e51323cce71d08001219ef2f9067b
         * orgName : 华气厚普能源互联网平台
         * userName : hptest
         * expires_in : 30
         * userId : 40288e81567c9e7f01567cc223440007
         */

        private String access_token;
        private String refresh_token;
        private String orgName;
        private String userName;
        private int expires_in;
        private String userId;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
