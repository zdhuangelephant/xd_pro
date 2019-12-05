package com.xiaodou.oms.agent.common.enums;

/**
 * Created by zyp on 14-7-3.
 */
public enum GorderState {
    Init(0,"初始化"),
    Success(1,"支付成功"),
    Fail(-1,"支付失败"),
    Closed(5,"关单");

    //订单状态
    private Integer state;

    //订单状态描述
    private String stateDesc;

    GorderState(Integer state,String stateDesc) {
        this.state = state;
        this.stateDesc = stateDesc;
    }

    public Integer getState() {
        return state;
    }

    public String getStateDesc() {
        return stateDesc;
    }
}
