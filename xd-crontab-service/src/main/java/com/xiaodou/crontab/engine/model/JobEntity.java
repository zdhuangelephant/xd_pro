package com.xiaodou.crontab.engine.model;

import java.util.Set;
import java.util.UUID;

import lombok.Data;

import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.crontab.instance.ServiceContext;

/**
 * @name @see com.xiaodou.crontab.engine.model.JobEntity.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月18日
 * @description 任务执行模型对象实体
 * @version 1.0
 */
@Data
public class JobEntity {
  /** configId 配置ID */
  private String configId;
  /** jobId 任务ID */
  private String jobId;
  /** contextId 执行者上下文ID */
  private String contextId;
  /** dataVersion 数据版本号 */
  private Integer dataVersion = 0;
  /** retryTime 当前重试次数 */
  private Integer retryTime = 0;
  /** retryContext 重试上下文 */
  private Set<String> retryContext = Sets.newHashSet();
  /** excuteId 执行ID */
  private String excuteId;
  /** retryId 重试ID */
  private String retryId;
  /** startTime 开始时间 */
  private long startTime;
  /** excutable 是否可执行 */
  private boolean excutable = true;

  /**
   * 刷新任务实体
   * 
   * @param job 任务实体
   */
  public void refreshData(JobEntity job) {
    synchronized (this) {
      if (job.getDataVersion() <= this.dataVersion) return;
      this.retryId = job.getRetryId();
      this.retryTime = job.getRetryTime();
      this.retryContext = job.getRetryContext();
    }
  }

  /**
   * 执行前准备操作
   */
  public void preExcute() {
    synchronized (this) {
      if (StringUtils.isNotBlank(retryId))
        excuteId = retryId;
      else
        excuteId = UUID.randomUUID().toString();
    }
  }

  /**
   * 失败重试重置任务状态
   */
  public void retry() {
    synchronized (this) {
      retryContext.add(ServiceContext.getId());
      retryTime = retryTime + 1;
      retryId = excuteId;
    }
  }

  public void ok() {
    synchronized (this) {
      retryContext.clear();
      retryTime = 0;
      retryId = null;
    }
  }
}
