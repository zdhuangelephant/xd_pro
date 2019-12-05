package com.xiaodou.oms.vo.input.task;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * Date: 2014/12/1
 * Time: 17:15
 *
 * @author Tian.Dong
 */
public class CheckMessageBatchPojo extends BaseValidatorPojo {

  private String productLine;

  private String startTime;

  private String endTime;

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
}
