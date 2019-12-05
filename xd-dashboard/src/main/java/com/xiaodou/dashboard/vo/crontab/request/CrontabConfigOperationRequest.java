package com.xiaodou.dashboard.vo.crontab.request;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.model.crontab.CrontabConfig;
import com.xiaodou.dashboard.model.crontab.inner.CrontHttpConfig;

/**
 * @name @see com.xiaodou.dashboard.vo.crontab.request.CrontabConfigOperationRequest .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月5日
 * @description 定时任务操作请求
 * @version 1.0
 */
@Data
public class CrontabConfigOperationRequest {
  /** id 主键ID */
  private Integer id;
  /** businessCode 业务模块 */
  private String businessCode;
  /** crontExpression 调度策略 */
  private String crontExpression;
  /** crontProtocol 调度协议 */
  private String crontProtocol;
  /** dataProtocol 数据协议 */
  private String dataProtocol;
  /** crontTarget 调度地址 */
  private String crontTarget;
  /** crontTimeOut 整体超时 */
  private Integer crontTimeOut = 5000;
  /** crontRetryTime 失败重试 */
  private Short crontRetryTimes = 3;
  /** protocolTimeOut 协议超时时间 */
  private Integer protocolTimeOut = 5000;
  /** protocolRetryTime 协议重试次数 */
  private Integer protocolRetryTimes = 3;
  /** protocolStructCheck 启动结果结构检查 */
  private Integer protocolStructCheck = 1;
  /** version 版本号 */
  private String version;
  /** inUse 启用状态 */
  private Short inUse;
  /** userGroup 所属用户组 */
  private Integer userGroup;

  public CrontabConfig buildModel() {
    CrontabConfig config = new CrontabConfig();
    config.setId(this.id);
    config.setBusinessCode(this.businessCode);
    config.setCrontExpression(this.crontExpression);
    config.setCrontProtocol(this.crontProtocol);
    config.setCrontTarget(this.crontTarget);
    config.setCrontTimeOut(this.crontTimeOut);
    config.setCrontRetryTime(this.crontRetryTimes);
    config.setProtocolTimeOut(this.protocolTimeOut);
    config.setProtocolRetryTimes(this.protocolRetryTimes);
    config.setProtocolStructCheck(this.protocolStructCheck);
    CrontHttpConfig httpConfig = new CrontHttpConfig();
    httpConfig.setDataProtocol(dataProtocol);
    config.setProtocolConfig(FastJsonUtil.toJson(httpConfig));
    config.setVersion(new Timestamp(System.currentTimeMillis()).toString());
    config.setInUse(this.inUse);
    config.setUserGroup(1);
    config.setOwner(1);
    config.setCreateTime(new Timestamp(System.currentTimeMillis()));
    config.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    return config;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getBusinessCode() {
    return businessCode;
  }

  public void setBusinessCode(String businessCode) {
    this.businessCode = businessCode;
  }

  public String getCrontExpression() {
    return crontExpression;
  }

  public void setCrontExpression(String crontExpression) {
    this.crontExpression = crontExpression;
  }

  public String getCrontProtocol() {
    return crontProtocol;
  }

  public void setCrontProtocol(String crontProtocol) {
    this.crontProtocol = crontProtocol;
  }

  public String getDataProtocol() {
    return dataProtocol;
  }

  public void setDataProtocol(String dataProtocol) {
    this.dataProtocol = dataProtocol;
  }

  public String getCrontTarget() {
    return crontTarget;
  }

  public void setCrontTarget(String crontTarget) {
    this.crontTarget = crontTarget;
  }

  public Integer getCrontTimeOut() {
    return crontTimeOut;
  }

  public void setCrontTimeOut(Integer crontTimeOut) {
    this.crontTimeOut = crontTimeOut;
  }

  public Short getCrontRetryTimes() {
    return crontRetryTimes;
  }

  public void setCrontRetryTimes(Short crontRetryTimes) {
    this.crontRetryTimes = crontRetryTimes;
  }

  public Integer getProtocolTimeOut() {
    return protocolTimeOut;
  }

  public void setProtocolTimeOut(Integer protocolTimeOut) {
    this.protocolTimeOut = protocolTimeOut;
  }

  public Integer getProtocolRetryTimes() {
    return protocolRetryTimes;
  }

  public void setProtocolRetryTimes(Integer protocolRetryTimes) {
    this.protocolRetryTimes = protocolRetryTimes;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Short getInUse() {
    return inUse;
  }

  public void setInUse(Short inUse) {
    this.inUse = inUse;
  }

  public Integer getUserGroup() {
    return userGroup;
  }

  public void setUserGroup(Integer userGroup) {
    this.userGroup = userGroup;
  }

}
