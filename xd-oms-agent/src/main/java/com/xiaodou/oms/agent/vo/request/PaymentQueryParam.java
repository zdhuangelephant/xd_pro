package com.xiaodou.oms.agent.vo.request;

/**
 * @author Kai.Chen
 * @version V1.0
 * @date 14/11/18 上午11:08
 */
public class PaymentQueryParam {
    private String gorderId;

    private String productLine;

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
