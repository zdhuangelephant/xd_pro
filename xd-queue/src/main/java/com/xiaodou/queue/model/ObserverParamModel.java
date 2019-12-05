package com.xiaodou.queue.model;

/**
 * 观察者参数模型
 * 
 * @author bing.cheng
 * 
 */
public class ObserverParamModel {

  /** ob执行的频率 */
  private int obRateTime;

  /** mode 任务执行模式 1 正常 2 批处理 */
  private int mode;

  /** batchLimit 批处理模式单次执行数量 */
  private int batchLimit;

  public int getBatchLimit() {
    return batchLimit;
  }

  public void setBatchLimit(int batchLimit) {
    this.batchLimit = batchLimit;
  }

  public int getMode() {
    return mode;
  }

  public void setMode(int mode) {
    this.mode = mode;
  }

  public int getObRateTime() {
    return obRateTime;
  }

  public void setObRateTime(int obRateTime) {
    this.obRateTime = obRateTime;
  }

}
