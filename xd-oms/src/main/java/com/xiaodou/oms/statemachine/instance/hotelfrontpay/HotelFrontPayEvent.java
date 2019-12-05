package com.xiaodou.oms.statemachine.instance.hotelfrontpay;

/**
 * HotelFrontPayEvent
  * @Title: HotelFrontPayEvent
  * @Desription 酒店前台支付事件 
  * @author Guanguo.Gao
  * @date 2014年12月25日 下午9:00:57
 */
public enum HotelFrontPayEvent {

    PaySuccess("PaySuccess","支付成功"),
    PayFailure("PayFailure","支付失败"),
    PayTimeout("PayTimeout", "支付超时"),
    RefundSuccess("RefundSuccess", "退款成功"),
    RefundFailure("RefundFailure", "退款失败"),
    CancelByUser("CancelByUser", "用户取消");

    private String name;

    private String desc;

    /**
     * Constructor for HotelFrontPayEvent. 
      * <p>Title: </p>
      * <p>Description: </p>
      * @param name
      * @param desc
     */
    private HotelFrontPayEvent(String name, String desc) {
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
