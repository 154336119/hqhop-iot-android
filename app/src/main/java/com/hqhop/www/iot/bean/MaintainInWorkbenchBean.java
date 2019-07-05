package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by allen on 2017/8/16.
 */

public class MaintainInWorkbenchBean implements Parcelable {
    /**
     * data : [{"level":"1","stationName":"龙泉站","id":"1","time":"2017-07-13 19:34","content":"维保信息内容"},{"level":"2","stationName":"龙泉站","id":"2","time":"2017-07-13 19:35","content":"维保信息内容"}]
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

    public static class DataBean implements Parcelable {
        /**
         * level : 1
         * stationName : 龙泉站
         * id : 1
         * time : 2017-07-13 19:34
         * content : 维保信息内容
         */

        private String level;
        private String stationName;
        private String id;
        private String time;
        private String content;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.level);
            dest.writeString(this.stationName);
            dest.writeString(this.id);
            dest.writeString(this.time);
            dest.writeString(this.content);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.level = in.readString();
            this.stationName = in.readString();
            this.id = in.readString();
            this.time = in.readString();
            this.content = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeString(this.message);
        dest.writeInt(this.version);
        dest.writeTypedList(this.data);
    }

    public MaintainInWorkbenchBean() {
    }

    protected MaintainInWorkbenchBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<MaintainInWorkbenchBean> CREATOR = new Parcelable.Creator<MaintainInWorkbenchBean>() {
        @Override
        public MaintainInWorkbenchBean createFromParcel(Parcel source) {
            return new MaintainInWorkbenchBean(source);
        }

        @Override
        public MaintainInWorkbenchBean[] newArray(int size) {
            return new MaintainInWorkbenchBean[size];
        }
    };
}
