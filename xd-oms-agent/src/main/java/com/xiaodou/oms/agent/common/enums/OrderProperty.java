package com.xiaodou.oms.agent.common.enums;

/**
 * Created by zyp on 14-7-1.
 */
public enum OrderProperty {

    id("id","1"),
    gorderId("gorderId","1"),
    productType("productType","1"),
    orderTime("orderTime","1"),
    orderStatus("orderStatus","1"),
    orderAmount("orderAmount","1"),
    saveAmount("saveAmount","1"),
    payAmount("payAmount","1"),
    logisticsAmount("logisticsAmount","1"),
    fee("fee","1"),
    originalPayAmount("originalPayAmount","1"),
    costAmount("costAmount","1"),
    buyAccountId("buyAccountId","1"),
    preCloseTime("preCloseTime","1"),
    updateTime("updateTime","1"),
    settleUp("settleUp","1"),
    successTime("successTime","1"),
    merchantId("merchantId","1"),
    merchantName("merchantName","1"),
    merchantAccount("merchantAccount","1"),
    merchantTel("merchantTel","1"),
    merchantOrderId("merchantOrderId","1"),
    merchantAmount("merchantAmount","1"),
    activityId("activityId","1"),
    activityType("activityType","1"),
    remark("remark","1"),
    orderDesc("orderDesc","1"),
    orderIp("orderIp","1"),
    paySuccessTime("paySuccessTime","1"),
    canDeliver("canDeliver","1"),
    canSettleUp("canSettleUp","1"),
    canRefund("canRefund","1"),
    misc("misc","1"),
    keywords("keywords","1"),
    deliveryBeginTime("deliveryBeginTime","1"),
    deliveryEndTime("deliveryEndTime","1"),
    relationId("relationId","1"),
    closedReason("closedReason","1"),
    statusDesc("statusDesc","1"),
    orderItemList("orderItemList","1"),
    note("note","1"),
    platformId("platformId","1"),
    gorder("gorder","1"),
    virtualRelationId("virtualRelationId","1"),
    signature("signature","1"),
    refundAmount("refundAmount","1");

    /**属性名称*/
    private String propertyName;

    /**属性类型*/
    private String propertyType;

    private OrderProperty(String propertyName,String propertyType) {
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
