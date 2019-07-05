package com.hqhop.www.iot.bean;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by allen on 2017/8/23.
 */

public class EquipmentMonitorBean {

    /**
     * data : [{"equipId":"45","equipName":"流量计","sumParameter":5,"equipImg":"llj3x.png","equipValues":[{"showDisplay":"0","unit":"Nm³/h","bizType":"none","alarm":"0","name":"瞬时流量","physicalLowerLimit":"0.0","physicalUpperLimit":"1000.0","id":"6249c470-8d35-11e7-828f-f1bfe84dc7fe","configType":"traffic_instantaneous","type":"traffic_instantaneous","value":"1.00"},{"showDisplay":"0","unit":"m³/h","bizType":"none","alarm":"0","name":"累计流量","physicalLowerLimit":"0.0","physicalUpperLimit":"1000.0","id":"624a053e-8d35-11e7-828f-f1bfe84dc7fe","configType":"traffic_cumulative","type":"traffic_cumulative","value":"200.00"},{"showDisplay":"0","unit":"m³/h","bizType":"none","alarm":"0","name":"工况流量","physicalLowerLimit":"0.0","physicalUpperLimit":"1000.0","id":"624a2dac-8d35-11e7-828f-f1bfe84dc7fe","configType":"traffic_working","type":"traffic_working","value":"3.00"},{"showDisplay":"0","unit":"℃","bizType":"none","alarm":"0","name":"温度","physicalLowerLimit":"-150.0","physicalUpperLimit":"50.0","id":"624a58b8-8d35-11e7-828f-f1bfe84dc7fe","configType":"temperature","type":"temperature","value":"-105.00"},{"showDisplay":"0","unit":"Mpa","bizType":"none","alarm":"0","name":"压力","physicalLowerLimit":"0.0","physicalUpperLimit":"2.0","id":"624a850e-8d35-11e7-828f-f1bfe84dc7fe","configType":"pressure","type":"pressure","value":"0.29"}]},{"equipId":"41","equipName":"1#瓶组","sumParameter":3,"equipImg":"pz3x.png","equipValues":[{"showDisplay":"0","unit":"mm","bizType":"none","alarm":"0","name":"液位","physicalLowerLimit":"0.0","physicalUpperLimit":"100.0","id":"5e10057c-8d35-11e7-828f-f1bfe84dc7fe","configType":"bottle_liquid_level","type":"bottle_liquid_level","value":"-"},{"showDisplay":"2","unit":"℃","bizType":"biz_type","alarm":"0","name":"温度","physicalLowerLimit":"-150.0","physicalUpperLimit":"50.0","id":"6248c976-8d35-11e7-828f-f1bfe84dc7fe","configType":"temperature","type":"temperature","value":"-"},{"showDisplay":"0","unit":"Kpa","bizType":"none","alarm":"2","name":"压力","physicalLowerLimit":"0.0","physicalUpperLimit":"2.0","id":"63b2fcc6-8d46-11e7-828f-f1bfe84dc7fe","configType":"pressure","type":"pressure","value":"1.83"}]},{"equipId":"42","equipName":"2#瓶组","sumParameter":3,"equipImg":"pz3x.png","equipValues":[{"showDisplay":"2","unit":"mm","bizType":"none","alarm":"0","name":"液位","physicalLowerLimit":"0.0","physicalUpperLimit":"100.0","id":"6248d8da-8d35-11e7-828f-f1bfe84dc7fe","configType":"bottle_liquid_level","type":"bottle_liquid_level","value":"90.00"},{"showDisplay":"2","unit":"MPa","bizType":"","alarm":"2","name":"压力","physicalLowerLimit":"0.0","physicalUpperLimit":"2.0","id":"6248f770-8d35-11e7-828f-f1bfe84dc7fe","configType":"pressure","type":"pressure","value":"0.85"},{"showDisplay":"2","unit":"℃","bizType":"","alarm":"0","name":"温度","physicalLowerLimit":"-150.0","physicalUpperLimit":"50.0","id":"62490d50-8d35-11e7-828f-f1bfe84dc7fe","configType":"temperature","type":"temperature","value":"-"}]},{"equipId":"444","equipName":"气化器","sumParameter":1,"equipImg":"qhq@3x.png","equipValues":[{"showDisplay":"2","unit":"MPa","bizType":"","alarm":"0","name":"压力","physicalLowerLimit":"0.0","physicalUpperLimit":"2.0","id":"5db79484-8d47-11e7-828f-f1bfe84dc7fe","configType":"pressure","type":"pressure","value":"1.74"}]},{"equipId":"46","equipName":"复热器","sumParameter":1,"equipImg":"frq3x.png","equipValues":[{"showDisplay":"2","unit":"℃","bizType":"","alarm":"0","name":"出口温度","physicalLowerLimit":"-150.0","physicalUpperLimit":"50.0","id":"624aadc2-8d35-11e7-828f-f1bfe84dc7fe","configType":"temperature","type":"temperature","value":"-150.00"}]},{"equipId":"47","equipName":"燃气探头1","sumParameter":1,"equipImg":"rqtt@3x.png","equipValues":[{"showDisplay":"0","unit":"%","bizType":"none","alarm":"2","name":"浓度","physicalLowerLimit":"0.0","physicalUpperLimit":"100.0","id":"624ac424-8d35-11e7-828f-f1bfe84dc7fe","configType":"none","type":"none","value":"95.00"}]},{"equipId":"49","equipName":"调压撬","sumParameter":1,"equipImg":"tyq@3x.png","equipValues":[{"showDisplay":"2","unit":"MPa","bizType":"","alarm":"0","name":"出口压力","physicalLowerLimit":"0.0","physicalUpperLimit":"2.0","id":"624ae2e2-8d35-11e7-828f-f1bfe84dc7fe","configType":"pressure","type":"pressure","value":"0.96"}]}]
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
         * equipId : 45
         * equipName : 流量计
         * sumParameter : 5
         * equipImg : llj3x.png
         * equipValues : [{"showDisplay":"0","unit":"Nm³/h","bizType":"none","alarm":"0","name":"瞬时流量","physicalLowerLimit":"0.0","physicalUpperLimit":"1000.0","id":"6249c470-8d35-11e7-828f-f1bfe84dc7fe","configType":"traffic_instantaneous","type":"traffic_instantaneous","value":"1.00"},{"showDisplay":"0","unit":"m³/h","bizType":"none","alarm":"0","name":"累计流量","physicalLowerLimit":"0.0","physicalUpperLimit":"1000.0","id":"624a053e-8d35-11e7-828f-f1bfe84dc7fe","configType":"traffic_cumulative","type":"traffic_cumulative","value":"200.00"},{"showDisplay":"0","unit":"m³/h","bizType":"none","alarm":"0","name":"工况流量","physicalLowerLimit":"0.0","physicalUpperLimit":"1000.0","id":"624a2dac-8d35-11e7-828f-f1bfe84dc7fe","configType":"traffic_working","type":"traffic_working","value":"3.00"},{"showDisplay":"0","unit":"℃","bizType":"none","alarm":"0","name":"温度","physicalLowerLimit":"-150.0","physicalUpperLimit":"50.0","id":"624a58b8-8d35-11e7-828f-f1bfe84dc7fe","configType":"temperature","type":"temperature","value":"-105.00"},{"showDisplay":"0","unit":"Mpa","bizType":"none","alarm":"0","name":"压力","physicalLowerLimit":"0.0","physicalUpperLimit":"2.0","id":"624a850e-8d35-11e7-828f-f1bfe84dc7fe","configType":"pressure","type":"pressure","value":"0.29"}]
         */

