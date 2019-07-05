package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allen on 2017/7/18.
 */

public class AlertTestBean implements Parcelable {
    private String emergency;

    private String station;

    private String person;

    private String phone;

    private String reason;

    private String value;

    private String date;

    private String device;

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.emergency);
        dest.writeString(this.station);
        dest.writeString(this.person);
        dest.writeString(this.phone);
        dest.writeString(this.reason);
        dest.writeString(this.value);
        dest.writeString(this.date);
        dest.writeString(this.device);
    }

    public AlertTestBean() {
    }

    protected AlertTestBean(Parcel in) {
        this.emergency = in.readString();
        this.station = in.readString();
        this.person = in.readString();
        this.phone = in.readString();
        this.reason = in.readString();
        this.value = in.readString();
        this.date = in.readString();
        this.device = in.readString();
    }

    public static final Parcelable.Creator<AlertTestBean> CREATOR = new Parcelable.Creator<AlertTestBean>() {
        @Override
        public AlertTestBean createFromParcel(Parcel source) {
            return new AlertTestBean(source);
        }

        @Override
        public AlertTestBean[] newArray(int size) {
            return new AlertTestBean[size];
        }
    };
}
