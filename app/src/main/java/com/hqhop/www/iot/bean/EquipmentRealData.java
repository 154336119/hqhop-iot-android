package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allen on 2017/9/12.
 */

public class EquipmentRealData implements Parcelable {
    /**
     * data : 10.0
     * success : true
     * message : 成功
     * version : 1
     */

    private float data;
    private boolean success;
    private String message;
    private int version;

    public float getData() {
        return data;
    }

    public void setData(float data) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.data);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeString(this.message);
        dest.writeInt(this.version);
    }

    public EquipmentRealData() {
    }

    protected EquipmentRealData(Parcel in) {
        this.data = in.readFloat();
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
    }

    public static final Parcelable.Creator<EquipmentRealData> CREATOR = new Parcelable.Creator<EquipmentRealData>() {
        @Override
        public EquipmentRealData createFromParcel(Parcel source) {
            return new EquipmentRealData(source);
        }

        @Override
        public EquipmentRealData[] newArray(int size) {
            return new EquipmentRealData[size];
        }
    };
}
