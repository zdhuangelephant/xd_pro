package com.xiaodou.oms.vo.input.pay;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 查询一个g order的payRecord
 *
 * Date: 2014/7/1
 * Time: 14:22
 *
 * @author Tian.Dong
 */
public class QueryRecordPojo extends BasePojo {


  @NotEmpty
  private String gorderId;


  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

}
