package com.xiaodou.oms.vo.input.pay;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 查询一个Gorder的第一次支付信息
 *
 * Date: 2014/8/27
 * Time: 13:16
 *
 * @author Tian.Dong
 */
public class QueryFirstPayInfoPojo extends BasePojo{
  @NotEmpty
  private String gorderId;


  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }
}
