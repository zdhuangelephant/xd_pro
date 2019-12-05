package com.xiaodou.crontab.domain;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @name @see com.xiaodou.dashboard.model.crontab.CrontabJobLog.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月3日
 * @description 任务执行记录模型类
 * @version 1.0
 */
@Data
public class CrontabJobLog {
  /** id 主键ID */
  private Integer id;
  /** configId 任务ID */
  private String configId;
  /** contextId 执行者ID */
  private String contextId;
  /** excutorId 执行ID */
  private String excutorId;
  /** contextName 执行者节点名 */
  private String contextName;
  /** dataVersion 数据版本号 */
  private Integer dataVersion;
  /** crontStatus 调度状态 */
  private Short crontStatus;
  /** crontTime 调度时间 */
  private Timestamp crontTime;
  /** crontCost 调度耗时 */
  private Integer crontCost;
  /** crontResult 调度结果 */
  private String crontResult;
  /** crontRetry 重试次数 */
  private Integer crontRetry;
}
