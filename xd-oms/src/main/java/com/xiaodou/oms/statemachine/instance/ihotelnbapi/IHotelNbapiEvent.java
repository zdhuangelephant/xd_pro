/**
 * @Title: NbapiEvent.java
 * @Package com.elong.oms.statemachine.instance.ihotelnbapi
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Email: songbin0819@163.com
 * 
 * @author zwl
 * @date 2015年4月22日 上午9:43:32
 * @version V1.0
 */
package com.xiaodou.oms.statemachine.instance.ihotelnbapi;

/**
 * NbapiEvent
 * 
 * @Title: NbapiEvent
 * @Description: 国际酒店-NBAPI业务的事件
 * @author zwl
 * @date 2015年4月22日 上午9:43:32
 *
 */
public enum IHotelNbapiEvent {
    DeliveredSuccess("DeliveredSuccess", "确认成功"), DeliveredFailure("DeliveredFailure", "确认失败"), PaySuccess("PaySuccess", "支付成功"), CancelByNbapi("CancelByNbapi", "NBAPI取消"), RefundSuccess(
            "RefundSuccess", "退款成功"), RefundFailure("RefundFailure", "退款失败"), DeliveredTimeout("DeliveredTimeout", "确认超时"), RetrySuccess("RetrySuccess", "NBAPI重试成功"), CancelFailure(
            "CancelFailure", "取消失败"), CancelingRequestAgain("CancelingRequestAgain", "重新取消请求"), ModifyToCancelSuccess("ModifyToCancelSuccess", "人工调整到取消成功"), CancelingFromFailure(
            "CancelingFromFailure", "确认失败取消"), CancelSuccess("CancelSuccess", "取消成功"), CheckingInSuccess("CheckingInSuccess", "入住成功"), CheckingOutSuccess("CheckingOutSuccess", "离店成功"),
            ForceCancelByMis("ForceCancelByMis", "客服强制取消");

    private String name;

    private String desc;

    /**
     * Constructor for IHotelNbapiEvent.
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param name
     * @param desc
     */
    private IHotelNbapiEvent(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * getter method for name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * setter method for name
     * 
     * @Description the name to set
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * 
      * override toString
      * <p>Title: toString</p>
      * <p>Description: </p>
      * @return
     */
    @Override
    public String toString() {
        return this.getName() + ":" + this.desc;
    }

}
