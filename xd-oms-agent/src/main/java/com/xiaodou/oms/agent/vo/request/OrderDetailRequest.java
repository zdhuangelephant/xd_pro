package com.xiaodou.oms.agent.vo.request;

import java.util.Map;

/**
 * Created by zyp on 14-7-1.
 */
public class OrderDetailRequest {

    /**业务线*/
    private String productLine;

    /**订单号*/
    private String orderId;

    /**查询属性*/
    private Map<String, Object> outputProperties;

    /**购买账号*/
    private String buyAccountId;

    public String getBuyAccountId() {
        return buyAccountId;
    }

    public void setBuyAccountId(String buyAccountId) {
        this.buyAccountId = buyAccountId;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Map<String, Object> getOutputProperties() {
        return outputProperties;
    }

    public void setOutputProperties(Map<String, Object> outputProperties) {
        this.outputProperties = outputProperties;
    }
}
