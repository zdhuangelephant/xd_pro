package com.xiaodou.oms.agent.vo.request;

/**
 * Created by zyp on 14-7-18.
 */
public class OrderItemNoteRequest extends BaseRequest {

    /**orderItem号*/
    private String orderItemId;

    /**备注内容*/
    private String note;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }
}
