package com.xiaodou.oms.agent.vo.request;

/**
 * Created by zyp on 14-7-2.
 */
public class PayRecordListRequest extends BaseRequest {

    /**支付单号*/
    private String gorderId;

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
}
