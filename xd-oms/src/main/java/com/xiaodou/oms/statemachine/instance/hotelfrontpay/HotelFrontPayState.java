package com.xiaodou.oms.statemachine.instance.hotelfrontpay;

/**
 * HotelFrontPayState
  * @Title: HotelFrontPayState
  * @Desription 酒店前台支付事件
  * @author Guanguo.Gao
  * @date 2014年12月25日 下午8:57:26
 */
public enum HotelFrontPayState {

    Init(0, "Init", "新单"),
    PaySuccess(1, "PaySuccess", "支付成功"),
    PayFailure(-1, "PayFAailure", "支付失败"),
    Closed(5, "Closed", "关单");

    //name
    private Integer name;
    //code
    private String code;
    //描述
    private String desc;

    /**
     * Constructor for HotelFrontPayState. 
      * <p>Title: </p>
      * <p>Description: </p>
      * @param name
      * @param code
      * @param desc
     */
    private HotelFrontPayState(Integer name, String code, String desc) {
      this.name = name;
      this.code = code;
      this.desc = desc;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public Integer getName() {
      return name;
    }

    public void setName(Integer name) {
      this.name = name;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    @Override
    public String toString() {
      return this.getName().toString();
    }
}
