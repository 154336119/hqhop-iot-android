package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allen on 2017/7/20.
 */

public class MaintainTestBean implements Parcelable {
    private String type;

    private String station;

    private String person;

    private String phone;

    private String reason;

    private String emergency;

    private String status;

    /**
     * 维保时间
     */
    private String dateMaintain;

    /**
     * 报修时间
     */
    private String dateSubmit;

    /**
     * 催修时间
     */
    private String dateUrge;

    private String remark;

    public MaintainTestBean(String type, String station, String person, String phone, String reason, String emergency, String status, String dateMaintain, String dateSubmit, String dateUrge, String remark) {
        this.type = type;
        this.station = station;
        this.person = person;
        this.phone = phone;
        this.reason = reason;
        this.emergency = emergency;
        this.status = status;
        this.dateMaintain = dateMaintain;
        this.dateSubmit = dateSubmit;
        this.dateUrge = dateUrge;
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateMaintain() {
        return dateMaintain;
    }

    public void setDateMaintain(String dateMaintain) {
        this.dateMaintain = dateMaintain;
    }

    public String getDateSubmit() {
        return dateSubmit;
    }

    public void setDateSubmit(String dateSubmit) {
        this.dateSubmit = dateSubmit;
    }

    public String getDateUrge() {
        return dateUrge;
    }

    public void setDateUrge(String dateUrge) {
        this.dateUrge = dateUrge;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.station);
        dest.writeString(this.person);
        dest.writeString(this.phone);
        dest.writeString(this.reason);
        dest.writeString(this.emergency);
        dest.writeString(this.status);
        dest.writeString(this.dateMaintain);
        dest.writeString(this.dateSubmit);
        dest.writeString(this.dateUrge);
        dest.writeString(this.remark);
    }

    protected MaintainTestBean(Parcel in) {
        this.type = in.readString();
        this.station = in.readString();
        this.person = in.readString();
        this.phone = in.readString();
        this.reason = in.readString();
        this.emergency = in.readString();
        this.status = in.readString();
        this.dateMaintain = in.readString();
        this.dateSubmit = in.readString();
        this.dateUrge = in.readString();
        this.remark = in.readString();
    }

    public static final Parcelable.Creator<MaintainTestBean> CREATOR = new Parcelable.Creator<MaintainTestBean>() {
        @Override
        public MaintainTestBean createFromParcel(Parcel source) {
            return new MaintainTestBean(source);
        }

        @Override
        public MaintainTestBean[] newArray(int size) {
            return new MaintainTestBean[size];
        }
    };
}
