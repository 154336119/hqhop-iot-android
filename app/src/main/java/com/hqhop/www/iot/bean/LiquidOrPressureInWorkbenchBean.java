package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by allen on 2017/8/16.
 */

public class LiquidOrPressureInWorkbenchBean implements Parcelable {
    /**
     * data : [{"unit":"mm","comments":"储罐2液位","storageTime":"2017-08-19 10:11:11","stationName":"重庆欣雨子公司","value":"0.64","stationId":58}]
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
         * unit : mm
         * comments : 储罐2液位
         * storageTime : 2017-08-19 10:11:11
         * stationName : 重庆欣雨子公司
         * value : 0.64
         * stationId : 58
         */

        private String unit;
        private String comments;
        private String storageTime;
        private String stationName;
        private String value;
        private int stationId;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getStorageTime() {
            return storageTime;
        }

        public void setStorageTime(String storageTime) {
            this.storageTime = storageTime;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getStationId() {
            return stationId;
        }

        public void setStationId(int stationId) {
            this.stationId = stationId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.unit);
            dest.writeString(this.comments);
            dest.writeString(this.storageTime);
            dest.writeString(this.stationName);
            dest.writeString(this.value);
            dest.writeInt(this.stationId);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.unit = in.readString();
            this.comments = in.readString();
            this.storageTime = in.readString();
            this.stationName = in.readString();
            this.value = in.readString();
            this.stationId = in.readInt();
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

    public LiquidOrPressureInWorkbenchBean() {
    }

    protected LiquidOrPressureInWorkbenchBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<LiquidOrPressureInWorkbenchBean> CREATOR = new Parcelable.Creator<LiquidOrPressureInWorkbenchBean>() {
        @Override
        public LiquidOrPressureInWorkbenchBean createFromParcel(Parcel source) {
            return new LiquidOrPressureInWorkbenchBean(source);
        }

        @Override
        public LiquidOrPressureInWorkbenchBean[] newArray(int size) {
            return new LiquidOrPressureInWorkbenchBean[size];
        }
    };
}
