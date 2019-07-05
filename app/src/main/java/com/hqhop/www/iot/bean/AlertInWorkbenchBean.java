package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 2017/8/16.
 */

public class AlertInWorkbenchBean implements Parcelable {

    /**
     * data : [{"unit":"%","comments":"浓度高","min":1,"storageTime":"2017-09-20 03:50:33","level":"1","max":1,"stationName":"艾官营气化站","configurationId":"0027513e-8d3c-11e7-828f-f1bfe84dc7fe","equipmentId":"47","value":"99.0"},{"unit":"mm","comments":"液位过低","min":1,"storageTime":"2017-09-20 03:50:33","level":"1","max":1,"stationName":"艾官营气化站","configurationId":"00273ec4-8d3c-11e7-828f-f1bfe84dc7fe","equipmentId":"42","value":"12.0"}]
     * success : true
     * message : 查询成功
     * version : 1
     */

    private boolean success;
    private String message;
    private int version;
    private List<AllAlertBean.DataBean> data;

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

    public List<AllAlertBean.DataBean> getData() {
        return data;
    }

    public void setData(List<AllAlertBean.DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * unit : %
         * comments : 浓度高
         * min : 1
         * storageTime : 2017-09-20 03:50:33
         * level : 1
         * max : 1
         * stationName : 艾官营气化站
         * configurationId : 0027513e-8d3c-11e7-828f-f1bfe84dc7fe
         * equipmentId : 47
         * value : 99.0
         */

        private String unit;
        private String comments;
        private int min;
        private String storageTime;
        private String level;
        private int max;
        private String stationName;
        private String configurationId;
        private String equipmentId;
        private String value;

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

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public String getStorageTime() {
            return storageTime;
        }

        public void setStorageTime(String storageTime) {
            this.storageTime = storageTime;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getConfigurationId() {
            return configurationId;
        }

        public void setConfigurationId(String configurationId) {
            this.configurationId = configurationId;
        }

        public String getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.unit);
            dest.writeString(this.comments);
            dest.writeInt(this.min);
            dest.writeString(this.storageTime);
            dest.writeString(this.level);
            dest.writeInt(this.max);
            dest.writeString(this.stationName);
            dest.writeString(this.configurationId);
            dest.writeString(this.equipmentId);
            dest.writeString(this.value);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.unit = in.readString();
            this.comments = in.readString();
            this.min = in.readInt();
            this.storageTime = in.readString();
            this.level = in.readString();
            this.max = in.readInt();
            this.stationName = in.readString();
            this.configurationId = in.readString();
            this.equipmentId = in.readString();
            this.value = in.readString();
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
        dest.writeList(this.data);
    }

    public AlertInWorkbenchBean() {
    }

    protected AlertInWorkbenchBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
        this.data = new ArrayList<AllAlertBean.DataBean>();
        in.readList(this.data, AllAlertBean.DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<AlertInWorkbenchBean> CREATOR = new Parcelable.Creator<AlertInWorkbenchBean>() {
        @Override
        public AlertInWorkbenchBean createFromParcel(Parcel source) {
            return new AlertInWorkbenchBean(source);
        }

        @Override
        public AlertInWorkbenchBean[] newArray(int size) {
            return new AlertInWorkbenchBean[size];
        }
    };
}
