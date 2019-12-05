package com.xiaodou.oms.agent.common.enums;

/**
 * Created by zyp on 14-7-1.
 */
public enum GorderProperty {

    id("id","1"),
    gorderTime("gorderTime","1"),
    gorderAmount("gorderAmount","1"),
    gpayAmount("gpayAmount","1"),
    originalGpayAmount("originalGpayAmount","1"),
    gsaveAmount("gsaveAmount","1"),
    gfee("gfee","1"),
    gorderStatus("gorderStatus","1"),
    realPayMethod("realPayMethod","1"),
    goodsName("goodsName","1"),
    payOrderId("payOrderId","1"),
    payUrl("payUrl","1"),
    preCloseTime("preCloseTime","1"),
    paySuccessTime("paySuccessTime","1"),
    sellerNickName("sellerNickName","1"),
    buyAccountId("buyAccountId","1"),
    gorderIp("gorderIp","1"),
    invoice("invoice","1"),
    shippingAddress("shippingAddress","1"),
    receiverEmail("receiverEmail","1"),
    receiverPhone("receiverPhone","1"),
    deliveryDateType("deliveryDateType","1"),
    expectedShippingTime("expectedShippingTime","1"),
    outerOrigin("outerOrigin","1"),
    innerOrigin("innerOrigin","1"),
    parentGorderId("parentGorderId","1"),
    note("note","1"),
    updateTime("updateTime","1"),
    clientType("clientType","1"),
    closeTime("closeTime","1"),
    gorderDesc("gorderDesc","1"),
    city("city","1"),
    productType("productType","1"),
    orderList("orderList","1"),
    isNeedInvoice("isNeedInvoice","1"),
    receiverName("receiverName","1"),
    uid("uid","1"),
    isLogin("isLogin","1"),
    orderFrom("orderFrom","1"),
    deviceId("deviceId","1"),
    wayBill("wayBill","1"),
    tags("tags","1"),
    mult("mult","1");

    /**属性名称*/
    private String propertyName;

    /**属性类型*/
    private String propertyType;

    private GorderProperty(String propertyName,String propertyType) {
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
