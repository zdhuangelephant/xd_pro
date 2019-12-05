package com.xiaodou.oms.agent.vo.request;

/**
 * Created by zyp on 14-7-4.
 */
public class OrderNoteRequest extends BaseRequest {

    /**订单号*/
    private String orderId;

    /**备注*/
    private String note;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
