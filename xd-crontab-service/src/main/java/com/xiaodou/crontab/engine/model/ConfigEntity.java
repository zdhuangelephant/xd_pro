package com.xiaodou.crontab.engine.model;

import lombok.Data;

import com.xiaodou.crontab.domain.CrontabConfig;
import com.xiaodou.crontab.engine.constant.CrontConstant;

/**
 * @name @see com.xiaodou.crontab.engine.model.ConfigEntity.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月19日
 * @description 配置项对象实体
 * @version 1.0
 */
@Data
public class ConfigEntity {
  /** configId 配置ID */
  private String configId;
  /** crontExpression 调度策略 */
  private String crontExpression;
  /** crontProtocol 调度协议 */
  private String crontProtocol;
  /** crontTargetUrl 调度地址 */
  private String crontTarget;
  /** crontRetryTime 调度重试次数 */
  private Integer crontRetryTime;
  /** crontTimeOut 整体超时时间 */
  private Integer crontTimeOut;
  /** protocolRetryTimes 协议重试次数 */
  private Integer protocolRetryTimes;
  /** protocolTimeOut 协议超时时间 */
  private Integer protocolTimeOut;
  /** protocolStructCheck 协议结果结构化检查 */
  private Boolean protocolStructCheck;
  /** protocalConfig 协议配置项信息 */
  private String protocolConfig;
  /** version 版本号 */
  private String version;
  /** inUse 是否启用 */
  private Integer inUse;

  public boolean isInUse() {
    return null != inUse && inUse == CrontConstant.CONFIG_INUSE;
  }

  public ConfigEntity() {}

  public ConfigEntity(CrontabConfig config) {
    if (null == config || null == config.getId()) return;
    this.configId = config.getId().toString();
    this.crontExpression = config.getCrontExpression();
    this.crontProtocol = config.getCrontProtocol();
    this.crontTarget = config.getCrontTarget();
    this.crontRetryTime =
        null == config.getCrontRetryTime() ? 0 : config.getCrontRetryTime().intValue();
    this.crontTimeOut = config.getCrontTimeOut();
    this.protocolRetryTimes = config.getProtocolRetryTimes();
    this.protocolTimeOut = config.getProtocolTimeOut();
    this.protocolStructCheck =
        (null != config.getProtocolStructCheck() && CrontConstant.TRUE == config
            .getProtocolStructCheck()) ? true : false;
    this.protocolConfig = config.getProtocolConfig();
    this.version = config.getVersion();
    this.inUse =
        null == config.getInUse() ? CrontConstant.CONFIG_UNUSE : config.getInUse().intValue();
  }
}
