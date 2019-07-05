package com.hqhop.www.iot.bean;

/**
 * Created by allenchiang on 2017/12/11.
 */

public class FeedbackBean {
    /**
     * data : {"code":"862044037647091","remark":"rhdbdbfbdbfnfn","id":"2c9081826035668a01604597abad0218","time":1512996055981,"type":"android","username":"hptest"}
     * success : true
     * message : 操作成功
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
         * code : 862044037647091
         * remark : rhdbdbfbdbfnfn
         * id : 2c9081826035668a01604597abad0218
         * time : 1512996055981
         * type : android
         * username : hptest
         */

        private String code;
        private String remark;
        private String id;
        private long time;
        private String type;
        private String username;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
