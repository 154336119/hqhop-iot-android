package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by allen on 2017/8/18.
 */

public class FollowDataBean implements Parcelable {

    /**
     * data : [{"leaderMobile":null,"leaderName":null,"latitude":"30.6693435080","stationName":"成都十陵CNG站","parameters":[{"configName":"压力传感器","unit":"1","configId":"57","alarm":null,"equipmentId":"25","value":null},{"configName":"储罐根部阀状态2（气象）","unit":"1","configId":"26","alarm":null,"equipmentId":"e5501172-9781-11e7-8346-28d24440ddb7","value":null}],"stationImag":null,"stationOnline":1,"longitude":"104.1713912216","stationId":9},{"leaderMobile":"18011751111","leaderName":"张三","latitude":"40.48736","stationName":"艾官营气化站","parameters":[{"configName":"压力","unit":"MPa","configId":"5db79484-8d47-11e7-828f-f1bfe84dc7fe","alarm":null,"equipmentId":"444","value":"0.99"},{"configName":"液位","unit":"mm","configId":"6248d8da-8d35-11e7-828f-f1bfe84dc7fe","alarm":null,"equipmentId":"42","value":"80.0"},{"configName":"出口压力","unit":"MPa","configId":"624ae2e2-8d35-11e7-828f-f1bfe84dc7fe","alarm":null,"equipmentId":"49","value":"0.36"},{"configName":"仪表风压力","unit":"MPa","configId":"624af1c4-8d35-11e7-828f-f1bfe84dc7fe","alarm":null,"equipmentId":"40","value":null}],"stationImag":"cng.jpg","stationOnline":1,"longitude":"116.101452","stationId":727}]
     * success : true
     * message : 成功
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
         * leaderMobile : null
         * leaderName : null
         * latitude : 30.6693435080
         * stationName : 成都十陵CNG站
         * parameters : [{"configName":"压力传感器","unit":"1","configId":"57","alarm":null,"equipmentId":"25","value":null},{"configName":"储罐根部阀状态2（气象）","unit":"1","configId":"26","alarm":null,"equipmentId":"e5501172-9781-11e7-8346-28d24440ddb7","value":null}]
         * stationImag : null
         * stationOnline : 1
         * longitude : 104.1713912216
         * stationId : 9
         */

        private String leaderMobile;
        private String leaderName;
        private String latitude;
        private String stationName;
        private String stationImag;
        private int stationOnline;
        private String longitude;
        private int stationId;
        private List<ParametersBean> parameters;

        public String getLeaderMobile() {
            return leaderMobile;
        }

        public void setLeaderMobile(String leaderMobile) {
            this.leaderMobile = leaderMobile;
        }

        public String getLeaderName() {
            return leaderName;
        }

        public void setLeaderName(String leaderName) {
            this.leaderName = leaderName;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getStationImag() {
            return stationImag;
        }

        public void setStationImag(String stationImag) {
            this.stationImag = stationImag;
        }

        public int getStationOnline() {
            return stationOnline;
        }

        public void setStationOnline(int stationOnline) {
            this.stationOnline = stationOnline;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public int getStationId() {
            return stationId;
        }

        public void setStationId(int stationId) {
            this.stationId = stationId;
        }

        public List<ParametersBean> getParameters() {
            return parameters;
        }

        public void setParameters(List<ParametersBean> parameters) {
            this.parameters = parameters;
        }

        public static class ParametersBean implements Parcelable {
            /**
             * configName : 压力传感器
             * unit : 1
             * configId : 57
             * alarm : null
             * equipmentId : 25
             * value : null
             */

            private String configName;
            private String unit;
            private String configId;
            private String alarm;
            private String equipmentId;
            private String value;

            public String getConfigName() {
                return configName;
            }

            public void setConfigName(String configName) {
                this.configName = configName;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getConfigId() {
                return configId;
            }

            public void setConfigId(String configId) {
                this.configId = configId;
            }

            public String getAlarm() {
                return alarm;
            }

            public void setAlarm(String alarm) {
                this.alarm = alarm;
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
                dest.writeString(this.configName);
                dest.writeString(this.unit);
                dest.writeString(this.configId);
                dest.writeString(this.alarm);
                dest.writeString(this.equipmentId);
                dest.writeString(this.value);
            }

            public ParametersBean() {
            }

            protected ParametersBean(Parcel in) {
                this.configName = in.readString();
                this.unit = in.readString();
                this.configId = in.readString();
                this.alarm = in.readString();
                this.equipmentId = in.readString();
                this.value = in.readString();
            }

            public static final Parcelable.Creator<ParametersBean> CREATOR = new Parcelable.Creator<ParametersBean>() {
                @Override
                public ParametersBean createFromParcel(Parcel source) {
                    return new ParametersBean(source);
                }

                @Override
                public ParametersBean[] newArray(int size) {
                    return new ParametersBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.leaderMobile);
            dest.writeString(this.leaderName);
            dest.writeString(this.latitude);
            dest.writeString(this.stationName);
            dest.writeString(this.stationImag);
            dest.writeInt(this.stationOnline);
            dest.writeString(this.longitude);
            dest.writeInt(this.stationId);
            dest.writeTypedList(this.parameters);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.leaderMobile = in.readString();
            this.leaderName = in.readString();
            this.latitude = in.readString();
            this.stationName = in.readString();
            this.stationImag = in.readString();
            this.stationOnline = in.readInt();
            this.longitude = in.readString();
            this.stationId = in.readInt();
            this.parameters = in.createTypedArrayList(ParametersBean.CREATOR);
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

    public FollowDataBean() {
    }

    protected FollowDataBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<FollowDataBean> CREATOR = new Parcelable.Creator<FollowDataBean>() {
        @Override
        public FollowDataBean createFromParcel(Parcel source) {
            return new FollowDataBean(source);
        }

        @Override
        public FollowDataBean[] newArray(int size) {
            return new FollowDataBean[size];
        }
    };
}
