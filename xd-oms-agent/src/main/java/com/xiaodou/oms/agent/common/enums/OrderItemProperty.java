package com.xiaodou.oms.agent.common.enums;

/**
 * Created by zyp on 14-7-1.
 */
public enum OrderItemProperty {

    id("id","1"),
    gorderId("gorderId","1"),
    orderId("orderId","1"),
    prctId("prctId","1"),
    subPrctId("subPrctId","1"),
    merchantPrctId("merchantPrctId","1"),
    subMerchantPrctId("subMerchantPrctId","1"),
    productType("productType","1"),
    productName("productName","1"),
    unitPrice("unitPrice","1"),
    buyCount("buyCount","1"),
    itemOrderAmount("itemOrderAmount","1"),
    originalItemPayAmount("originalItemPayAmount","1"),
    itemPayAmount("itemPayAmount","1"),
    itemCostAmount("itemCostAmount","1"),
    itemFee("itemFee","1"),
    refundAmount("refundAmount","1"),
    createTime("createTime","1"),
    updateTime("updateTime","1"),
    canRefund("canRefund","1"),
    refundDeadline("refundDeadline","1"),
    refundStatus("refundStatus","1"),
    canExchange("canExchange","1"),
    exchangeDeadline("exchangeDeadline","1"),
    exchangeStatus("exchangeStatus","1"),
    okCount("okCount","1"),
    okAmount("okAmount","1"),
    itemSaveAmount("itemSaveAmount","1"),
    activityLabel("activityLabel","1"),
    itemPoint("itemPoint","1"),
    goodsImageUrl("goodsImageUrl","1"),
    merchantId("merchantId","1"),
    buyAccountId("buyAccountId","1"),
    itemStatus("itemStatus","1"),
    relationId("relationId","1"),
    misc("misc","1"),
    gorder("gorder","1"),
    order("order","1"),
    virtualRelationId("virtualRelationId","1"),
    note("note","1");

    /**属性名称*/
    private String propertyName;

    /**属性类型*/
    private String propertyType;

    private OrderItemProperty(String propertyName,String propertyType) {
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