        private String equipId;
        private String equipName;
        private int sumParameter;
        private String equipImg;
        private List<EquipValuesBean> equipValues;

        public String getEquipId() {
            return equipId;
        }

        public void setEquipId(String equipId) {
            this.equipId = equipId;
        }

        public String getEquipName() {
            return equipName;
        }

        public void setEquipName(String equipName) {
            this.equipName = equipName;
        }

        public int getSumParameter() {
            return sumParameter;
        }

        public void setSumParameter(int sumParameter) {
            this.sumParameter = sumParameter;
        }

        public String getEquipImg() {
            return equipImg;
        }

        public void setEquipImg(String equipImg) {
            this.equipImg = equipImg;
        }

        public List<EquipValuesBean> getEquipValues() {
            return equipValues;
        }

        public void setEquipValues(List<EquipValuesBean> equipValues) {
            this.equipValues = equipValues;
        }

        public static class EquipValuesBean implements Comparable<EquipValuesBean> {
            /**
             * showDisplay : 0
             * unit : Nm³/h
             * bizType : none
             * alarm : 0
             * name : 瞬时流量
             * physicalLowerLimit : 0.0
             * physicalUpperLimit : 1000.0
             * id : 6249c470-8d35-11e7-828f-f1bfe84dc7fe
             * configType : traffic_instantaneous
             * type : traffic_instantaneous
             * value : 1.00
             */

            private int showDisplay;
            private String unit;
            private String bizType;
            private String alarm;
            private String name;
            private String physicalLowerLimit;
            private String physicalUpperLimit;
            private String id;
            private String configType;
            private String type;
            private String value;

            public int getShowDisplay() {
                return showDisplay;
            }

            public void setShowDisplay(int showDisplay) {
                this.showDisplay = showDisplay;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getBizType() {
                return bizType;
            }

            public void setBizType(String bizType) {
                this.bizType = bizType;
            }

            public String getAlarm() {
                return alarm;
            }

            public void setAlarm(String alarm) {
                this.alarm = alarm;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhysicalLowerLimit() {
                return physicalLowerLimit;
            }

            public void setPhysicalLowerLimit(String physicalLowerLimit) {
                this.physicalLowerLimit = physicalLowerLimit;
            }

            public String getPhysicalUpperLimit() {
                return physicalUpperLimit;
            }

            public void setPhysicalUpperLimit(String physicalUpperLimit) {
                this.physicalUpperLimit = physicalUpperLimit;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getConfigType() {
                return configType;
            }

            public void setConfigType(String configType) {
                this.configType = configType;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            @Override
            public int compareTo(@NonNull EquipValuesBean o) {
                return ((Integer) o.getShowDisplay()).compareTo(this.getShowDisplay());
            }
        }
    }
}
