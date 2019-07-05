package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by allen on 2017/8/17.
 */

public class AllAlertBean implements Parcelable {

    /**
     * data : [{"unit":"%","comments":"浓度高","min":1,"parameterId":"624ac424-8d35-11e7-828f-f1bfe84dc7fe","storageTime":"2017-10-11 04:25:38","level":"1","max":1,"equipmentName":"燃气探头1","stationName":"艾官营气化站","equipmentId":"47","value":"99.00"},{"unit":"MPa","comments":"压力超高","min":1,"parameterId":"6248f770-8d35-11e7-828f-f1bfe84dc7fe","storageTime":"2017-10-11 04:25:38","level":"1","max":1,"equipmentName":"2#瓶组","stationName":"艾官营气化站","equipmentId":"42","value":"4.85"}]
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
         * unit : %
         * comments : 浓度高
         * min : 1
         * parameterId : 624ac424-8d35-11e7-828f-f1bfe84dc7fe
         * storageTime : 2017-10-11 04:25:38
         * level : 1
         * max : 1
         * equipmentName : 燃气探头1
         * stationName : 艾官营气化站
         * equipmentId : 47
         * value : 99.00
         */

        private String unit;
        private String comments;
        private int min;
        private String parameterId;
        private String storageTime;
        private String level;
        private int max;
        private String equipmentName;
        private String stationName;
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

        public String getParameterId() {
            return parameterId;
        }

        public void setParameterId(String parameterId) {
            this.parameterId = parameterId;
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

        public String getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
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
            dest.writeString(this.parameterId);
            dest.writeString(this.storageTime);
            dest.writeString(this.level);
            dest.writeInt(this.max);
            dest.writeString(this.equipmentName);
            dest.writeString(this.stationName);
            dest.writeString(this.equipmentId);
            dest.writeString(this.value);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.unit = in.readString();
            this.comments = in.readString();
            this.min = in.readInt();
            this.parameterId = in.readString();
            this.storageTime = in.readString();
            this.level = in.readString();
            this.max = in.readInt();
            this.equipmentName = in.readString();
            this.stationName = in.readString();
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
        dest.writeTypedList(this.data);
    }

    public AllAlertBean() {
    }

    protected AllAlertBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<AllAlertBean> CREATOR = new Parcelable.Creator<AllAlertBean>() {
        @Override
        public AllAlertBean createFromParcel(Parcel source) {
            return new AllAlertBean(source);
        }

        @Override
        public AllAlertBean[] newArray(int size) {
            return new AllAlertBean[size];
        }
    };
}
