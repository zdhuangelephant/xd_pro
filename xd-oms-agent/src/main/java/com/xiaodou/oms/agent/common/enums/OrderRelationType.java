package com.xiaodou.oms.agent.common.enums;

/**
 * Created by zyp on 14-6-24.
 */
public enum OrderRelationType {

    ORDER_ORDER("1","order-order"),
    ORDER_ITEM("2","order_orderItem"),
    ITEM_ORDER("3","orderItem_order"),
    ITEM_ITEM("4","orderItem_orderItem");

    /**
     * 关联关系编码
     */
    private String code;
    /**
     * 关联关系名称
     */
    private String name;

    private OrderRelationType(String code,String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
