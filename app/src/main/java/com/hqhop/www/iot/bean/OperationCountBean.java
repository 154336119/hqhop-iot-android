package com.hqhop.www.iot.bean;

import java.util.List;

/**
 * Created by allen on 10/26/2017.
 */

public class OperationCountBean {


    /**
     * data : {"data":[{"noticeNo":"0001","category":"update","content":"公告内容","noticeTitle":"公告1"}],"iosVersion":"2.2.0"}
     * success : true
     * message : 操作成功
     * version : 1
     */

    private DataBeanX data;
    private boolean success;
    private String message;
    private int version;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
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

    public static class DataBeanX {
        /**
         * data : [{"noticeNo":"0001","category":"update","content":"公告内容","noticeTitle":"公告1"}]
         * iosVersion : 2.2.0
         */

        private String iosVersion;
        private List<DataBean> data;

        public String getIosVersion() {
            return iosVersion;
        }

        public void setIosVersion(String iosVersion) {
            this.iosVersion = iosVersion;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * noticeNo : 0001
             * category : update
             * content : 公告内容
             * noticeTitle : 公告1
             */

            private String noticeNo;
            private String category;
            private String content;
            private String noticeTitle;

            public String getNoticeNo() {
                return noticeNo;
            }

            public void setNoticeNo(String noticeNo) {
                this.noticeNo = noticeNo;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getNoticeTitle() {
                return noticeTitle;
            }

            public void setNoticeTitle(String noticeTitle) {
                this.noticeTitle = noticeTitle;
            }
        }
    }
}
