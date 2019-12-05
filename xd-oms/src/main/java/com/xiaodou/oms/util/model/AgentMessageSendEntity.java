package com.xiaodou.oms.util.model;

import com.xiaodou.common.util.warp.FastJsonUtil;


/**
 * Date: 2014/12/2
 * Time: 15:56
 *
 * @author Tian.Dong
 */
public class AgentMessageSendEntity {
  private String tag;
  private String retCode;
  private String retDesc;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getRetCode() {
    return retCode;
  }

  public void setRetCode(String retCode) {
    this.retCode = retCode;
  }

  public String getRetDesc() {
    return retDesc;
  }

  public void setRetDesc(String retDesc) {
    this.retDesc = retDesc;
  }

  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
