package com.xiaodou.oms.agent.vo.request;

import java.util.List;

/**
 * Created by zyp on 14-7-16.
 */
public class OrderFireListRequest extends BaseRequest {

    //事件名称
    private String event;
    //ip
    private String ip;
    //
    private List<OrderFireRequest> pojoList;

    public List<OrderFireRequest> getPojoList() {
        return pojoList;
    }

    public void setPojoList(List<OrderFireRequest> pojoList) {
        this.pojoList = pojoList;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
