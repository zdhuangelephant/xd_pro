package com.xiaodou.oms.agent.vo.request;

import java.util.Map;

/**
 * Created by zyp on 14-7-1.
 */
public class OrderItemDetailRequest extends BaseRequest {


    /**主键id*/
    private String orderItemId;

    /**返回属性*/
    private Map<String, Object> outputProperties ;

    /**购票账号*/
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

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Map<String, Object> getOutputProperties() {
        return outputProperties;
    }

    public void setOutputProperties(Map<String, Object> outputProperties) {
        this.outputProperties = outputProperties;
    }
}
