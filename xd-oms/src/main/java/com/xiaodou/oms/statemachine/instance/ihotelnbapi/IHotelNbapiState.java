/**
 * @Title: NbapiState.java
 * @Package com.elong.oms.statemachine.instance.ihotelnbapi
 * @Description: 国际酒店-NBAPI B2B订单状态
 * Copyright: Copyright (c) 2014 
 * Email: songbin0819@163.com
 * 
 * @author zwl
 * @date 2015年4月22日 上午9:44:19
 * @version V1.0
 */
package com.xiaodou.oms.statemachine.instance.ihotelnbapi;

/**
 * NbapiState
 * 
 * @Title: NbapiState
 * @Description: 国际酒店-NBAPI B2B订单状态
 * @author zwl
 * @date 2015年4月22日 上午9:44:19
 *
 */
public enum IHotelNbapiState {
    Init(0, "Init", "新单"), PaySuccess(1, "PaySuccess", "支付成功"), Delivered(2, "Delivered", "确认成功"), DeliveredFailure(-2, "DeliveredFailure", "确认失败"), Closed(5, "Closed", "取消"), Canceling(4,
            "Canceling", "取消中"), CancelSuccess(7, "CancelSuccess", "取消成功"), CancelFailure(-7, "CancelFailure", "取消失败"), CheckedIn(3, "CheckedIn", "已入住"), CheckedOut(8, "CheckedOut", "已结账");

    private Integer name;

    private String code;

    private String desc;

    private IHotelNbapiState(Integer name, String code, String desc) {
        this.name = name;
        this.code = code;
        this.desc = desc;
    }

    /**
     * getter method for name
     * 
     * @return the name
     */
    public Integer getName() {
        return name;
    }

    /**
     * setter method for name
     * 
     * @Description the name to set
     * @param name
     */
    public void setName(Integer name) {
        this.name = name;
    }

    /**
     * getter method for code
     * 
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * setter method for code
     * 
     * @Description the code to set
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * getter method for desc
     * 
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * setter method for desc
     * 
     * @Description the desc to set
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.getName().toString();
    }
}
