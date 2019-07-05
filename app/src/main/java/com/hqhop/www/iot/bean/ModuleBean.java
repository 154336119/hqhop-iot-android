package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by allen on 2017/8/18.
 */

public class ModuleBean implements Parcelable {

    /**
     * data : {"unSelected":[{"configName":"仪表风压力","configId":"297edb225d9ddea2015d9de7b7450005","stationId":58},{"configName":"储罐2液位","configId":"297edb225da2023e015da20546f70002","stationId":58}],"selected":[{"configName":"储罐2压力","configId":"123123","stationId":58},{"configName":"仪表风温度","configId":"QWE12","stationId":58}]}
     * success : true
     * message : 成功
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

    public static class DataBean implements Parcelable {
        private List<UnSelectedBean> unSelected;
        private List<SelectedBean> selected;

        public List<UnSelectedBean> getUnSelected() {
            return unSelected;
        }

        public void setUnSelected(List<UnSelectedBean> unSelected) {
            this.unSelected = unSelected;
        }

        public List<SelectedBean> getSelected() {
            return selected;
        }

        public void setSelected(List<SelectedBean> selected) {
            this.selected = selected;
        }

        public static class UnSelectedBean implements Parcelable {
            /**
             * configName : 仪表风压力
             * configId : 297edb225d9ddea2015d9de7b7450005
             * stationId : 58
             */

            private String configName;
            private String configId;
            private int stationId;

            public String getConfigName() {
                return configName;
            }

            public void setConfigName(String configName) {
                this.configName = configName;
            }

            public String getConfigId() {
                return configId;
            }

            public void setConfigId(String configId) {
                this.configId = configId;
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
                dest.writeString(this.configName);
                dest.writeString(this.configId);
                dest.writeInt(this.stationId);
            }

            public UnSelectedBean() {
            }

            protected UnSelectedBean(Parcel in) {
                this.configName = in.readString();
                this.configId = in.readString();
                this.stationId = in.readInt();
            }

            public static final Parcelable.Creator<UnSelectedBean> CREATOR = new Parcelable.Creator<UnSelectedBean>() {
                @Override
                public UnSelectedBean createFromParcel(Parcel source) {
                    return new UnSelectedBean(source);
                }

                @Override
                public UnSelectedBean[] newArray(int size) {
                    return new UnSelectedBean[size];
                }
            };
        }

        public static class SelectedBean implements Parcelable {
            /**
             * configName : 储罐2压力
             * configId : 123123
             * stationId : 58
             */

            private String configName;
            private String configId;
            private int stationId;

            public String getConfigName() {
                return configName;
            }

            public void setConfigName(String configName) {
                this.configName = configName;
            }

            public String getConfigId() {
                return configId;
            }

            public void setConfigId(String configId) {
                this.configId = configId;
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
                dest.writeString(this.configName);
                dest.writeString(this.configId);
                dest.writeInt(this.stationId);
            }

            public SelectedBean() {
            }

            protected SelectedBean(Parcel in) {
                this.configName = in.readString();
                this.configId = in.readString();
                this.stationId = in.readInt();
            }

            public static final Parcelable.Creator<SelectedBean> CREATOR = new Parcelable.Creator<SelectedBean>() {
                @Override
                public SelectedBean createFromParcel(Parcel source) {
                    return new SelectedBean(source);
                }

                @Override
                public SelectedBean[] newArray(int size) {
                    return new SelectedBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.unSelected);
            dest.writeTypedList(this.selected);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.unSelected = in.createTypedArrayList(UnSelectedBean.CREATOR);
            this.selected = in.createTypedArrayList(SelectedBean.CREATOR);
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
        dest.writeParcelable(this.data, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeString(this.message);
        dest.writeInt(this.version);
    }

    public ModuleBean() {
    }

    protected ModuleBean(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
    }

    public static final Parcelable.Creator<ModuleBean> CREATOR = new Parcelable.Creator<ModuleBean>() {
        @Override
        public ModuleBean createFromParcel(Parcel source) {
            return new ModuleBean(source);
        }

        @Override
        public ModuleBean[] newArray(int size) {
            return new ModuleBean[size];
        }
    };
}
