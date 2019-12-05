package com.xiaodou.oms.vo.input.task;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;


/**
 * Date: 2014/7/1
 * Time: 16:55
 *
 * @author Tian.Dong
 */
public class BatchPojo extends BaseValidatorPojo {

  /**
   * 处理数
   */
  @NotEmpty
  private Integer top;
  /**
   * 模数
   */
  @NotEmpty
  private Integer mod;
  /**
   * 余数
   */
  @NotEmpty
  private Integer remainder;

  public Integer getTop() {
    return top;
  }

  public void setTop(Integer top) {
    this.top = top;
  }

  public Integer getMod() {
    return mod;
  }

  public void setMod(Integer mod) {
    this.mod = mod;
  }

  public Integer getRemainder() {
    return remainder;
  }

  public void setRemainder(Integer remainder) {
    this.remainder = remainder;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
