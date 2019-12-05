
package com.xiaodou.oms.statemachine.instance.ihotelinsurance;



/**
 * @Description: <p>国际酒店-保险事件<p>
 * @author <mailto:guanguo.gao@corp.elong.com>Guanguo.Gao</mailto>
 * @date 2014年10月23日 下午3:28:15
 * @version <b>1.0</b>
 */
public enum IHotelInsuranceEvent {

    DeliveringSuccess("DeliveringSuccess", "投保请求成功"),
    DeliveredSuccess("DeliveredSuccess", "投保成功"),
    DeliveredFailure("DeliveredFailure", "投保失败"),
    DeliveringRequestAgain("DeliveringRequestAgain", "再次请求投保"),
    ModifiedToDelivered("ModifiedToDelivered", "客服修改成功"),
    RefundRequest("RefundRequest", "退款请求"),
    RefundSuccess("RefundSuccess", "退款成功"),
    RefundFailure("RefundFailure", "退款失败");
    
    private String name;
    
    private String desc;
    
    /**
     * Constructor for IHotelInsuranceEvent. 
      * <p>Title: </p>
      * <p>Description: </p>
      * @param name
      * @param desc
     */
    private IHotelInsuranceEvent(String name, String desc){
        this.name = name;
        this.desc = desc;
    }
    

    /**
     * getter method for name
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * setter method for name
     * @Description the name to set
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
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
    public String toString(){
        return this.name;
    }
    
}
