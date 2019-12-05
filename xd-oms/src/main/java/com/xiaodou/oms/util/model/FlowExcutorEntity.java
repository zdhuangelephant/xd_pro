package com.xiaodou.oms.util.model;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.statemachine.Context;

public class FlowExcutorEntity {
  
  public FlowExcutorEntity(String productLine, String transationName, Context context, Integer toState){
    this.productLine = productLine;
    this.transationName = transationName;
    this.context = context;
    this.toState = toState;
  }
  
  /**
   * 所属产品线
   */
  private String productLine;
  
  /**
   * transation名称
   */
  private String transationName;
  
  /**
   * 上下文对象
   */
  private Context context;
  
  /**
   * 目标状态
   */
  private Integer toState;
  
  /**
   * 耗时
   */
  private Long costTime;
  
  /**
   * 执行结果
   */
  private String result;
  
  /**
   * 错误信息
   */
  private String errmsg;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getTransationName() {
    return transationName;
  }

  public void setTransationName(String transationName) {
    this.transationName = transationName;
  }

  public Context getContext() {
    return context;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  public Integer getToState() {
    return toState;
  }

  public void setToState(Integer toState) {
    this.toState = toState;
  }

  public String getCostTime() {
    return costTime + "ms";
  }

  public void setCostTime(Long costTime) {
    this.costTime = costTime;
  }
  
  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

}
