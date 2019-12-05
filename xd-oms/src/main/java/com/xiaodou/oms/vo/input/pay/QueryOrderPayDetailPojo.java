package com.xiaodou.oms.vo.input.pay;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * Date: 2014/10/22
 * Time: 11:05
 *
 * @author Tian.Dong
 */
public class QueryOrderPayDetailPojo extends BasePojo {
  @NotEmpty
  private String gorderId;

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }
}
