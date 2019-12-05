package com.xiaodou.queue.model;

import com.xiaodou.queue.worker.AbstractDefaultWorker;

/**
 * 执行者参数模型
 * 
 * @author bing.cheng
 * 
 */
public class WorkerParamModel {

  /** messageHandlerPath 扫描消息监听者位置 */
  private String messageHandlerPath;

  /** worker执行的频率 */
  private int workerRateTime;

  /** workerCount 默认执行器数量 */
  private Integer workerCount;

  /** 执行者实例 */
  private AbstractDefaultWorker woker;

  public String getMessageHandlerPath() {
    return messageHandlerPath;
  }

  public void setMessageHandlerPath(String messageHandlerPath) {
    this.messageHandlerPath = messageHandlerPath;
  }

  public int getWorkerRateTime() {
    return workerRateTime;
  }

  public void setWorkerRateTime(int workerRateTime) {
    this.workerRateTime = workerRateTime;
  }

  public int getWorkerCount() {
    return null == workerCount ? Runtime.getRuntime().availableProcessors() * 2 : workerCount;
  }

  public void setWorkerCount(int workerCount) {
    this.workerCount = workerCount;
  }

  public AbstractDefaultWorker getWoker() {
    return woker;
  }

  public void setWoker(AbstractDefaultWorker woker) {
    this.woker = woker;
  }


}
