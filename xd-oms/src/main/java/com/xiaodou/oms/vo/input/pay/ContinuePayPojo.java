package com.xiaodou.oms.vo.input.pay;


import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * Date: 2014/7/4
 * Time: 10:30
 *
 * @author Tian.Dong
 */
public class ContinuePayPojo extends BasePojo {

  /**
   * 大订单号
   */
  @NotEmpty
  public String gorderId;

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

}
