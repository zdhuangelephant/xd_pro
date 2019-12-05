package com.xiaodou.oms.entity.order;

import java.sql.Timestamp;

/**
 * 订单状态变化日志
 * @author Administrator
 *
 */
public class StatusLog {

    /**商品订单号*/
    private String orderId;

    /**状态*/
    private Integer orderStatus;

    /**记录时间*/
    private Timestamp logTime;

    /**记录IP*/
    private String logIp;

    /**备注*/
    private String note;

    /**用户id*/
    private String buyAccountId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBuyAccountId() {
        return buyAccountId;
    }

    public void setBuyAccountId(String buyAccountId) {
        this.buyAccountId = buyAccountId;
    }
}
