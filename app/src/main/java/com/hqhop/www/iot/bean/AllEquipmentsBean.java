package com.hqhop.www.iot.bean;

import java.util.List;

/**
 * Created by allen on 10/26/2017.
 */

public class AllEquipmentsBean {

    /**
     * data : [{"equipmentProfile":null,"name":"压缩机","station":null,"equipmentParameters":null,"id":"40","category":null},{"equipmentProfile":null,"name":"1#瓶组","station":null,"equipmentParameters":null,"id":"41","category":null},{"equipmentProfile":null,"name":"2#瓶组","station":null,"equipmentParameters":null,"id":"42","category":null},{"equipmentProfile":null,"name":"汇流排1进口阀位","station":null,"equipmentParameters":null,"id":"43","category":null},{"equipmentProfile":null,"name":"汇流排2进口阀位","station":null,"equipmentParameters":null,"id":"44","category":null},{"equipmentProfile":null,"name":"气化器","station":null,"equipmentParameters":null,"id":"444","category":null},{"equipmentProfile":null,"name":"2#气化器","station":null,"equipmentParameters":null,"id":"445","category":null},{"equipmentProfile":null,"name":"流量计","station":null,"equipmentParameters":null,"id":"45","category":null},{"equipmentProfile":null,"name":"复热器","station":null,"equipmentParameters":null,"id":"46","category":null},{"equipmentProfile":null,"name":"燃气探头1","station":null,"equipmentParameters":null,"id":"47","category":null},{"equipmentProfile":null,"name":"燃气探头2","station":null,"equipmentParameters":null,"id":"48","category":null},{"equipmentProfile":null,"name":"调压撬","station":null,"equipmentParameters":null,"id":"49","category":null}]
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

    public static class DataBean {
        /**
         * equipmentProfile : null
         * name : 压缩机
         * station : null
         * equipmentParameters : null
         * id : 40
         * category : null
         */

        private String equipmentProfile;
        private String name;
        private String station;
        private String equipmentParameters;
        private String id;
        private String category;

        public String getEquipmentProfile() {
            return equipmentProfile;
        }

        public void setEquipmentProfile(String equipmentProfile) {
            this.equipmentProfile = equipmentProfile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public String getEquipmentParameters() {
            return equipmentParameters;
        }

        public void setEquipmentParameters(String equipmentParameters) {
            this.equipmentParameters = equipmentParameters;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
