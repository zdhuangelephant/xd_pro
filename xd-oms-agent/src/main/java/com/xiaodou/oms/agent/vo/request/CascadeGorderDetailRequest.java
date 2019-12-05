package com.xiaodou.oms.agent.vo.request;

import java.util.Map;

/**
 * Created by zyp on 14-7-3.
 */
public class CascadeGorderDetailRequest extends BaseRequest {

    /**支付单号*/
    private String gorderId;

    /**用户信息*/
    private String buyAccountId;

    /**gorder输出参数*/
    private Map<String, Object> gorderOutputProperties;

    /**order输出参数*/
    private Map<String, Object> orderOutputProperties;

    /**orderItem输出参数*/
    private Map<String, Object> orderItemOutputProperties;

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

    public String getGorderId() {
        return gorderId;
    }

    public void setGorderId(String gorderId) {
        this.gorderId = gorderId;
    }

    public Map<String, Object> getGorderOutputProperties() {
        return gorderOutputProperties;
    }

    public void setGorderOutputProperties(Map<String, Object> gorderOutputProperties) {
        this.gorderOutputProperties = gorderOutputProperties;
    }

    public Map<String, Object> getOrderOutputProperties() {
        return orderOutputProperties;
    }

    public void setOrderOutputProperties(Map<String, Object> orderOutputProperties) {
        this.orderOutputProperties = orderOutputProperties;
    }

    public Map<String, Object> getOrderItemOutputProperties() {
        return orderItemOutputProperties;
    }

    public void setOrderItemOutputProperties(Map<String, Object> orderItemOutputProperties) {
        this.orderItemOutputProperties = orderItemOutputProperties;
    }
}
