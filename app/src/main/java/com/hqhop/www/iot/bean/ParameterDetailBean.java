package com.hqhop.www.iot.bean;

/**
 * Created by allen on 2017/9/12.
 */

public class ParameterDetailBean {
    /**
     * data : {"dateOfManufacture":1501570684000,"nextAnnualInspection":null,"purchaseDate":1503557905000,"code":"ttr","nextMaintenanceDate":1503125897000,"id":null,"purchasePrice":"12","manufacturer":"示范点"}
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

    public static class DataBean {
        /**
         * dateOfManufacture : 1501570684000
         * nextAnnualInspection : null
         * purchaseDate : 1503557905000
         * code : ttr
         * nextMaintenanceDate : 1503125897000
         * id : null
         * purchasePrice : 12
         * manufacturer : 示范点
         */

        private long dateOfManufacture;
        private Object nextAnnualInspection;
        private long purchaseDate;
        private String code;
        private long nextMaintenanceDate;
        private Object id;
        private String purchasePrice;
        private String manufacturer;

        public long getDateOfManufacture() {
            return dateOfManufacture;
        }

        public void setDateOfManufacture(long dateOfManufacture) {
            this.dateOfManufacture = dateOfManufacture;
        }

        public Object getNextAnnualInspection() {
            return nextAnnualInspection;
        }

        public void setNextAnnualInspection(Object nextAnnualInspection) {
            this.nextAnnualInspection = nextAnnualInspection;
        }

        public long getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(long purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getNextMaintenanceDate() {
            return nextMaintenanceDate;
        }

        public void setNextMaintenanceDate(long nextMaintenanceDate) {
            this.nextMaintenanceDate = nextMaintenanceDate;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(String purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }
    }
}
