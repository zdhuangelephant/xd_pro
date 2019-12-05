package com.xiaodou.dashboard.vo.crontab.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.dashboard.enums.CommonEnums;
import com.xiaodou.dashboard.model.crontab.CrontabConfig;
import com.xiaodou.dashboard.model.crontab.inner.CrontProtocolConfig;
import com.xiaodou.dashboard.model.crontab.inner.CrontProtocolConfigBuilder;

/**
 * @name @see com.xiaodou.dashboard.vo.crontab.response.CrontabConfigModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月8日
 * @description 定时任务页面展示模型
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CrontabConfigModel extends CrontabConfig {
  /** businessType 业务类型 */
  private String businessType;
  private CrontProtocolConfig protocolConfigModel;

  public CrontabConfigModel(CrontabConfig config) {
    setId(config.getId());
    setBusinessCode(config.getBusinessCode());
    setBusinessType(CommonEnums.getBussinessCodeByCode(config.getBusinessCode()).getDesc());
    setCreateTime(config.getCreateTime());
    setCrontExpression(config.getCrontExpression());
    setCrontProtocol(config.getCrontProtocol());
    setCrontRetryTime(config.getCrontRetryTime());
    setCrontTarget(config.getCrontTarget());
    setCrontTimeOut(config.getCrontTimeOut());
    setInUse(config.getInUse());
    setOwner(config.getOwner());
    setVersion(config.getVersion());
    setProtocolRetryTimes(config.getProtocolRetryTimes());
    setProtocolTimeOut(config.getProtocolTimeOut());
    setProtocolStructCheck(config.getProtocolStructCheck());
    setUpdateTime(config.getUpdateTime());
    setUserGroup(config.getUserGroup());
    setProtocolConfigModel(CrontProtocolConfigBuilder.buildProtocolConfig(config));
  }

  public String getBusinessType() {
    return businessType;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public CrontProtocolConfig getProtocolConfigModel() {
    return protocolConfigModel;
  }

  public void setProtocolConfigModel(CrontProtocolConfig protocolConfigModel) {
    this.protocolConfigModel = protocolConfigModel;
  }

}
