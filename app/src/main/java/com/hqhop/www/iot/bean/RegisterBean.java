package com.hqhop.www.iot.bean;

/**
 * Created by allenchiang on 2017/11/3.
 */

public class RegisterBean {

    /**
     * data : {"msg":null,"lastLogin":"2017-11-03 10:06:22","lastLoginClient":null,"name":"18582368329","wechatId":null,"id":"4028417c5f7f9ae7015f7fa10fb70000","email":null,"invitationCode":"36xhgc","lastLoginIp":null,"telnum":"18582368329","username":"18582368329"}
     * success : true
     * message : 添加用户成功
     * version : 1
     */

//    private DataBean data;
    private boolean success;
    private String message;
    private int version;

//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }

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

//    public static class DataBean {
//        /**
//         * msg : null
//         * lastLogin : 2017-11-03 10:06:22
//         * lastLoginClient : null
//         * name : 18582368329
//         * wechatId : null
//         * id : 4028417c5f7f9ae7015f7fa10fb70000
//         * email : null
//         * invitationCode : 36xhgc
//         * lastLoginIp : null
//         * telnum : 18582368329
//         * username : 18582368329
//         */
//
//        private Object msg;
//        private String lastLogin;
//        private Object lastLoginClient;
//        private String name;
//        private Object wechatId;
//        private String id;
//        private Object email;
//        private String invitationCode;
//        private Object lastLoginIp;
//        private String telnum;
//        private String username;
//
//        public Object getMsg() {
//            return msg;
//        }
//
//        public void setMsg(Object msg) {
//            this.msg = msg;
//        }
//
//        public String getLastLogin() {
//            return lastLogin;
//        }
//
//        public void setLastLogin(String lastLogin) {
//            this.lastLogin = lastLogin;
//        }
//
//        public Object getLastLoginClient() {
//            return lastLoginClient;
//        }
//
//        public void setLastLoginClient(Object lastLoginClient) {
//            this.lastLoginClient = lastLoginClient;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public Object getWechatId() {
//            return wechatId;
//        }
//
//        public void setWechatId(Object wechatId) {
//            this.wechatId = wechatId;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public Object getEmail() {
//            return email;
//        }
//
//        public void setEmail(Object email) {
//            this.email = email;
//        }
//
//        public String getInvitationCode() {
//            return invitationCode;
//        }
//
//        public void setInvitationCode(String invitationCode) {
//            this.invitationCode = invitationCode;
//        }
//
//        public Object getLastLoginIp() {
//            return lastLoginIp;
//        }
//
//        public void setLastLoginIp(Object lastLoginIp) {
//            this.lastLoginIp = lastLoginIp;
//        }
//
//        public String getTelnum() {
//            return telnum;
//        }
//
//        public void setTelnum(String telnum) {
//            this.telnum = telnum;
//        }
//
//        public String getUsername() {
//            return username;
//        }
//
//        public void setUsername(String username) {
//            this.username = username;
//        }
//    }
}
