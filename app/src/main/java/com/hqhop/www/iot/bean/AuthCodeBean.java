package com.hqhop.www.iot.bean;

/**
 * Created by allen on 2017/8/28.
 */

public class AuthCodeBean {
    /**
     * data : {"oauthzCode":"a56c85006af16ff4e1b98c3b5d893417","qrCodeId":"ceb8549043fa4719989da6b4472f28f3","userId":"40288e81567d599a01567d5b60310000","status":"confirm"}
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
         * oauthzCode : a56c85006af16ff4e1b98c3b5d893417
         * qrCodeId : ceb8549043fa4719989da6b4472f28f3
         * userId : 40288e81567d599a01567d5b60310000
         * status : confirm
         */

        private String oauthzCode;
        private String qrCodeId;
        private String userId;
        private String status;

        public String getOauthzCode() {
            return oauthzCode;
        }

        public void setOauthzCode(String oauthzCode) {
            this.oauthzCode = oauthzCode;
        }

        public String getQrCodeId() {
            return qrCodeId;
        }

        public void setQrCodeId(String qrCodeId) {
            this.qrCodeId = qrCodeId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
