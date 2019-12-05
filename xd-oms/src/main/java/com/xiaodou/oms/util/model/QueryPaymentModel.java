package com.xiaodou.oms.util.model;

/**
 * @author Kai.Chen
 * @version V1.0
 * @date 14/11/11 下午6:47
 */
public class QueryPaymentModel {
    private String id;

    private String productType;

    private String orderId;

    private String payNo;

    private String gorderIp;

    private Integer gorderStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Integer getGorderStatus() {
        return gorderStatus;
    }

    public void setGorderStatus(Integer gorderStatus) {
        this.gorderStatus = gorderStatus;
    }

    public String getGorderIp() {
        return gorderIp;
    }

    public void setGorderIp(String gorderIp) {
        this.gorderIp = gorderIp;
    }

    @Override
    public String toString() {
        return "QueryPaymentModel{" +
                "id='" + id + '\'' +
                ", productType='" + productType + '\'' +
                ", orderId='" + orderId + '\'' +
                ", payNo='" + payNo + '\'' +
                ", gorderIp='" + gorderIp + '\'' +
                ", gorderStatus=" + gorderStatus +
                '}';
    }
}
