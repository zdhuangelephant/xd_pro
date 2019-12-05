package com.xiaodou.oms.statemachine.instance.ihotel;

/**
 * 
 * @Title:IHotelState.java
 * 
 * @Description:国际酒店-酒店的订单状态
 * 
 * @author guanguo.gao
 * @date Oct 16, 2014 20:09:35 PM
 * @version V1.0
 */
public enum IHotelState {
      Init(0, "Init", "新单"), 
      PaySuccess(1, "PaySuccess", "支付成功"),
      PayFailure(-1, "PayFailure", "支付失败"), 
      Closed(5, "Closed", "取消"), 
      Delivering(6, "Delivering", "确认中"),
      Delivered(2, "Delivered", "确认成功"), 
      WaitingSecondPay(10, "WaitingSecondPay", "确认成功有差价"),
      DeliveredFailure(-2, "DeliveredFailure", "确认失败"),
      Canceling(4, "Canceling", "取消中"),
      CancelSuccess(7, "CancelSuccess", "取消成功"),
      CancelFailure(-7, "CancelFailure", "取消失败"),
      CheckedIn(3, "CheckedIn", "已入住"),
      CheckedOut(8, "CheckedOut", "已结账");

    /** 名字 **/
    private Integer name;
    /** code **/
    private String code;
    /** 描述　 **/
    private String desc;

    /**
     * Constructor for IHotelState.
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param name
     * @param code
     * @param desc
     */
    private IHotelState(Integer name, String code, String desc) {
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
     * getter method for code
     * 
     * @return the code
     */
    public String getCode() {
        return code;
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
      * <p>Title: toString</p>
      * <p>Description: </p>
      * @return
     */
    @Override
    public String toString() {
        return this.getName().toString();
    }

}
