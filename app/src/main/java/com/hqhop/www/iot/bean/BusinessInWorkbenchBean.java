package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by allen on 2017/8/16.
 */

public class BusinessInWorkbenchBean implements Parcelable {
    /**
     * data : [{"monthSale":"469700","todaySale":"5000","stationName":"龙泉站","id":"1","stock":"34567","weekSale":"27689","lastSale":"4800","forecastSale":"3300"}]
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
         * monthSale : 469700
         * todaySale : 5000
         * stationName : 龙泉站
         * id : 1
         * stock : 34567
         * weekSale : 27689
         * lastSale : 4800
         * forecastSale : 3300
         */

        private String monthSale;
        private String todaySale;
        private String stationName;
        private String id;
        private String stock;
        private String weekSale;
        private String lastSale;
        private String forecastSale;

        public String getMonthSale() {
            return monthSale;
        }

        public void setMonthSale(String monthSale) {
            this.monthSale = monthSale;
        }

        public String getTodaySale() {
            return todaySale;
        }

        public void setTodaySale(String todaySale) {
            this.todaySale = todaySale;
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

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getWeekSale() {
            return weekSale;
        }

        public void setWeekSale(String weekSale) {
            this.weekSale = weekSale;
        }

        public String getLastSale() {
            return lastSale;
        }

        public void setLastSale(String lastSale) {
            this.lastSale = lastSale;
        }

        public String getForecastSale() {
            return forecastSale;
        }

        public void setForecastSale(String forecastSale) {
            this.forecastSale = forecastSale;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.monthSale);
            dest.writeString(this.todaySale);
            dest.writeString(this.stationName);
            dest.writeString(this.id);
            dest.writeString(this.stock);
            dest.writeString(this.weekSale);
            dest.writeString(this.lastSale);
            dest.writeString(this.forecastSale);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.monthSale = in.readString();
            this.todaySale = in.readString();
            this.stationName = in.readString();
            this.id = in.readString();
            this.stock = in.readString();
            this.weekSale = in.readString();
            this.lastSale = in.readString();
            this.forecastSale = in.readString();
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

    public BusinessInWorkbenchBean() {
    }

    protected BusinessInWorkbenchBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<BusinessInWorkbenchBean> CREATOR = new Parcelable.Creator<BusinessInWorkbenchBean>() {
        @Override
        public BusinessInWorkbenchBean createFromParcel(Parcel source) {
            return new BusinessInWorkbenchBean(source);
        }

        @Override
        public BusinessInWorkbenchBean[] newArray(int size) {
            return new BusinessInWorkbenchBean[size];
        }
    };
}
