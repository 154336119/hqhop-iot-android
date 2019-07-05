package com.hqhop.www.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 2017/7/6.
 */

public class UserInfoBean implements Parcelable {

    /**
     * data : {"organizationId":21,"station":[{"name":"324","checked":false,"id":697,"region":{"code":"110000","name":"北京市","id":2,"parentId":1},"type":"station"},{"name":"3245","checked":false,"id":698,"region":{"code":"110000","name":"北京市","id":2,"parentId":1},"type":"station"}],"id":"40288e81567d599a01567d5b60310000","username":"zhadmin"}
     * success : true
     * message : success
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
         * organizationId : 21
         * station : [{"name":"324","checked":false,"id":697,"region":{"code":"110000","name":"北京市","id":2,"parentId":1},"type":"station"},{"name":"3245","checked":false,"id":698,"region":{"code":"110000","name":"北京市","id":2,"parentId":1},"type":"station"}]
         * id : 40288e81567d599a01567d5b60310000
         * username : zhadmin
         */

        private int organizationId;
        private String id;
        private String username;
        private List<StationBean> station;

        public int getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(int organizationId) {
            this.organizationId = organizationId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<StationBean> getStation() {
            return station;
        }

        public void setStation(List<StationBean> station) {
            this.station = station;
        }

        public static class StationBean {
            /**
             * name : 324
             * checked : false
             * id : 697
             * region : {"code":"110000","name":"北京市","id":2,"parentId":1}
             * type : station
             */

            private String name;
            private boolean checked;
            private int id;
            private RegionBean region;
            private String type;

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

            public RegionBean getRegion() {
                return region;
            }

            public void setRegion(RegionBean region) {
                this.region = region;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class RegionBean {
                /**
                 * code : 110000
                 * name : 北京市
                 * id : 2
                 * parentId : 1
                 */

                private String code;
                private String name;
                private int id;
                private int parentId;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getParentId() {
                    return parentId;
                }

                public void setParentId(int parentId) {
                    this.parentId = parentId;
                }
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.organizationId);
            dest.writeString(this.id);
            dest.writeString(this.username);
            dest.writeList(this.station);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.organizationId = in.readInt();
            this.id = in.readString();
            this.username = in.readString();
            this.station = new ArrayList<StationBean>();
            in.readList(this.station, StationBean.class.getClassLoader());
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

    public UserInfoBean() {
    }

    protected UserInfoBean(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.success = in.readByte() != 0;
        this.message = in.readString();
        this.version = in.readInt();
    }

    public static final Parcelable.Creator<UserInfoBean> CREATOR = new Parcelable.Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
