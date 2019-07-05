package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allen on 2017/7/24.
 */

public class StationTestBean implements Parcelable {

    private String id;

    private String station;

    private String person;

    private String phone;

    private String status;

    public StationTestBean(String station, String person, String phone) {
        this.station = station;
        this.person = person;
        this.phone = phone;
    }

    public StationTestBean(String id, String station, String person, String phone, String status) {
        this.id = id;
        this.station = station;
        this.person = person;
        this.phone = phone;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.station);
        dest.writeString(this.person);
        dest.writeString(this.phone);
        dest.writeString(this.status);
    }

    protected StationTestBean(Parcel in) {
        this.id = in.readString();
        this.station = in.readString();
        this.person = in.readString();
        this.phone = in.readString();
        this.status = in.readString();
    }

    public static final Creator<StationTestBean> CREATOR = new Creator<StationTestBean>() {
        @Override
        public StationTestBean createFromParcel(Parcel source) {
            return new StationTestBean(source);
        }

        @Override
        public StationTestBean[] newArray(int size) {
            return new StationTestBean[size];
        }
    };
}
