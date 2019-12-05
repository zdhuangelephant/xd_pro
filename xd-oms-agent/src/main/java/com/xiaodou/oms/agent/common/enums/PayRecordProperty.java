package com.xiaodou.oms.agent.common.enums;

/**
 * Created by zyp on 14-7-1.
 */
public enum PayRecordProperty {

    ;

    /**属性名称*/
    private String propertyName;

    /**属性类型*/
    private String propertyType;

    private PayRecordProperty(String propertyName,String propertyType) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }
}
