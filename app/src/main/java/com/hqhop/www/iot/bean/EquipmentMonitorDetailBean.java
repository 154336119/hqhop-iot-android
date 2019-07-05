package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allen on 2017/8/24.
 */

public class EquipmentMonitorDetailBean implements Parcelable {

    /**
     * data : {"equipId":"297edb225da1fc19015da1fef28f0002","alarmMax":"null","charts":{},"equipName":"仪表风","assetCode":"12","absoluteMin":"1.0","buyDate":"2017-08-10 08:00:00","baoYangData":"2017-08-10","manufacturer":"12","absoluteMax":"1.0","alarmMin":"null","productionDate":"2017-08-04","price":"1","value":null}
     * success : true
     * message : 查询成功
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
        /**
         * equipId : 297edb225da1fc19015da1fef28f0002
         * alarmMax : null
         * charts : {}
         * equipName : 仪表风
         * assetCode : 12
         * absoluteMin : 1.0
         * buyDate : 2017-08-10 08:00:00
         * baoYangData : 2017-08-10
         * manufacturer : 12
         * absoluteMax : 1.0
         * alarmMin : null
         * productionDate : 2017-08-04
         * price : 1
         * value : null
         */

        private String equipId;
        private String alarmMax;
        private ChartsBean charts;
        private String equipName;
        private String assetCode;
        private String absoluteMin;
        private String buyDate;
        private String baoYangData;
        private String manufacturer;
        private String absoluteMax;
        private String alarmMin;
        private String productionDate;
        private String price;
        private String value;

        public String getEquipId() {
            return equipId;
        }

        public void setEquipId(String equipId) {
            this.equipId = equipId;
        }

        public String getAlarmMax() {
            return alarmMax;
        }

        public void setAlarmMax(String alarmMax) {
            this.alarmMax = alarmMax;
        }

        public ChartsBean getCharts() {
            return charts;
        }

        public void setCharts(ChartsBean charts) {
            this.charts = charts;
        }

        public String getEquipName() {
            return equipName;
        }

        public void setEquipName(String equipName) {
            this.equipName = equipName;
        }

        public String getAssetCode() {
            return assetCode;
        }

        public void setAssetCode(String assetCode) {
            this.assetCode = assetCode;
        }

        public String getAbsoluteMin() {
            return absoluteMin;
        }

        public void setAbsoluteMin(String absoluteMin) {
            this.absoluteMin = absoluteMin;
        }

        public String getBuyDate() {
            return buyDate;
        }

        public void setBuyDate(String buyDate) {
            this.buyDate = buyDate;
        }

        public String getBaoYangData() {
            return baoYangData;
        }

        public void setBaoYangData(String baoYangData) {
            this.baoYangData = baoYangData;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getAbsoluteMax() {
            return absoluteMax;
        }

        public void setAbsoluteMax(String absoluteMax) {
            this.absoluteMax = absoluteMax;
        }

        public String getAlarmMin() {
            return alarmMin;
        }

        public void setAlarmMin(String alarmMin) {
            this.alarmMin = alarmMin;
        }

        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static class ChartsBean implements Parcelable {
            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
            }

            public ChartsBean() {
            }

            protected ChartsBean(Parcel in) {
            }

            public static final Parcelable.Creator<ChartsBean> CREATOR = new Parcelable.Creator<ChartsBean>() {
                @Override
                public ChartsBean createFromParcel(Parcel source) {
                    return new ChartsBean(source);
                }

                @Override
                public ChartsBean[] newArray(int size) {
                    return new ChartsBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.equipId);
            dest.writeString(this.alarmMax);
            dest.writeParcelable(this.charts, flags);
            dest.writeString(this.equipName);
            dest.writeString(this.assetCode);
            dest.writeString(this.absoluteMin);
            dest.writeString(this.buyDate);
            dest.writeString(this.baoYangData);
            dest.writeString(this.manufacturer);
            dest.writeString(this.absoluteMax);
            dest.writeString(this.alarmMin);
            dest.writeString(this.productionDate);
            dest.writeString(this.price);
            dest.writeString(this.value);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.equipId = in.readString();
            this.alarmMax = in.readString();
            this.charts = in.readParcelable(ChartsBean.class.getClassLoader());
            this.equipName = in.readString();
            this.assetCode = in.readString();
            this.absoluteMin = in.readString();
            this.buyDate = in.readString();
            this.baoYangData = in.readString();
            this.manufacturer = in.readString();
            this.absoluteMax = in.readString();
            this.alarmMin = in.readString();
            this.productionDate = in.readString();
            this.price = in.readString();
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
        dest.writeParcelable(this.data, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeString(this.message);
        dest.writeInt(this.version);
    }

    public EquipmentMonitorDetailBean() {
    }

    protected EquipmentMonitorDetailBean(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
    }

    public static final Parcelable.Creator<EquipmentMonitorDetailBean> CREATOR = new Parcelable.Creator<EquipmentMonitorDetailBean>() {
        @Override
        public EquipmentMonitorDetailBean createFromParcel(Parcel source) {
            return new EquipmentMonitorDetailBean(source);
        }

        @Override
        public EquipmentMonitorDetailBean[] newArray(int size) {
            return new EquipmentMonitorDetailBean[size];
        }
    };
}
