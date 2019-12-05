package com.xiaodou.oms.statemachine;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.oms.statemachine.param.CoreParams;

/**
 * @Title:Context.java
 * @Description:封装状态机调用参数以及Transition执行过程中的共享数据
 * @author zhaoyang
 * @date June 18, 2014 8:33:35 AM
 * @version V1.0
 */
public class Context {
  /** 核心调用参数 */
  private CoreParams coreParams = new CoreParams();
  /** 其他调用参数 */
  private Map otherParams = new HashMap();
  /** 共享数据 */
  private Map shares = new HashMap();
  
  public Map getShares() {
    return shares;
  }

  public void setShares(Map shares) {
    this.shares = shares;
  }

  public CoreParams getCoreParams() {
    return coreParams;
  }

  public void setCoreParams(CoreParams coreParams) {
    this.coreParams = coreParams;
  }

  public Map getOtherParams() {
    return otherParams;
  }

  public void setOtherParams(Map otherParams) {
    this.otherParams = otherParams;
  }

}
