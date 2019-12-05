package com.xiaodou.crontab.domain;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @name @see com.xiaodou.dashboard.model.crontab.CrontabConfig.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月3日
 * @description 任务定义模型类
 * @version 1.0
 */
@Data
public class CrontabConfig {
  /** id 主键ID */
  private Integer id;
  /** businessCode 业务模块 */
  private String businessCode;
  /** crontExpression 调度策略 */
  private String crontExpression;
  /** crontProtocol 调度协议 */
  private String crontProtocol;
  /** crontTarget 调度地址 */
  private String crontTarget;
  /** crontTimeOut 整体超时 */
  private Integer crontTimeOut;
  /** crontRetryTime 失败重试 */
  private Short crontRetryTime;
  /** protocolRetryTimes 协议重试次数 */
  private Integer protocolRetryTimes;
  /** protocolTimeOut 协议超时时间 */
  private Integer protocolTimeOut;
  /** protocolStructCheck 协议格式检查 */
  private Integer protocolStructCheck;
  /** protocolConfig 协议配置项信息 */
  private String protocolConfig;
  /** version 版本号 */
  private String version;
  /** inUse 启用状态 */
  private Short inUse;
  /** createTime 创建时间 */
  private Timestamp createTime;
  /** updateTime 更新时间 */
  private Timestamp updateTime;
  /** userGroup 所属用户组 */
  private Integer userGroup;
  /** owner 创建者 */
  private Integer owner;

}
