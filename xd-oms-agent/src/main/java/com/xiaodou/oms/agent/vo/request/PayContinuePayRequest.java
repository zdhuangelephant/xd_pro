package com.xiaodou.oms.agent.vo.request;

/**
 * Created by zyp on 14-7-4.
 */
public class PayContinuePayRequest extends BaseRequest {

    /**支付单号*/
    public String gorderId;

    public String getGorderId() {
        return gorderId;
    }

    public void setGorderId(String gorderId) {
        this.gorderId = gorderId;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }
}
