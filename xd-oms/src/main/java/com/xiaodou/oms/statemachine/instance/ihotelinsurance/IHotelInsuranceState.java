
package com.xiaodou.oms.statemachine.instance.ihotelinsurance;



/**
 * @Description: <p>国际酒店-保险状态机<p>
 * @author <mailto:guanguo.gao@corp.elong.com>Guanguo.Gao</mailto>
 * @date 2014年10月23日 下午3:18:12
 * @version <b>1.0</b>
 */
public enum IHotelInsuranceState {
    
    Init(0, "Init", "新单"), 
    PaySuccess(1, "PaySuccess", "支付成功"), 
    PayFailure(-1, "PayFailure", "支付失败"), 
    Closed(5, "Closed", "取消"), 
    Delivering(6,"Delivering","投保中"),
    Delivered(2,"Delivered","投保成功"),
    DeliveredFailure(-2,"DeliveredFailure","投保失败");

    private Integer name;
    private String code;
    private String desc;

    /**
     * Constructor for IHotelInsuranceState. 
      * <p>Title: </p>
      * <p>Description: </p>
      * @param name
      * @param code
      * @param desc
     */
    private IHotelInsuranceState(Integer name, String code, String desc) {
      this.name = name;
      this.code = code;
      this.desc = desc;
    }


    /**
     * getter method for name
     * @return the name
     */
    public Integer getName() {
        return name;
    }



    /**
     * setter method for name
     * @Description the name to set
     * @param name 
     */
    public void setName(Integer name) {
        this.name = name;
    }



    /**
     * getter method for code
     * @return the code
     */
    public String getCode() {
        return code;
    }



    /**
     * setter method for code
     * @Description the code to set
     * @param code 
     */
    public void setCode(String code) {
        this.code = code;
    }



    /**
     * getter method for desc
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }



    /**
     * setter method for desc
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
      return this.getName().toString();
    }
}
