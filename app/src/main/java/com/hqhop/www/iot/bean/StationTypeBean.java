package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by allen on 2017/8/18.
 */

public class StationTypeBean implements Parcelable {

    /**
     * data : [{"address":null,"leaderMobile":null,"saleToday":null,"regionMap":"","latitude":"","operateTime":"","regionName":null,"pId":58,"delFlag":"","type":"gas","operator":"","stationImg":null,"scene":null,"alarmCount":0,"leaderName":null,"regionId":null,"name":"重庆欣雨测试站点c","checked":false,"id":63,"stock":null,"forecastDate":null,"longitude":"","status":null},{"address":null,"leaderMobile":null,"saleToday":null,"regionMap":null,"latitude":"123","operateTime":null,"regionName":null,"pId":63,"delFlag":null,"type":"gas","operator":null,"stationImg":null,"scene":null,"alarmCount":0,"leaderName":null,"regionId":null,"name":"CETSS","checked":false,"id":64,"stock":null,"forecastDate":null,"longitude":"23","status":null},{"address":"北京市延庆县","leaderMobile":"18011751111","saleToday":null,"regionMap":"","latitude":"40.48736","operateTime":"","regionName":null,"pId":708,"delFlag":"1","type":"gas","operator":"","stationImg":"cng.jpg","scene":null,"alarmCount":0,"leaderName":"张三","regionId":null,"name":"艾官营气化站","checked":false,"id":727,"stock":null,"forecastDate":null,"longitude":"116.101452","status":null}]
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
         * address : null
         * leaderMobile : null
         * saleToday : null
         * regionMap :
         * latitude :
         * operateTime :
         * regionName : null
         * pId : 58
         * delFlag :
         * type : gas
         * operator :
         * stationImg : null
         * scene : null
         * alarmCount : 0
         * leaderName : null
         * regionId : null
         * name : 重庆欣雨测试站点c
         * checked : false
         * id : 63
         * stock : null
         * forecastDate : null
         * longitude :
         * status : null
         */

        private String address;
        private String leaderMobile;
        private String saleToday;
        private String regionMap;
        private String latitude;
        private String operateTime;
        private String regionName;
        private int pId;
        private String delFlag;
        private String type;
        private String operator;
        private String stationImg;
        private String scene;
        private int alarmCount;
        private String leaderName;
        private String regionId;
        private String name;
        private boolean checked;
        private int id;
        private String stock;
        private String forecastDate;
        private String longitude;
        private int status;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLeaderMobile() {
            return leaderMobile;
        }

        public void setLeaderMobile(String leaderMobile) {
            this.leaderMobile = leaderMobile;
        }

        public String getSaleToday() {
            return saleToday;
        }

        public void setSaleToday(String saleToday) {
            this.saleToday = saleToday;
        }

        public String getRegionMap() {
            return regionMap;
        }

        public void setRegionMap(String regionMap) {
            this.regionMap = regionMap;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(String operateTime) {
            this.operateTime = operateTime;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public int getpId() {
            return pId;
        }

        public void setpId(int pId) {
            this.pId = pId;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getStationImg() {
            return stationImg;
        }

        public void setStationImg(String stationImg) {
            this.stationImg = stationImg;
        }

        public String getScene() {
            return scene;
        }

        public void setScene(String scene) {
            this.scene = scene;
        }

        public int getAlarmCount() {
            return alarmCount;
        }

        public void setAlarmCount(int alarmCount) {
            this.alarmCount = alarmCount;
        }

        public String getLeaderName() {
            return leaderName;
        }

        public void setLeaderName(String leaderName) {
            this.leaderName = leaderName;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getForecastDate() {
            return forecastDate;
        }

        public void setForecastDate(String forecastDate) {
            this.forecastDate = forecastDate;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.address);
            dest.writeString(this.leaderMobile);
            dest.writeString(this.saleToday);
            dest.writeString(this.regionMap);
            dest.writeString(this.latitude);
            dest.writeString(this.operateTime);
            dest.writeString(this.regionName);
            dest.writeInt(this.pId);
            dest.writeString(this.delFlag);
            dest.writeString(this.type);
            dest.writeString(this.operator);
            dest.writeString(this.stationImg);
            dest.writeString(this.scene);
            dest.writeInt(this.alarmCount);
            dest.writeString(this.leaderName);
            dest.writeString(this.regionId);
            dest.writeString(this.name);
            dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
            dest.writeInt(this.id);
            dest.writeString(this.stock);
            dest.writeString(this.forecastDate);
            dest.writeString(this.longitude);
            dest.writeInt(this.status);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.address = in.readString();
            this.leaderMobile = in.readString();
            this.saleToday = in.readString();
            this.regionMap = in.readString();
            this.latitude = in.readString();
            this.operateTime = in.readString();
            this.regionName = in.readString();
            this.pId = in.readInt();
            this.delFlag = in.readString();
            this.type = in.readString();
            this.operator = in.readString();
            this.stationImg = in.readString();
            this.scene = in.readString();
            this.alarmCount = in.readInt();
            this.leaderName = in.readString();
            this.regionId = in.readString();
            this.name = in.readString();
            this.checked = in.readByte() != 0;
            this.id = in.readInt();
            this.stock = in.readString();
            this.forecastDate = in.readString();
            this.longitude = in.readString();
            this.status = in.readInt();
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

    public StationTypeBean() {
    }

    protected StationTypeBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<StationTypeBean> CREATOR = new Parcelable.Creator<StationTypeBean>() {
        @Override
        public StationTypeBean createFromParcel(Parcel source) {
            return new StationTypeBean(source);
        }

        @Override
        public StationTypeBean[] newArray(int size) {
            return new StationTypeBean[size];
        }
    };
}
