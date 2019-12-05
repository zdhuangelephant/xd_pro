package com.xiaodou.oms.statemachine.instance.ihotel;

/**
 * 
 * @Title:IHotelEvent.java
 * 
 * @Description:国际酒店-酒店业务的事件
 *
 * @author  guanguo.gao
 * @date    June 16, 2014 3:24:35 PM
 * @version V1.0
 */
public enum IHotelEvent {
    PaySuccess("PaySuccess","支付成功"),
    PayFailure("PayFailure","支付失败"),
    PayTimeout("PayTimeout", "支付超时"),
    RefundSuccess("RefundSuccess", "退款成功"),
    RefundFailure("RefundFailure", "退款失败"),
    CancelByUser("CancelByUser", "用户取消"),
    DeliveringSuccess("DeliveringSuccess", "配单成功"),
    DeliveredSuccess("DeliveredSuccess", "确认成功"),
    WaitingSecondPay("WaitingSecondPay", "确认成功有差价"),
    DeliveredTimeout("DeliveredTimeout", "确认超时"),
    DeliveredFailure("DeliveredFailure", "确认失败"),
    DeliveringAgain("DeliveringAgain", "再次确认请求"),
    SecondPaySuccess("SecondPaySuccess", "差价退还成功"),
    ModifyToSuccess("ModifyToSuccess", "客服修改成功"),
    CancelingFromFailure("CancelingFromFailure", "确认失败取消"),
    CancelingRequest("CancelingRequest", "取消请求"),
    CancelingFailure("CancelingFailure", "取消失败"),
    CancelSuccess("CancelSuccess", "取消成功"),
    CancelFailure("CancelFailure", "取消失败"),
    CancelingRequestAgain("CancelingRequestAgain", "重新取消请求"),
    ModifyToCancelSuccess("ModifyToCancelSuccess", "人工调整到取消成功"),
    ModifyToDelivered("ModifyToDelivered", "人工调整到确认成功"),
    CheckingInSuccess("CheckingInSuccess", "入住成功"),
    CheckingOutSuccess("CheckingOutSuccess", "结账成功"),
    CancelByFraud("CancelByFraud","风控拒绝"),
    OutworkDoubtByFraud("OutworkDoubtByFraud","非工作时间风控可疑"),
    ForceCancelByMis("ForceCancelByMis", "客服强制取消");

    private String name;

    private String desc;

    /**
     * Constructor for IHotelEvent.
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
    private IHotelEvent(String name, String desc) {
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
     * getter method for desc
     * 
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 
     * override toString
     * <p>
     * Title: toString
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @return
     */
    @Override
    public String toString() {
        return this.getName() + ":" + this.desc;
    }
  
}
