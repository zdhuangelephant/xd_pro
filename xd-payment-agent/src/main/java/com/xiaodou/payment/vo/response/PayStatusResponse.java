package com.xiaodou.payment.vo.response;


/**
 * {"rep_code":"0","error_info":null,"businesstype":1022,"orderid":"7296365","result_status":1,"pay_time":"2014-11-10 14:08:04"}
 * <p/>
 * Date: 2014/7/18
 * Time: 14:52
 *
 * @author Tian.Dong
 */
public class PayStatusResponse extends ResponseBase {

  /**
   * 业务线
   */
  private Integer businessType;

  /**
   * 业务订单号
   */
  private String orderId;

  /**
   * 支付状态
   */
  private Integer resultStatus;

  /**
   * 支付时间
   */
  private String payTime;

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Integer getResultStatus() {
    return resultStatus;
  }

  public void setResultStatus(Integer resultStatus) {
    this.resultStatus = resultStatus;
  }

  public String getPayTime() {
    return payTime;
  }

  public void setPayTime(String payTime) {
    this.payTime = payTime;
  }
}
