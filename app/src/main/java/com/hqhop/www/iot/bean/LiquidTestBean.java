package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allen on 2017/7/19.
 */

public class LiquidTestBean implements Parcelable {
    private String station;

    private String information;

    private String date;

    private String value;

    public LiquidTestBean(String station, String information, String date, String value) {
        this.station = station;
        this.information = information;
        this.date = date;
        this.value = value;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        dest.writeString(this.station);
        dest.writeString(this.information);
        dest.writeString(this.date);
        dest.writeString(this.value);
    }

    protected LiquidTestBean(Parcel in) {
        this.station = in.readString();
        this.information = in.readString();
        this.date = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<LiquidTestBean> CREATOR = new Parcelable.Creator<LiquidTestBean>() {
        @Override
        public LiquidTestBean createFromParcel(Parcel source) {
            return new LiquidTestBean(source);
        }

        @Override
        public LiquidTestBean[] newArray(int size) {
            return new LiquidTestBean[size];
        }
    };
}
